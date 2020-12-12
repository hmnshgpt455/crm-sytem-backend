package com.luv2code.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.luv2code")
@PropertySource({"classpath:persistence-mysql.properties"})
@EnableTransactionManagement
public class SpringConfig {

    private Environment environment;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource initializeDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exception) {
            throw new RuntimeException(exception);
        }
        logger.info(">>> jdbc.url=" + environment.getProperty("jdbc.driver"));
        logger.info(">>> jdbc.user=" + environment.getProperty("jdbc.user"));
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        dataSource.setInitialPoolSize(getIntProperty(environment.getProperty("connection.pool.initialPoolSize")));
        dataSource.setMinPoolSize(getIntProperty(environment.getProperty("connection.pool.minPoolSize")));
        dataSource.setMaxPoolSize(getIntProperty(environment.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMaxIdleTime(getIntProperty(environment.getProperty("connection.pool.maxIdleTime")));
        return dataSource;
    }

    private Integer getIntProperty(String property) {
        return Integer.valueOf(property);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(initializeDataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }
}

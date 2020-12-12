package com.luv2code.testdb;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String username = "springstudent";
        String password = "springstudent";

        String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        try {
            PrintWriter out = response.getWriter();
            out.println("Connecting to database : " + jdbcUrl);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            out.println("SUCCESS !!!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

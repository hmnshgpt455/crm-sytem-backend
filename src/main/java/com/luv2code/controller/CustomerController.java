package com.luv2code.controller;

import com.luv2code.entity.Customer;
import com.luv2code.error.CustomerNotFoundException;
import com.luv2code.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        customer.setId(0);
        customerService.saveCustomer(customer);
        return customer;
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId ) {
        Customer customer = customerService.getCustomer(customerId);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return customer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        customerService.deleteCustomer(customerId);
    }
}

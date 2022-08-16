package com.ankit.dundermifflin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ankit.dundermifflin.service.CustomerService;
import com.ankit.dundermifflin.exception.CustomerAlreadyExistException;
import com.ankit.dundermifflin.exception.CustomerNotFoundException;
import com.ankit.dundermifflin.persistence.model.Customer;



@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;


    @GetMapping(value = "get/name")
    public ResponseEntity<Customer> getCustomerByName(@RequestParam String customerName) throws CustomerNotFoundException  {
        return  new ResponseEntity<Customer>(service.getCustomerByName(customerName), HttpStatus.OK);
    }

    @GetMapping(value = "get/id")
    public ResponseEntity<Object> findOne(@RequestParam long id) throws CustomerNotFoundException {
        return new ResponseEntity<Object>(service.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<Object> findAll() throws CustomerNotFoundException {
        return new ResponseEntity<Object>(service.getAllCustomers(), HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) throws CustomerAlreadyExistException {
        System.out.println(customer.toString());
        return new ResponseEntity<Customer>(service.createCustomer(customer), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam long id) throws CustomerNotFoundException {
          
         return new ResponseEntity<String>(service.deleteCustomer(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Customer> updateBook(@RequestBody Customer customer) throws CustomerNotFoundException, CustomerAlreadyExistException {
      return new ResponseEntity<Customer>(service.updateCustomer(customer), HttpStatus.OK);
    }
    
    
    
    
}

package com.ankit.dundermifflin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.dundermifflin.exception.CustomerAlreadyExistException;
import com.ankit.dundermifflin.exception.CustomerNotFoundException;
import com.ankit.dundermifflin.persistence.model.Customer;
import com.ankit.dundermifflin.persistence.repo.CustomerRepo;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer getCustomerByName(String customerName) throws CustomerNotFoundException {

        return customerRepo.findByName(customerName).orElseThrow(
                () -> new CustomerNotFoundException("No Customer is found for the customer name provided."));
    }

    public Customer getCustomerById(long id) throws CustomerNotFoundException {
        return customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No Customer is found for the customer id provided."));
    }

    public List<Customer> getAllCustomers() throws CustomerNotFoundException{
        List<Customer> customers =  (List<Customer>) customerRepo.findAll();
        if(customers.isEmpty()){
            throw new CustomerNotFoundException("No Customers are in the system.");
        }
        return customers;
    }

    public Customer createCustomer(Customer customer) throws CustomerAlreadyExistException {

        if(customerRepo.findByName(customer.getName()).isPresent()){
            throw new CustomerAlreadyExistException("Customer Already Exists with this name.");
        }

        return customerRepo.save(customer);

    }

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException, CustomerAlreadyExistException {
        this.getCustomerById(customer.getId());

        return this.createCustomer(customer);
    }

    public String deleteCustomer(long id) throws CustomerNotFoundException {
        this.getCustomerById(id);
        customerRepo.deleteById(id);
        return "Deleted";
    }

}

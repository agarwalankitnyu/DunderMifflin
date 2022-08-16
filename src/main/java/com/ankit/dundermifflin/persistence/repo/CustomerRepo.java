package com.ankit.dundermifflin.persistence.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ankit.dundermifflin.persistence.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    Optional<Customer> findByName(String name);
    
}

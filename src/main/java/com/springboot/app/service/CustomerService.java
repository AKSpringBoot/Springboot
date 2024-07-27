package com.springboot.app.service;


import com.springboot.app.exception.UserNameExistException;
import com.springboot.app.model.Customer;
import com.springboot.app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {

        if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new UserNameExistException("Username already exists!!");
        }
        //Random password being set for the customer
        customer.setPassword(generateRandomPassword());

        //IBAN generated for customer
        customer.setIban(generateIban());

        return customerRepository.save(customer);
    }


    //Generating IBAN for Customer

    private String generateIban() {
        return "NL" + (int) (Math.random() * 10000000000000000L);
    }


    //Random Password Generation

    private String generateRandomPassword() {
        return String.valueOf(UUID.randomUUID());
    }


    //Login Authentication 

    public Boolean authenticate(String username, String password) {
        Customer customerOpt = customerRepository.findByUsername(username);
        if (!StringUtils.isEmpty(customerOpt.getUsername()) && password.equalsIgnoreCase(customerOpt.getPassword())) {
            return true;
        }
        return false;
    }
}

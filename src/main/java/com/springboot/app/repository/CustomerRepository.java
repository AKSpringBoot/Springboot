package com.springboot.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   
	Customer findByUsername(String userName);
	
	 boolean existsByUsername(String userName);
}

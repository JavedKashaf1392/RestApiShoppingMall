package com.ps.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	

}

package com.coforge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

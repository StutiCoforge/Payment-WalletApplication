package com.coforge.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coforge.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
	Optional<Customer> findByEmailAndPwd(String email, String pwd);
}


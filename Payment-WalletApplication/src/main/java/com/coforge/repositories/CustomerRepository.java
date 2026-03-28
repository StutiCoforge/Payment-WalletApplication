package com.coforge.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coforge.entities.Customer;
import com.coforge.entities.Transaction;
import com.coforge.entities.TransactionCategory;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
	Optional<Customer> findByEmailAndPwd(String email, String pwd);

	List<Transaction> findByCustomer_CustIdAndCategory(Long custId, TransactionCategory category);
}


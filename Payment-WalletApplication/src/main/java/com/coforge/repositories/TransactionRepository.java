package com.coforge.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.entities.Beneficiary;
import com.coforge.entities.Transaction;
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	  List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
}

package com.coforge.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.coforge.entities.Beneficiary;
import com.coforge.entities.Transaction;
import com.coforge.entities.TransactionCategory;
import com.coforge.entities.TransactionSubCategory;
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	    List<Transaction> findByCategory(TransactionCategory category);

	    List<Transaction> findBySubCategory(TransactionSubCategory subCategory);

	    List<Transaction> findByCustomer_CustId(Long custId);

	    List<Transaction> findByCustomer_CustIdAndCategory(Long custId, TransactionCategory category);

	    List<Transaction> findByCustomer_CustIdAndSubCategory(Long custId, TransactionSubCategory subCategory);
	    List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);

@Query("SELECT t FROM Transaction t WHERE MONTH(t.transactionDate) = :month AND YEAR(t.transactionDate) = :year")
List<Transaction> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

}

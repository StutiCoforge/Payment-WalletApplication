package com.coforge.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.coforge.entities.Customer;
import com.coforge.entities.Wallet;
import com.coforge.exception.TransactionNotFoundException;
import com.coforge.repositories.CustomerRepository;
import com.coforge.repositories.TransactionRepository;





import org.springframework.stereotype.Service;

import com.coforge.daos.TransactionDao;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.dtos.TransactionDto;
import com.coforge.entities.Transaction;
import com.coforge.entities.TransactionCategory;
import com.coforge.entities.TransactionSubCategory;

@Service
public class TransactionService implements TransactionServiceInterface {

    @Autowired
    private TransactionDao transactionDao;

    private TransactionDto toDto(Transaction tx) {
          TransactionDto dto = new TransactionDto();
          dto.setTransactionId(tx.getTransactionId());
          dto.setTransactionType(tx.getTransactionType());
          dto.setTransactionStatus(tx.getTransactionStatus());
          dto.setTransactionAmount(tx.getTransactionAmount());
          dto.setDescription(tx.getDescription());
          dto.setTransactionDate(tx.getTransactionDate());

          dto.setCustomerId(tx.getCustomer().getCustId());
          dto.setCategory(tx.getCategory());
          dto.setSubCategory(tx.getSubCategory());

          return dto;
      }


  
    
       @Override
    public Transaction addTransaction(Transaction requestTx) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();

        Customer customer1 = transactionDao.findCustomerById(
                requestTx.getCustomer().getCustId()
        );

        if (customer1 == null)
            throw new RuntimeException("Customer not found");

        Transaction tx = requestTx;

//        tx.setCustomer(customer);
//        tx.setTransactionAmount(requestTx.getTransactionAmount());
//        tx.setTransactionType(requestTx.getTransactionType());
        
        tx.setTransactionStatus("PENDING");
        tx.setTransactionDate(LocalDate.now());

        return transactionDao.saveTransaction(tx);
    }

    @Override
    public Transaction updateTransaction(Transaction requestTx) {

        Transaction existingTx = transactionDao.findTransactionById(
                requestTx.getTransactionId()
        );

        if (existingTx == null)
            throw new TransactionNotFoundException("Transaction not found with ID: " 
                    + requestTx.getTransactionId());

        existingTx.setTransactionStatus(requestTx.getTransactionStatus());

        return transactionDao.saveTransaction(existingTx);
    }

    // ✅ VIEW ALL
       @Override
       public List<TransactionDto> viewAllTransaction() {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
           return transactionDao
                   .viewAllTransaction()
                   .stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
       }


@Override
public List<TransactionDto> viewTransactionByDate(LocalDate from, LocalDate to) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();

    List<Transaction> transactions =
            transactionDao.viewTransactionByDate(from, to);

    return transactions.stream()
            .map(this::toDto)
            .toList();
}


@Override
    public List<TransactionDto> getByCategory(TransactionCategory category) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
    List<Transaction> transactions =
  transactionDao.getByCategory(category);

    return transactions.stream()
            .map(this::toDto)
            .toList();
    }

   
    @Override
    public List<TransactionDto> getBySubCategory(TransactionSubCategory subCategory) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
        List<Transaction> transactions =
transactionDao.getBySubCategory(subCategory);
        return transactions.stream()
        .map(this::toDto)
        .toList();
        
    }
    @Override
    public List<TransactionDto> getCustomerTransactionsByCategory(Long custId, TransactionCategory category) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
    	 List<Transaction> transactions = transactionDao.getCustomerTransactionsByCategory(custId, category);
         return transactions.stream()
        	        .map(this::toDto)
        	        .toList();
    }


     @Override
     public List<TransactionDto> getCustomerTransactionsBySubCategory(Long custId, TransactionSubCategory subCategory) {
    	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
    	 List<Transaction> transactions =transactionDao.getCustomerTransactionsBySubCategory(custId, subCategory);
    	  return transactions.stream()
      	        .map(this::toDto)
      	        .toList();
     }



     @Override
       public List<TransactionDto> viewTransactionByMonth(int month, int year) {
 	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
    	 List<Transaction> transactions = transactionDao.viewTransactionByMonth(month, year);
    	  return transactions.stream()
        	        .map(this::toDto)
        	        .toList();
       }


	
	

}

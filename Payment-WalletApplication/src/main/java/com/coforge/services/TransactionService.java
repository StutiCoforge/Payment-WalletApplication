package com.coforge.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coforge.entities.Customer;
import com.coforge.entities.Wallet;
import com.coforge.repositories.CustomerRepository;
import com.coforge.repositories.TransactionRepository;





import org.springframework.stereotype.Service;

import com.coforge.daos.TransactionDao;

import com.coforge.entities.Transaction;

@Service
public class TransactionService implements TransactionServiceInterface {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction addTransaction(Transaction requestTx) {

        Customer customer = transactionDao.findCustomerById(
                requestTx.getCustomer().getCustomerId()
        );

        if (customer == null)
            throw new RuntimeException("Customer not found");

        Transaction tx = new Transaction();

        tx.setCustomer(customer);
        tx.setTransactionAmount(requestTx.getTransactionAmount());
        tx.setTransactionType(requestTx.getTransactionType());

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
            throw new RuntimeException("Transaction not found");

        existingTx.setTransactionStatus(requestTx.getTransactionStatus());

        return transactionDao.saveTransaction(existingTx);
    }

	@Override
	public List<Transaction> viewAllTransaction() {
	    return transactionDao.viewAllTransaction();
	}
	
	@Override
	public List<Transaction> viewTransactionByDate(LocalDate from, LocalDate to) {
	    return transactionDao.viewTransactionByDate(from, to);
	}


	
	

}

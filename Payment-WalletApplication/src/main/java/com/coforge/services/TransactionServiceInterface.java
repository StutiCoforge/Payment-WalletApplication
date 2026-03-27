package com.coforge.services;

import java.time.LocalDate;
import java.util.List;

import com.coforge.entities.Transaction;



public interface TransactionServiceInterface {
  public Transaction addTransaction(Transaction tran);
  public Transaction updateTransaction(Transaction tran);
  
  public List<Transaction> viewAllTransaction();
  public List<Transaction> viewTransactionByDate(LocalDate from,LocalDate to);
  

}

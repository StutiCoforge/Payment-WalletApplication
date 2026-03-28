package com.coforge.services;

import java.time.LocalDate;
import java.util.List;

import com.coforge.entities.Transaction;


import java.time.LocalDate;
import java.util.List;

import com.coforge.entities.Transaction;
import com.coforge.entities.TransactionCategory;
import com.coforge.entities.TransactionSubCategory;

public interface TransactionServiceInterface {

    // Add Transaction
    public Transaction addTransaction(Transaction tran);

    // Update Transaction (SUCCESS / FAILED)
    public Transaction updateTransaction(Transaction tran);

    // View ALL Transactions
    public List<Transaction> viewAllTransaction();

    // View Transactions by Date Range
    public List<Transaction> viewTransactionByDate(LocalDate from, LocalDate to);

    // View by Category (BENEFICIARY, BILL_PAYMENT, TOP_UP)
    public List<Transaction> getByCategory(TransactionCategory category);

    //View by SubCategory (ELECTRICITY, GAS, MOBILE_RECHARGE, etc.)
    public List<Transaction> getBySubCategory(TransactionSubCategory subCategory);

    // View Customer's Transactions by Category
    public List<Transaction> getCustomerTransactionsByCategory(Long custId, TransactionCategory category);

    //View Customer's Transactions by SubCategory
    public List<Transaction> getCustomerTransactionsBySubCategory(Long custId, TransactionSubCategory subCategory);

	List<Transaction> viewTransactionByMonth(int month, int year);

}
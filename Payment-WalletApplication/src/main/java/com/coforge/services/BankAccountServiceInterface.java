package com.coforge.services;

import java.util.List;

import com.coforge.dtos.BankAccountDto;
import com.coforge.entities.BankAccount;

public interface BankAccountServiceInterface {
	public List<BankAccountDto> getAllBankAccountsOfCustomer();
	public List<BankAccount> getAllBankAccounts();
	public BankAccount getBankAccountByAccountId(long bankAccountId);
	public BankAccount saveBankAccount(BankAccount bankAccount);
	public BankAccount updateBankAccount(BankAccount bankAccount);
	public BankAccount debitFromBankAccount(long bankAccountId,double amount);
	public String transferToWallet(double amount,long bankAccountId);
	public void deleteBankAccount(long bankAccountId);
}

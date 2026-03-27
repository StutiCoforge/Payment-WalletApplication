package com.coforge.services;

import java.util.List;

import com.coforge.entities.BankAccount;

public interface BankAccountServiceInterface {
	public List<BankAccount> getAllBankAccounts();
	public BankAccount getBankAccountByAccountId(long bankAccountId);
	public BankAccount saveBankAccount(BankAccount bankAccount);
	public BankAccount updateBankAccount(BankAccount bankAccount);
	public void deleteBankAccount(long bankAccountId);
}

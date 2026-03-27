package com.coforge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coforge.daos.BankAccountDao;
import com.coforge.entities.BankAccount;
import com.coforge.exception.BankAccountNotFoundException;

@Service
public class BankAccountService implements BankAccountServiceInterface {
	@Autowired
	BankAccountDao bankAccountDao;
	
	@Override
	public List<BankAccount> getAllBankAccounts() {
		return bankAccountDao.getAllBankAccounts();
	}

	@Override
	public BankAccount getBankAccountByAccountId(long bankAccountId) {
		return bankAccountDao.getBankAccountByAccountId(bankAccountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
	}

	@Override
	public BankAccount saveBankAccount(BankAccount bankAccount) {
		return bankAccountDao.saveBankAccount(bankAccount);
	}

	@Override
	public BankAccount updateBankAccount(BankAccount bankAccount) {
		getBankAccountByAccountId(bankAccount.getBankAccountId());
		return bankAccountDao.saveBankAccount(bankAccount);
	}

	@Override
	public void deleteBankAccount(long bankAccountId) {
		getBankAccountByAccountId(bankAccountId);
		bankAccountDao.deleteBankAccount(bankAccountId);
	}

}

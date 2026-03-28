package com.coforge.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.coforge.daos.BankAccountDao;
import com.coforge.dtos.BankAccountDto;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.entities.BankAccount;
import com.coforge.entities.Customer;
import com.coforge.entities.Wallet;
import com.coforge.exception.BankAccountInsufficientBalanceException;
import com.coforge.exception.BankAccountNotFoundException;

@Service
public class BankAccountService implements BankAccountServiceInterface {
	@Autowired
	BankAccountDao bankAccountDao;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	WalletService walletService;
	
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
		Customer customer = customerService.getById(customerDto.getCustId());		

		bankAccount.setBalance(10000);
		bankAccount.setCustomer(customer);
		customerService.addBankAccount(customer.getCustId(), bankAccount);
		
		return bankAccountDao.saveBankAccount(bankAccount);
	}

	@Override
	public BankAccount updateBankAccount(BankAccount bankAccount) {
		getBankAccountByAccountId(bankAccount.getBankAccountId());
		return bankAccountDao.saveBankAccount(bankAccount);
	}
	
	public String transferToWallet(double amount,long bankAccountId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
//		Customer customer = customerService.getById(customerDto.getCustId());	
		Wallet wallet = walletService.getWalletByCustomerId(customerDto.getCustId());
		
		walletService.credit(wallet.getWalletId(), BigDecimal.valueOf(amount));
		
		debitFromBankAccount(bankAccountId,amount);
		
		return amount+"Rs. Transferred";
	}

//	@Override
//	public BankAccount debitMoneyFromBankAccount(BankAccount bankAccount) {
//		getBankAccountByAccountId(bankAccount.getBankAccountId());
//		return bankAccountDao.saveBankAccount(bankAccount);
//	}
	
	@Override
	public BankAccount debitFromBankAccount(long bankAccountId,double amount) {
		BankAccount bankAccount = getBankAccountByAccountId(bankAccountId);
		if(bankAccount.getBalance()<amount) {
			throw new BankAccountInsufficientBalanceException("Insuficient Balance");
		}
		bankAccount.setBalance(bankAccount.getBalance()-amount);
		return bankAccountDao.saveBankAccount(bankAccount);
	}

	@Override
	public void deleteBankAccount(long bankAccountId) {
		getBankAccountByAccountId(bankAccountId);
		bankAccountDao.deleteBankAccount(bankAccountId);
	}

	@Override
	public List<BankAccountDto> getAllBankAccountsOfCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
		Customer customer = customerService.getById(customerDto.getCustId());
		
		List<BankAccountDto> banks = customer.getBankAccounts().stream().map((b)->new BankAccountDto(b.getBankAccountId(),b.getAccountNo(),b.getIfscCode(),b.getBankname(),b.getBalance())).collect(Collectors.toList());
		return banks;
	}

}

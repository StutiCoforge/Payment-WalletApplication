package com.coforge.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.dtos.BankAccountDto;
import com.coforge.entities.BankAccount;
import com.coforge.entities.Wallet;
import com.coforge.services.BankAccountService;

@RestController
@RequestMapping("/auth/bankAccount")
public class BankAccountController {
	@Autowired
	BankAccountService bankAccountService;
	
	@GetMapping("/")
	public ResponseEntity<List<BankAccountDto>> getBankAccounts(){
		return new ResponseEntity<>(bankAccountService.getAllBankAccountsOfCustomer(),HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<BankAccountDto> addBankAccounts(@RequestBody BankAccount bankAccount){
		BankAccount bank = bankAccountService.saveBankAccount(bankAccount);
		return new ResponseEntity<>(new BankAccountDto(bank.getBankAccountId(),bank.getAccountNo(),bank.getIfscCode(),bank.getBankname(),bank.getBalance()),HttpStatus.OK);
	}
	
	@PostMapping("/transferToWallet/{bankAccountId}")
    public ResponseEntity<String> topUpWallet(@Param("amount") double amount,@PathVariable("bankAccountId") long bankAccountId) {
        String response = bankAccountService.transferToWallet(amount,bankAccountId);
        
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}

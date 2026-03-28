package com.coforge.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import com.coforge.dao.StudentDao;
import com.coforge.daos.BeneficiaryDao;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.entities.Beneficiary;
import com.coforge.entities.Customer;
import com.coforge.entities.Transaction;
import com.coforge.entities.Wallet;
//import com.coforge.entities.Student;
//import com.coforge.exceptions.InvalidDobFormatException;
//import com.coforge.exceptions.StudentNotFoundException;
import com.coforge.exception.BeneficiaryException;
import com.coforge.repositories.BeneficiaryRepo;

@Service
public class BeneficiaryService implements BeneficiaryServiceInterface{
	@Autowired     
	BeneficiaryDao dao;

	@Autowired
	WalletServiceImpl walletService;

	@Autowired
	CustomerService customerService;

    @Autowired
    private BeneficiaryRepo beneficiaryRepository;

			@Override
		public List<Beneficiary> getAllBeneficiary() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
			Wallet wallet = walletService.getWalletByCustomerId(customer.getCustId());
				
			return walletService.getWalletBeneficiaries(wallet.getWalletId());
		}

		@Override
		public Beneficiary addBeneficiary(Beneficiary beneficiary) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomerJWTTokenDto customer = (CustomerJWTTokenDto) auth.getPrincipal();
			Wallet wallet = walletService.getWalletByCustomerId(customer.getCustId());
//			Beneficiary b = dao.saveBeneficiary(beneficiary);
//			System.out.println(wallet.getWalletId());
//			System.out.println("okkk");
			walletService.addBeneficiary(wallet.getWalletId(),beneficiary);
			
			return beneficiary;
		}
		@Override
		public Beneficiary updateBeneficiary(Beneficiary beneficiary) {
			

			return dao.saveBeneficiary(beneficiary);
		}
		@Override
		public Beneficiary getBeneficiaryById(long bid) {
			// TODO Auto-generated method stub
			
			try {
				return dao.getBeneficiaryById(bid).orElseThrow(()-> new BeneficiaryException("no beneficiary found with this id"+bid));
			} catch (BeneficiaryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		
		}

		
		@Override
		public void deleteBeneficiary(long bid) {
			// TODO Auto-generated method stub
			Beneficiary exBeneficiary=dao.getBeneficiaryById(bid).orElseThrow(()-> new BeneficiaryException("no beneficiary found with this id"+bid));
	        if(exBeneficiary!=null) {
	        	dao.deleteBeneficiary(bid);
	        	System.out.println("beneficiary deleted successfully");
	        }
	        else {
	        	System.out.println("no beneficiary present");
	        }
		}

	    @Override
	    public Optional<Beneficiary> findByMobileNumber(String mobileNumber) {
	        return dao.findByMobileNumber(mobileNumber);
	    }

	    
	    @Autowired
	    private TransactionService transactionService;
	 
	  
	 
	    public String sendMoney(String mobileNumber, double amount) {
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
			
			Customer customer = customerService.getById(customerDto.getCustId());
			Wallet wallet = walletService.getWalletByCustomerId(customerDto.getCustId());
	        // 1. Validate Beneficiary
	        Beneficiary beneficiary = beneficiaryRepository.findByMobileNumber(mobileNumber)
	                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
	 
	        walletService.debit(wallet.getWalletId(), BigDecimal.valueOf(amount));
	        
	        String discription="Transfer to"+beneficiary.getBeneficiaryName();
	        Transaction trans= new Transaction("DEBIT",amount,customer,discription);
	        Transaction transaction = transactionService.addTransaction(trans);
	        
	        try {
	           
	            	 
	        	transaction.setTransactionStatus("SUCCESS");
	            transactionService.updateTransaction(transaction);
	                   
	           
	 
	        } catch (Exception e) {
	 
	        	transaction.setTransactionStatus("FAILED");

	            transactionService.updateTransaction(transaction);
	            
	        }
	 
	        return "Transaction processed";



}

		@Override
		public Optional<Beneficiary> findByBeneficiaryName(String beneficiaryName) {
			// TODO Auto-generated method stub
	        return dao.findByBeneficiaryName(beneficiaryName);

		}}

package com.coforge.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.coforge.dao.StudentDao;
import com.coforge.daos.BeneficiaryDao;
import com.coforge.entities.Beneficiary;
import com.coforge.entities.Customer;
import com.coforge.entities.Transaction;
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
    private BeneficiaryRepo beneficiaryRepository;

			@Override
		public List<Beneficiary> getAllBeneficiary() {
			// TODO Auto-generated method stub
			return dao.getAllBeneficiary();
		}

		@Override
		public Beneficiary addBeneficiary(Beneficiary beneficiary) {
			// TODO Auto-generated method stub

		            // storing back as String or save LocalDate if field changed

			return dao.saveBeneficiary(beneficiary);
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
		public void deleteBeneficiary(long bid) throws BeneficiaryException {
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
	 
	  
	 
	    public String sendMoney(Customer customer, Long beneficiaryId, double amount) {
	 
	        // 1. Validate Beneficiary
	        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
	                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
	 
	       	    String discription="Transfer to"+beneficiary.getBeneficiaryName();
	        Transaction trans= new Transaction("DEBIT",amount,customer,discription);
	        Transaction transaction = transactionService.addTransaction(trans);
	        
	        try {
	           
	            	 
	            trans.setTransactionStatus("SUCCESS");
	            transactionService.updateTransaction(trans);
	                   
	           
	 
	        } catch (Exception e) {
	 
	            trans.setTransactionStatus("FAILED");

	            transactionService.updateTransaction(trans);
	            
	        }
	 
	        return "Transaction processed";



}

		@Override
		public Optional<Beneficiary> findByBeneficiaryName(String beneficiaryName) {
			// TODO Auto-generated method stub
	        return dao.findByBeneficiaryName(beneficiaryName);

		}}

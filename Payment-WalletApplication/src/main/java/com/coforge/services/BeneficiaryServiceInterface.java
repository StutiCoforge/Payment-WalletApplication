package com.coforge.services;

import java.util.List;
import java.util.Optional;

import com.coforge.entities.Beneficiary;
import com.coforge.exception.BeneficiaryException;

public interface BeneficiaryServiceInterface {

public List<Beneficiary> getAllBeneficiary();

public Beneficiary addBeneficiary(Beneficiary beneficiary);
		
public Beneficiary getBeneficiaryById(long id);
	
public Beneficiary updateBeneficiary(Beneficiary beneficiary);

public Optional<Beneficiary> findByMobileNumber(String mobileNumber);
public Optional<Beneficiary> findByBeneficiaryName(String beneficiaryName);

public	void deleteBeneficiary(long id) throws BeneficiaryException;

}

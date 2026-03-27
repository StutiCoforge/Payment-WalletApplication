package com.coforge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.entities.Beneficiary;
import com.coforge.services.BeneficiaryService;


@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {
	    @Autowired
	   private BeneficiaryService service;
		
		@GetMapping("/beneficiary")
		public ResponseEntity<List<Beneficiary>> getAllBeneficiary() {
			return new ResponseEntity<>(service.getAllBeneficiary(),HttpStatus.OK) ;
		}
		
		@PostMapping("/beneficiary")
		public ResponseEntity<Beneficiary> saveBeneficiary(@RequestBody Beneficiary beneficiary) {


	    //Student student = new Student();
	   
	    //student.setDob(service.parseDob(student.getDob())); // manual DOB parsing

			return new ResponseEntity<Beneficiary>(service.addBeneficiary(beneficiary),HttpStatus.CREATED);
		}
		@GetMapping("/beneficiary/{cid}")
		public ResponseEntity<Beneficiary> getBeneficiaryById(@PathVariable("cid")long cid) {
			return new ResponseEntity<Beneficiary>(service.getBeneficiaryById(cid),HttpStatus.OK);
		}

		@DeleteMapping("/beneficiary/{cid}")
		public ResponseEntity<String> deleteBeneficiary(@PathVariable("cid") long cid) {
		  
			service.deleteBeneficiary(cid);
			return new ResponseEntity<>("Beneficiary deleted successfully",HttpStatus.OK);
		}
	
	
	


		@GetMapping("/mobile/{mobileNumber}")
		public ResponseEntity<Beneficiary> getBeneficiaryByMobile(@PathVariable String mobileNumber) {

		    return service.findByMobileNumber(mobileNumber)
		            .map(ResponseEntity::ok)
		            .orElse(ResponseEntity.notFound().build());
		}
}

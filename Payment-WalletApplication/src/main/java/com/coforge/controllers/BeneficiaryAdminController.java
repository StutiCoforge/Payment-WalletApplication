package com.coforge.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coforge.entities.Beneficiary;
import com.coforge.services.BeneficiaryAdminService;

@RestController
@RequestMapping("/admin/beneficiaries")
public class BeneficiaryAdminController {

    @Autowired
    private BeneficiaryAdminService adminService;

    
    @GetMapping
    public ResponseEntity<List<Beneficiary>> getAllBeneficiaries() {
        return ResponseEntity.ok(adminService.viewAllBeneficiaries());
    }

    
    @GetMapping("/{beneficiaryId}")
    public ResponseEntity<Beneficiary> getBeneficiaryById(@PathVariable long beneficiaryId) {
        return ResponseEntity.ok(adminService.viewBeneficiaryById(beneficiaryId));
    }

  
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Beneficiary>> getCustomerBeneficiaries(@PathVariable long customerId) {
        return ResponseEntity.ok(adminService.viewBeneficiariesOfCustomer(customerId));
    }

   
    @DeleteMapping("/{beneficiaryId}")
    public ResponseEntity<String> deleteBeneficiary(@PathVariable long beneficiaryId) {
        String response = adminService.deleteBeneficiary(beneficiaryId);
        return ResponseEntity.ok(response);
    }
}
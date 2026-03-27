package com.coforge.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.dtos.BillPaymentRequestDto;
import com.coforge.dtos.BillPaymentResponseDto;
import com.coforge.services.BillPaymentService;

@RestController
@RequestMapping("/billPayments")
public class BillPaymentController {
	@Autowired
	BillPaymentService billPaymentService;
	
	@PostMapping("/create")
	public ResponseEntity<Map<String,Object>> createBillPayment(@RequestBody BillPaymentRequestDto billPaymentRequestDto){
		System.out.println(billPaymentRequestDto);
		billPaymentService.createBillPayment(billPaymentRequestDto);
		
		Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Bill Paid successfully");
        response.put("timestamp", System.currentTimeMillis());
		
        return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{billId}")
	public ResponseEntity<BillPaymentResponseDto> getBillPayment(@PathVariable("billId") long billId){
		BillPaymentResponseDto billPayment =  billPaymentService.getBillPaymentByBillId(billId);
		
//		Map<String, Object> response = new HashMap<>();
//        response.put("status", "success");
//        response.put("message", "Bill Payment fetched successfully");
//        response.put("timestamp", System.currentTimeMillis());
		
        return new ResponseEntity<>(billPayment,HttpStatus.CREATED);
	}
}

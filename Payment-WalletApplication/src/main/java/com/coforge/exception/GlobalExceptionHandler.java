package com.coforge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BillPaymentNotFoundException.class)
	public ResponseEntity<String> handleBillPaymentNotFoundException(BillPaymentNotFoundException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidBillPaymentDataException.class)
	public ResponseEntity<String> handleInvalidBillPaymentDataException(InvalidBillPaymentDataException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BankAccountNotFoundException.class)
	public ResponseEntity<String> handleBankAccountNotFoundException(BankAccountNotFoundException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
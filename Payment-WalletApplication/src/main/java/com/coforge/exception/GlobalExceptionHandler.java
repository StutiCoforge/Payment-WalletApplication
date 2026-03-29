package com.coforge.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.coforge.security.JwtAuthenticationFilter;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@ExceptionHandler(BillPaymentNotFoundException.class)
	public ResponseEntity<String> handleBillPaymentNotFoundException(BillPaymentNotFoundException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidBillPaymentDataException.class)
	public ResponseEntity<String> handleInvalidBillPaymentDataException(InvalidBillPaymentDataException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BankAccountNotFoundException.class)
	public ResponseEntity<String> handleBankAccountNotFoundException(BankAccountNotFoundException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BankAccountInsufficientBalanceException.class)
	public ResponseEntity<String> handleBankAccountInsufficientBalanceException(BankAccountInsufficientBalanceException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<String> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<String> handleWalletNotFoundException(WalletNotFoundException e){
		logger.error("Error: {}",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
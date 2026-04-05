package com.coforge.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.dtos.CustomerDto;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.dtos.LoginRequestDto;
import com.coforge.dtos.LoginResponseDto;
import com.coforge.dtos.SendOtpRequestDto;
import com.coforge.entities.Customer;
import com.coforge.security.JwtUtil;
import com.coforge.services.CustomerService;
import com.coforge.services.EmailService;
@RestController
@CrossOrigin
public class CustomerController
{
	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	@GetMapping("/admin/customers")
	public ResponseEntity<List<CustomerDto>> getAllCustomer()
	{
		return new ResponseEntity<> (customerService.getAllCustomer(), HttpStatus.OK);
	}
	@GetMapping("/admin/customers/{customerId}")
	public ResponseEntity<CustomerDto> getById(@PathVariable long customerId)
	{
		return new ResponseEntity<>(customerService.getCustomerDtoById(customerId), HttpStatus.OK);
	}
	@PostMapping("/customers/signup")
	public ResponseEntity<LoginResponseDto> saveCustomer(@RequestBody Customer customer)
	{	
		Customer savedCustomer = customerService.saveCustomer(customer);
		String token = jwtUtil.generateToken(new CustomerJWTTokenDto(savedCustomer.getCustId(),savedCustomer.getCustName(),savedCustomer.getMobileNumber(),savedCustomer.getEmail(),savedCustomer.getRole()));
		return new ResponseEntity<> (new LoginResponseDto(token, savedCustomer.getEmail()), HttpStatus.CREATED);
	}
	@PostMapping("/customers/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto)
	{
		Customer customer = customerService.login(loginDto.getEmail(), loginDto.getPwd());
		String token = jwtUtil.generateToken(new CustomerJWTTokenDto(customer.getCustId(),customer.getCustName(),customer.getMobileNumber(),customer.getEmail(),customer.getRole()));
		return new ResponseEntity<> (new LoginResponseDto(token, customer.getEmail()), HttpStatus.OK);
	}

	@PostMapping("/customers/send-otp")
	public ResponseEntity<Map<String,String>> login(@RequestBody SendOtpRequestDto otpRequestDto)
	{
		String otp = emailService.sendOtp(otpRequestDto.getEmail());
		return new ResponseEntity<> (Map.of("message","Otp Sent","otp",otp), HttpStatus.OK);
	}

	@GetMapping("/auth/customers/getDetails")
	public ResponseEntity<CustomerDto> getDetails()
	{
		CustomerDto customer = customerService.getDetails();
		return new ResponseEntity<> (customer, HttpStatus.OK);
	}
	
	@PutMapping("/admin/customers/{customerId}")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody Customer customer, @PathVariable long customerId)
	{
		return new ResponseEntity<> (customerService.updateCustomer(customer, customerId), HttpStatus.OK);
	}
	@DeleteMapping("/admin/customers/{customerId}")
	public ResponseEntity<Map<String,String>> deleteCustomer(@PathVariable("customerId") long customerId)
	{
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>(Map.of("message","Customer Deleted Successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/admin/customers/search")
	public ResponseEntity<List<CustomerDto>> searchCustomers(@Param("query") String query)
	{
		List<CustomerDto> customers = customerService.findCustomerByQuery(query);
		return new ResponseEntity<> (customers, HttpStatus.OK);
	}
}
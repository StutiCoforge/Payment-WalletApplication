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
import org.springframework.web.bind.annotation.RestController;

import com.coforge.dtos.CustomerDto;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.dtos.LoginRequestDto;
import com.coforge.dtos.LoginResponseDto;
import com.coforge.entities.Customer;
import com.coforge.security.JwtUtil;
import com.coforge.services.CustomerService;
@RestController
public class CustomerController
{
	@Autowired
	private CustomerService customerService;
	@Autowired
	private JwtUtil jwtUtil;
	@GetMapping("/auth/customers")
	public ResponseEntity<List<CustomerDto>> getAllCustomer()
	{
		return new ResponseEntity<> (customerService.getAllCustomer(), HttpStatus.OK);
	}
	@GetMapping("/customers{customerId}")
	public ResponseEntity<Customer> getById(@PathVariable long customerId)
	{
		return new ResponseEntity<>(customerService.getById(customerId), HttpStatus.OK);
	}
	@PostMapping("/customers/signup")
	public ResponseEntity<LoginResponseDto> saveCustomer(@RequestBody Customer customer)
	{	
		Customer savedCustomer = customerService.saveCustomer(customer);
		String token = jwtUtil.generateToken(new CustomerJWTTokenDto(savedCustomer.getCustId(),savedCustomer.getCustName(),savedCustomer.getMobileNumber(),savedCustomer.getEmail()));
		return new ResponseEntity<> (new LoginResponseDto(token, savedCustomer.getEmail()), HttpStatus.CREATED);
	}
	@PostMapping("/customers/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto)
	{
		Customer customer = customerService.login(loginDto.getEmail(), loginDto.getPwd());
		String token = jwtUtil.generateToken(new CustomerJWTTokenDto(customer.getCustId(),customer.getCustName(),customer.getMobileNumber(),customer.getEmail()));
		return new ResponseEntity<> (new LoginResponseDto(token, customer.getEmail()), HttpStatus.OK);
	}
	@PutMapping("/customers/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable long customerId)
	{
		return new ResponseEntity<> (customerService.updateCustomer(customer, customerId), HttpStatus.OK);
	}
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<String> deleteCustomer(long customerId)
	{
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>("Customer Deleted Successfully", HttpStatus.OK);
	}
}
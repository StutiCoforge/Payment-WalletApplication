package com.coforge.services;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.coforge.daos.CustomerDao;
import com.coforge.dtos.CustomerDto;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.dtos.WalletDto;
import com.coforge.entities.BankAccount;
import com.coforge.entities.Customer;
import com.coforge.entities.Wallet;
import com.coforge.exception.CustomerAlreadyExistsException;
import com.coforge.exception.CustomerNotFoundException;

@Service
public class CustomerService implements CustomerServiceInterface
{
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private WalletServiceImpl walletService;
	
	@Override
	public List<CustomerDto> getAllCustomer()
	{
		List<CustomerDto> customers =  customerDao.getAllCustomer().stream().map(
				(c)->{
					Wallet wallet = walletService.getWalletByCustomerId(c.getCustId());
					WalletDto w = new WalletDto(wallet.getWalletId(),wallet.getBalance(),wallet.getBeneficiary());
					return new CustomerDto(c.getCustId(),c.getCustName(),c.getMobileNumber(),c.getEmail(),w);	
				}).collect(Collectors.toList());
		
		return customers;
	}
	
	@Override
	public CustomerDto getDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
		Customer customer = customerDao.getById(customerDto.getCustId()).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerDto.getCustId()));
		Wallet wallet = walletService.getWalletByCustomerId(customer.getCustId());
		WalletDto w = new WalletDto(wallet.getWalletId(),wallet.getBalance(),wallet.getBeneficiary());
		return new CustomerDto(customer.getCustId(),customer.getCustName(),customer.getMobileNumber(),customer.getEmail(),w);
	}

	@Override
	public Customer saveCustomer(Customer customer)
	{	
		List<Customer> cList = customerDao.findByEmailOrMobileNumber(customer.getEmail(), customer.getMobileNumber());
		if(cList.size()>0) {
			throw new CustomerAlreadyExistsException("Customer with this email or mobile number already exists. Please login.");
		}
		Wallet wallet = walletService.createWallet(BigDecimal.ZERO);
		customer.setWallet(wallet);
		return customerDao.saveCustomer(customer);
	}

	@Override
	public Customer getById(long customerId)
	{
		Customer customer = customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
		
		return customer;
	}

	@Override
	public CustomerDto getCustomerDtoById(long customerId)
	{
		Customer customer = customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
		
		Wallet wallet = walletService.getWalletByCustomerId(customer.getCustId());
		WalletDto w = new WalletDto(wallet.getWalletId(),wallet.getBalance(),wallet.getBeneficiary());
		return new CustomerDto(customer.getCustId(),customer.getCustName(),customer.getMobileNumber(),customer.getEmail(),w);
	}
	
	@Override
	public CustomerDto updateCustomer(Customer customer, long customerId)
	{
		Customer customer1 = customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
		customer1.setCustName(customer.getCustName());
		customer1.setMobileNumber(customer.getMobileNumber());
		customer1.setPwd(customer.getPwd());
		Customer updatedCustomer =customerDao.updateCustomer(customer1, customerId);
		
		Wallet wallet = walletService.getWalletByCustomerId(updatedCustomer.getCustId());
		WalletDto w = new WalletDto(wallet.getWalletId(),wallet.getBalance(),wallet.getBeneficiary());
		return new CustomerDto(updatedCustomer.getCustId(),updatedCustomer.getCustName(),updatedCustomer.getMobileNumber(),updatedCustomer.getEmail(),w); 
	}
	
	@Override
	public Customer addBankAccount(long customerId,BankAccount bankAccount)
	{
		Customer customer = customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
		customer.addBankAccount(bankAccount);
//		System.out.println(customer.getBankAccounts());
		return customerDao.updateCustomer(customer, customerId);
	}

	@Override
	public void deleteCustomer(long customerId)
	{
		customerDao.deleteCustomer(customerId);
	}

	@Override
	public Customer login(String email, String pwd)
	{	
		return customerDao.findByEmailAndPwd(email, pwd).orElseThrow(() -> new CustomerNotFoundException("Invalid Email or Password"));
	}

	@Override
	public List<Customer> findByEmail(String email) {
		return customerDao.findByEmail(email);
	}

	@Override
	public List<Customer> findByMobileNumber(String mobileNumber) {
		return customerDao.getAllCustomer();
	}

	@Override
	public List<Customer> findByEmailOrMobileNumber(String email, String mobileNumber) {
		return customerDao.findByEmailOrMobileNumber(email, mobileNumber);
	}

	@Override
	public List<CustomerDto> findCustomerByQuery(String query) {
		return customerDao.findCustomerByQuery(query).stream().map(
				(c)->{
					Wallet wallet = walletService.getWalletByCustomerId(c.getCustId());
					WalletDto w = new WalletDto(wallet.getWalletId(),wallet.getBalance(),wallet.getBeneficiary());
					return new CustomerDto(c.getCustId(),c.getCustName(),c.getMobileNumber(),c.getEmail(),w);	
				}).collect(Collectors.toList());
	}
}
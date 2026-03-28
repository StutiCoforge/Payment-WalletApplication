package com.coforge.services;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coforge.daos.CustomerDao;
import com.coforge.dtos.CustomerDto;
import com.coforge.entities.BankAccount;
import com.coforge.entities.Customer;
import com.coforge.entities.Wallet;
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
		List<CustomerDto> customers =  customerDao.getAllCustomer().stream().map((c)->new CustomerDto(c.getCustName(),c.getMobileNumber(),c.getEmail())).collect(Collectors.toList());
		
		return customers;
	}

	@Override
	public Customer saveCustomer(Customer customer)
	{	
		System.out.println(customer);
		Wallet wallet = walletService.createWallet(BigDecimal.ZERO);
		customer.setWallet(wallet);
		return customerDao.saveCustomer(customer);
	}

	@Override
	public Customer getById(long customerId)
	{
		return customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
	}
	@Override
	public Customer updateCustomer(Customer customer, long customerId)
	{
		Customer customer1 = customerDao.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found" + customerId));
		customer1.setCustName(customer.getCustName());
		customer1.setMobileNumber(customer.getMobileNumber());
		customer1.setPwd(customer.getPwd());
		return customerDao.updateCustomer(customer1, customerId);
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
}
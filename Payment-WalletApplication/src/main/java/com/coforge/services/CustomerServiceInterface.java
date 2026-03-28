package com.coforge.services;
import java.util.List;

import com.coforge.dtos.CustomerDto;
import com.coforge.entities.BankAccount;
import com.coforge.entities.Customer;

public interface CustomerServiceInterface
{
	public List<CustomerDto> getAllCustomer();
	public Customer saveCustomer(Customer customer);
	public Customer getById(long customerId);
	public Customer updateCustomer(Customer customer, long id);
	public void deleteCustomer(long itemId);
	public Customer addBankAccount(long customerId,BankAccount bankAccount);
	public Customer login(String email, String pwd);
}
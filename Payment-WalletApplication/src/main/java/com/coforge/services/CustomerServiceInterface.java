package com.coforge.services;
import java.util.List;
import com.coforge.entities.Customer;

public interface CustomerServiceInterface
{
	public List<Customer> getAllCustomer();
	public Customer saveCustomer(Customer customer);
	public Customer getById(long customerId);
	public Customer updateCustomer(Customer customer, long id);
	public void deleteCustomer(long itemId);
	public Customer login(String email, String pwd);
}
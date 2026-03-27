package com.coforge.daos;
import java.util.List;
import java.util.Optional;
import com.coforge.entities.Customer;
public interface CustomerDaoInterface
{
	public List<Customer> getAllCustomer();
	public Customer saveCustomer(Customer customer);
	public Optional<Customer> getById(long customerId);
	public Customer updateCustomer(Customer customer, long id);
	public void deleteCustomer(long itemId);
	public Optional<Customer> findByEmailAndPwd(String email, String pwd);
}
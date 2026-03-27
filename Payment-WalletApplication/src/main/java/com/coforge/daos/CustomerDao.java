package com.coforge.daos;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.coforge.entities.Customer;
import com.coforge.repositories.CustomerRepository;
@Repository
public class CustomerDao implements CustomerDaoInterface
{
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public List<Customer> getAllCustomer()
	{
		return customerRepository.findAll();
	}
	@Override
	public Customer saveCustomer(Customer customer)
	{
		return customerRepository.save(customer);
	}
	@Override
	public Optional<Customer> getById(long customerId)
	{
		return customerRepository.findById(customerId);
	}
	@Override
	public Customer updateCustomer(Customer customer, long id)
	{
		return customerRepository.save(customer);
	}
	@Override
	public void deleteCustomer(long customerId)
	{
		customerRepository.deleteById(customerId);
	}
	@Override
	public Optional<Customer> findByEmailAndPwd(String email, String pwd)
	{
		return customerRepository.findByEmailAndPwd(email, pwd);
	}
}
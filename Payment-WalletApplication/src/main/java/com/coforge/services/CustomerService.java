package com.coforge.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coforge.daos.CustomerDao;
import com.coforge.entities.Customer;
import com.coforge.exception.CustomerNotFoundException;

@Service
public class CustomerService implements CustomerServiceInterface
{
	@Autowired
	private CustomerDao customerDao;
	@Override
	public List<Customer> getAllCustomer()
	{
		return customerDao.getAllCustomer();
	}

	@Override
	public Customer saveCustomer(Customer customer)
	{
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
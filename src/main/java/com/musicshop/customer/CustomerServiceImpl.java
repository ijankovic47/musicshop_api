package com.musicshop.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;

	@Autowired
	public CustomerServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Optional<Customer> readByEmail(String email) {
		
		return Optional.ofNullable(customerDao.readById(email));
	}

	@Override
	public void active(String email, boolean active) {
		
		Customer c=customerDao.readById(email);
		c.setActive(active);
	}

	@Override
	public String create(Customer customer) {
		return customerDao.create(customer);
	}
}

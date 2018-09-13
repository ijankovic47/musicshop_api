package com.musicshop.customer;

import org.springframework.stereotype.Repository;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class CustomerDaoImpl extends GenericDaoImpl<Customer, String> implements CustomerDao{

	public CustomerDaoImpl() {
		super(Customer.class);
	}
}

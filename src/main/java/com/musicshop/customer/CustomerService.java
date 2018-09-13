package com.musicshop.customer;

import java.util.Optional;

public interface CustomerService {

	Optional<Customer> readByEmail(String email);
	void active(String email, boolean active);
	String create(Customer customer);
}

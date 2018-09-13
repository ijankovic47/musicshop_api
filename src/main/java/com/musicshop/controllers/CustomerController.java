package com.musicshop.controllers;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.musicshop.customer.Customer;
import com.musicshop.customer.CustomerService;
import com.musicshop.exceptions.NoSuchEntityException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value="/{email:.+}", produces="application/json", method=RequestMethod.GET)
	public ResponseEntity<?> readByEmail(@PathVariable("email") String email) throws NoSuchEntityException {

		Optional<Customer> c = customerService.readByEmail(email);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		}
		throw new NoSuchEntityException("No customer found for email '" + email + "'");
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Customer customer){
		
		String email = customerService.create(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/customer/{id}").build().expand(email)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	@PatchMapping(value="/{email:.+}")
	public ResponseEntity<?> setActive(@PathVariable("email") String email, @RequestBody Boolean active){
		
		customerService.active(email, active);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}

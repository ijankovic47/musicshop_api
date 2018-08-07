package com.musicshop.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.musicshop.exceptions.NoSuchEntityException;
import com.musicshop.family.Family;
import com.musicshop.family.FamilyService;

@RestController
@RequestMapping("/family")
public class FamilyController {

	private FamilyService familyService;
	
	@Autowired
	public FamilyController(FamilyService familyService) {
		this.familyService=familyService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Family family) throws URISyntaxException {

		Integer id = familyService.create(family);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/family/{id}").build().expand(id)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> read(@RequestParam(name="brandId", required=false) Integer brandId) {

		return ResponseEntity.ok(familyService.read(brandId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readById(@PathVariable("id") Integer id) throws NoSuchEntityException {
		
		Optional<Family> family = familyService.readById(id);
		if (family.isPresent()) {
			return ResponseEntity.ok(family.get());
		}
		throw new NoSuchEntityException("No family found for id = " + id);
	}
}

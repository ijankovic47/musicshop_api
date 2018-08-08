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
import com.musicshop.type.TypeDto;
import com.musicshop.type.TypeService;

@RestController
@RequestMapping("/type")
public class TypeController {

	public TypeService typeService;

	@Autowired
	public TypeController(TypeService typeService) {

		this.typeService = typeService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody TypeDto type) throws URISyntaxException {

		Integer id = typeService.create(type);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/type/{id}").build().expand(id)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> read(@RequestParam("familyId") Integer familyId, @RequestParam(name="brandId", required=false) Integer brandId) {

		return ResponseEntity.ok(typeService.read(familyId, brandId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readById(@PathVariable("id") Integer id) throws NoSuchEntityException {

		Optional<TypeDto> type = typeService.readById(id);
		if (type.isPresent()) {
			return ResponseEntity.ok(type.get());
		}
		throw new NoSuchEntityException("No type found for id = " + id);
	}
}

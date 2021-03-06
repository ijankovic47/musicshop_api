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
import com.musicshop.property.PropertyDto;
import com.musicshop.property.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyConroller {

	private PropertyService propertyService;

	@Autowired
	public PropertyConroller(PropertyService propeprtyService) {
		this.propertyService = propeprtyService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody PropertyDto property) throws URISyntaxException {

		Integer id = propertyService.create(property);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/type/{id}").build().expand(id)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readById(@PathVariable("id") Integer id) throws NoSuchEntityException {

		Optional<PropertyDto> property = propertyService.readById(id);
		if (property.isPresent()) {
			return ResponseEntity.ok(property.get());
		}
		throw new NoSuchEntityException("No property found for id = " + id);
	}
	@RequestMapping()
	public ResponseEntity<?> read(@RequestParam("typeId") Integer typeId,
			@RequestParam(name = "brandId", required = false) Integer brandId,
			@RequestParam(name = "priceMin", required = false) Double priceMin,
			@RequestParam(name = "priceMax", required = false) Double priceMax,
			@RequestParam(name = "havingInstruments", defaultValue = "false") boolean havingInstruments) {

		return ResponseEntity.ok(propertyService.read(typeId, brandId, priceMin, priceMax, havingInstruments));
	}
	
	@RequestMapping(value = "/{propertyId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> edit(@RequestBody PropertyDto property, @PathVariable("propertyId") Integer id) {

		propertyService.edit(id, property);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{propertyId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("propertyId") Integer id) {

		propertyService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}

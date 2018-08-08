package com.musicshop.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.musicshop.property.PropertyDto;
import com.musicshop.property.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyConroller {

	private PropertyService propeprtyService;
	
	@Autowired
	public PropertyConroller(PropertyService propeprtyService) {
		this.propeprtyService=propeprtyService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody PropertyDto property) throws URISyntaxException {

		Integer id = propeprtyService.create(property);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/type/{id}").build().expand(id)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping()
	public ResponseEntity<?> read(@RequestParam("typeId") Integer typeId, @RequestParam(name="brandId", required=false) Integer brandId){
		
		return ResponseEntity.ok(propeprtyService.read(typeId, brandId));
	}
}

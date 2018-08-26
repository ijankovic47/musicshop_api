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
import com.musicshop.brand.Brand;
import com.musicshop.brand.BrandService;
import com.musicshop.brand.BrandSort;
import com.musicshop.exceptions.NoSuchEntityException;

@RestController
@RequestMapping("/brand")
public class BrandController {

	private BrandService brandService;

	@Autowired
	public BrandController(BrandService brandService) {

		this.brandService = brandService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Brand brand) throws URISyntaxException {

		Integer id = brandService.create(brand);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/brand/{id}").build().expand(id)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> read(@RequestParam(name = "familyId", required = false) Integer familyId,
			@RequestParam(name = "typeId", required = false) Integer typeId,
			@RequestParam(name = "propertyId", required = false) Integer propertyId,
			@RequestParam(name = "priceMin", required = false) Double priceMin,
			@RequestParam(name = "priceMax", required = false) Double priceMax,
			@RequestParam(name = "havingInstruments", defaultValue="false") boolean havingInstruments,
			@RequestParam(name = "sort", required=false) BrandSort brandSort) {

		return ResponseEntity.ok(brandService.read(familyId, typeId, propertyId, priceMin, priceMax, havingInstruments, brandSort));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readById(@PathVariable("id") Integer id) throws NoSuchEntityException {

		Optional<Brand> brand = brandService.readById(id);
		if (brand.isPresent()) {
			return ResponseEntity.ok(brand.get());
		}
		throw new NoSuchEntityException("No brand found for id = " + id);
	}
	
	@RequestMapping(value = "/{brandId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> edit(@RequestBody Brand brand, @PathVariable("brandId") Integer id) {

		brandService.edit(id, brand);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{brandId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("brandId") Integer id) {

		brandService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}

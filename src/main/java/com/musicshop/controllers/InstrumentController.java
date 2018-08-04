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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.musicshop.exceptions.NoSuchEntityException;
import com.musicshop.instrument.InstrumentDto;
import com.musicshop.instrument.InstrumentService;

@RestController
@RequestMapping("/instrument")
public class InstrumentController {

	private InstrumentService instrumentService;

	@Autowired
	public InstrumentController(InstrumentService instrumentService) {

		this.instrumentService = instrumentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody InstrumentDto instrument) throws URISyntaxException {

		Integer id = instrumentService.create(instrument);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/instrument/{id}").build()
				.expand(id).toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> readAll() {

		return ResponseEntity.ok(instrumentService.read(null, null));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readById(@PathVariable("id") Integer id) throws NoSuchEntityException {

		Optional<InstrumentDto> instrument = instrumentService.readById(id);
		if (instrument.isPresent()) {
			return ResponseEntity.ok(instrument.get());
		}
		throw new NoSuchEntityException("No instrument found for id = " + id);
	}
}

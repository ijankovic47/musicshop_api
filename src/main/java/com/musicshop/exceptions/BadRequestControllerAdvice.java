package com.musicshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestControllerAdvice {

	@ExceptionHandler(NoSuchEntityException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
	public NoSuchEntityException noeEntity(NoSuchEntityException noEntityException) {
		
		System.out.println(noEntityException.getMessage());
		return noEntityException;
	}
}

package com.musicshop.exceptions;

public class NoSuchEntityException extends Exception{

	private static final long serialVersionUID = 7861835874874993843L;

	public NoSuchEntityException(String message) {

		super(message);
	}
}

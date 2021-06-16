package com.kajal.spring.exception;

import org.springframework.http.HttpStatus;

public class NoSuchNameException extends Exception {

	
	
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;

	public HttpStatus getStatus() {
		return this.status;
	}

	public NoSuchNameException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}



}


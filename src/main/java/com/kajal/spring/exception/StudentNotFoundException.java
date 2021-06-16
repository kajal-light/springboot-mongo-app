package com.kajal.spring.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;

	public HttpStatus getStatus() {
		return this.status;
	}

	public StudentNotFoundException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}



}
	
	
	


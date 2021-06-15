package com.kajal.spring.exception.response;

import org.springframework.http.HttpStatus;

//you can put any information you want in ApiErrorResponse 
public class ApiErrorResponse {

 private final HttpStatus status;
 private final String message;

 public ApiErrorResponse(HttpStatus status, String message) {
     this.status= status;
     this.message = message;
 }

 public HttpStatus getStatus() { 
     return this.status; 
 }

 public String getMessage() {
     return this.message;
 }


}


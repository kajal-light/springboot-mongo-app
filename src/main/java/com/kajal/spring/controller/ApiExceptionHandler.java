package com.kajal.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.response.ApiErrorResponse;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ StudentExistsException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(StudentExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
    
    
}




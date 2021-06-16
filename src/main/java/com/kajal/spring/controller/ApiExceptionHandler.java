package com.kajal.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.NoDepartmentfound;
import com.kajal.spring.exception.NoSuchNameException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.StudentNotFoundException;
import com.kajal.spring.exception.response.ApiErrorResponse;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ StudentExistsException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(StudentExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler({ StudentNotFoundException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(StudentNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
    
    @ExceptionHandler({ NoSuchNameException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(NoSuchNameException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
    @ExceptionHandler({ NoDepartmentfound.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(NoDepartmentfound ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
    @ExceptionHandler({ InvalidEmailException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(InvalidEmailException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
      
}




package com.justlife.hs.clean.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.justlife.hs.clean.response.Status;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Status> handleResourceNotFound(ResourceNotFoundException ex) {
		Status response = new Status("404", "FAILURE", "RESOURCE_NOT_FOUND", ex.getMessage(), Instant.now());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Status> handleBadRequest(BadRequestException ex) {
		Status response = new Status("400", "FAILURE", "INVALID_INPUT", ex.getMessage(), Instant.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<Status> handleInternalServer(BadRequestException ex) {
		Status response = new Status("500", "FAILURE", "INTERNAL_SERVER_ERROR", ex.getMessage(), Instant.now());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

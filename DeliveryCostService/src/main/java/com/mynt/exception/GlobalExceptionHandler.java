package com.mynt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(WeightExceededLimitException.class)
	public ResponseEntity<ExceptionInfo> handleWeightExceededLimitException(WeightExceededLimitException e){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(VoucherApiFailureException.class)
	public ResponseEntity<ExceptionInfo> handleVoucherApiFailureException(VoucherApiFailureException e){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceAccessException.class)
	public ResponseEntity<ExceptionInfo> handleResourceAccessException(ResourceAccessException e){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
		return new ResponseEntity<>(exceptionInfo, HttpStatus.SERVICE_UNAVAILABLE);
	}
} 

package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ExceptionInfo> handleNoRecordFoundException(NoRecordFoundException ex){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(exceptionInfo, HttpStatus.NOT_FOUND);
	}
}

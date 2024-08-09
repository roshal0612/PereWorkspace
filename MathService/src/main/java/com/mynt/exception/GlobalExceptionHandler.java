package com.mynt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mynt.dto.ExceptionInfo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	private ResponseEntity<ExceptionInfo> handleRecordNotFoundException (RecordNotFoundException ex){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(exceptionInfo, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DenominatorZeroException.class)
	private ResponseEntity<ExceptionInfo> handleDenominatorZeroException (DenominatorZeroException ex){
		ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
	} 
}

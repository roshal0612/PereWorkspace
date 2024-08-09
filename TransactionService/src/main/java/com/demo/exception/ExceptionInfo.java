package com.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionInfo {

	private Integer statusCode;
	
	private String message;
}

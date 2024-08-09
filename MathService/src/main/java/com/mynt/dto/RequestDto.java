package com.mynt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestDto {

	@NotNull
	private Integer numerator;
	
	@NotNull
	private Integer denominator;
}

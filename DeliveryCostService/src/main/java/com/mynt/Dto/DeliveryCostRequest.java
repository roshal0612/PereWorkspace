package com.mynt.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCostRequest {

	@NotNull @Min(value = 1, message = "Value cannot be negative")
	private Double weight;
	
	@NotNull @Min(value = 1, message = "Value cannot be negative")
	private Double height;
	
	@NotNull @Min(value = 1, message = "Value cannot be negative")
	private Double width;
	
	@NotNull @Min(value = 1, message = "Value cannot be negative")
	private Double length;
	
	private String voucherCode;
}

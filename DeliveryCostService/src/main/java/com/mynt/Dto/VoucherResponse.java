package com.mynt.Dto;

import lombok.Data;

@Data
public class VoucherResponse {

	private String code;
	
	private double discount;
	
	private String expiry;
}

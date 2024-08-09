package com.mynt.service;

import com.mynt.dto.RequestDto;
import com.mynt.dto.ResponseDto;

public interface DivisionService {

	public boolean divide (RequestDto request);
	
	public ResponseDto getQuotient(Integer opId);
}

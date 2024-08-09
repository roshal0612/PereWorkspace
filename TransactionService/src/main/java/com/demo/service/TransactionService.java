package com.demo.service;

import com.demo.dto.RequestDto;
import com.demo.dto.ResponseDto;

public interface TransactionService {

	public boolean addTransaction(RequestDto request);
	
	public ResponseDto getTransactionWithLeastTimeStamp();
}

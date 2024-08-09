package com.mynt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mynt.dto.RequestDto;
import com.mynt.dto.ResponseDto;
import com.mynt.entity.DivisionOperation;
import com.mynt.exception.RecordNotFoundException;
import com.mynt.repo.DivisionOpRepository;
import com.mynt.service.DivisionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DivisionServiceImpl implements DivisionService {

	@Autowired
	private DivisionOpRepository divisionRepo;
	
	public boolean divide (RequestDto request) {
		
		int numerator = request.getNumerator();
		int denominator = request.getDenominator();
		
		DivisionOperation divisionOperation = DivisionOperation.builder()
				.numerator(numerator)
				.denominator(denominator)
				.quotient(numerator / denominator)
				.remainder(numerator % denominator)
				.build();
		
		log.info("Division Completed Successfully");
		DivisionOperation save = divisionRepo.save(divisionOperation);		
		return !(save.getId() == null);
	}
	
	public ResponseDto getQuotient(Integer opId) {
		
		DivisionOperation divisionOperation = divisionRepo.findById(opId)
				.orElseThrow(() -> {
					log.error("Record not found");
					throw new RecordNotFoundException("No record found for given id");
					});
		
		log.info("Record retrieved successfully");
		
		ResponseDto response = new ResponseDto();
		response.setQuotient(divisionOperation.getQuotient());
		
		return response;
	}
}

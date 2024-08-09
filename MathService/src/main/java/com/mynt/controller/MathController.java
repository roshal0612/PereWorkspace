package com.mynt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.dto.RequestDto;
import com.mynt.dto.ResponseDto;
import com.mynt.exception.DenominatorZeroException;
import com.mynt.service.DivisionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MathController {

	private DivisionService divisionService;

	public MathController(DivisionService divisionService) {
		this.divisionService = divisionService;
	}

	@PostMapping("/division")
	private ResponseEntity<String> divide(@RequestBody RequestDto request) {

		log.info("Division Request received");

		if (request.getDenominator() == 0) {
			log.error("Error : Denominator cannot be zero");
			throw new DenominatorZeroException("Denominator cannot be zero");
		}

		if (divisionService.divide(request)) {
			log.info("DivsionOperation saved successfully");
			return new ResponseEntity<>("Operation Completed successfully", HttpStatus.CREATED);
		} else {
			log.info("Division could not be completed Successfully");
			return new ResponseEntity<>("Operation could not be completed successfully", HttpStatus.OK);
		}
	}

	@GetMapping("/division/{id}/quotient")
	private ResponseEntity<ResponseDto> getQuotient(@PathVariable Integer id) {

		ResponseDto quotient = divisionService.getQuotient(id);
		
		log.info("Quotient Received");
		return new ResponseEntity<>(quotient, HttpStatus.OK);
	}

}

package com.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.RequestDto;
import com.demo.dto.ResponseDto;
import com.demo.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping()
	public ResponseEntity<String> addtransaction(@RequestBody RequestDto request) {
		log.info("Recieved request to add transaction");
		if (transactionService.addTransaction(request)) {
			log.info("Transaction saved successfully");
			return new ResponseEntity<>("Transaction saved successfully", HttpStatus.CREATED);
		} else {
			log.info("Transaction could not be saved");
			return new ResponseEntity<>("Transaction saved successfully", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/lowest-timestamp")
	public ResponseEntity<ResponseDto> getTransactionWithLeastTimeStamp() {
		ResponseDto response = transactionService.getTransactionWithLeastTimeStamp();
		log.info("Retrieved transaction successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}

package com.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.RequestDto;
import com.demo.dto.ResponseDto;
import com.demo.entity.Transaction;
import com.demo.exception.NoRecordFoundException;
import com.demo.repo.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;

	public boolean addTransaction(RequestDto request) {
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(request, transaction);

		Transaction save = transactionRepo.save(transaction);
		log.info("Transaction saved to database");

		return !(save.getId() == null);
	}

	public ResponseDto getTransactionWithLeastTimeStamp() {
		Transaction transaction = transactionRepo.findFirstByOrderByTimeStampOrderAscAmountDsc().orElseThrow(() -> {
			log.error("NoRecordFoundExceptionOccured");
			throw new NoRecordFoundException("There are no transactions to display");
		});

		transactionRepo.deleteById(transaction.getId());
		
		log.info("Transaction Deleted successfully");

		ResponseDto response = new ResponseDto();
		BeanUtils.copyProperties(transaction, response);

		return response;
	}
}

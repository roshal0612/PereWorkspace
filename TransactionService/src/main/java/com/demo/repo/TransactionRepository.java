package com.demo.repo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Serializable>{
	
	@Query("SELECT t FROM Transaction t ORDER BY t.timeStamp ASC, t.amount DESC LIMIT 1")
	Optional<Transaction> findFirstByOrderByTimeStampOrderAscAmountDsc();

}

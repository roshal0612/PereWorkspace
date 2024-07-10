package com.dailyCodeBuffer.paymentService.command.api.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Serializable>{

}

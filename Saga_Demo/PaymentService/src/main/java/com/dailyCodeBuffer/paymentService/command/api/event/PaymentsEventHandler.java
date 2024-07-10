package com.dailyCodeBuffer.paymentService.command.api.event;

import java.time.LocalDate;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.dailyCodeBuffer.events.PaymentCancelledEvent;
import com.dailyCodeBuffer.events.PaymentProcessedEvent;
import com.dailyCodeBuffer.paymentService.command.api.data.Payment;
import com.dailyCodeBuffer.paymentService.command.api.data.PaymentRepository;

@Component
public class PaymentsEventHandler {

	private PaymentRepository paymentRepository;
	
	public PaymentsEventHandler(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@EventHandler
	public void on (PaymentProcessedEvent event) {
		Payment payment 
		 = Payment.builder()
		 .paymentId(event.getPaymentId())
		 .orderId(event.getOrderId())
		 .paymentStatus("COMPLETED")
		 .timeStamp(LocalDate.now())
		 .build();
		
		paymentRepository.save(payment);
	}
	
	@EventHandler
	public void on (PaymentCancelledEvent event) {
		
		Payment payment = paymentRepository.findById(event.getPaymentId()).get();
		payment.setPaymentStatus(event.getPaymentStatus());
		paymentRepository.save(payment);
	}
}

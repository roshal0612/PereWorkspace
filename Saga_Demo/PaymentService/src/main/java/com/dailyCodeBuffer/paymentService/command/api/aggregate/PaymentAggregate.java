package com.dailyCodeBuffer.paymentService.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.dailyCodeBuffer.command.CancelPaymentCommand;
import com.dailyCodeBuffer.command.ValidatePaymentCommand;
import com.dailyCodeBuffer.events.PaymentCancelledEvent;
import com.dailyCodeBuffer.events.PaymentProcessedEvent;
import com.dailyCodeBuffer.model.CardDetails;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class PaymentAggregate {

	@AggregateIdentifier
	private String paymentId;

	private String orderId;

	private CardDetails cardDetails;
	
	private String paymentStatus;

	public PaymentAggregate() {
	}

	@CommandHandler
	public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {

		// Validate Payment Details
		// publish payment processed event

		log.info("Executing ValidatePaymentCommand for " + "Order Id: {} and Payment Id: {}",
				validatePaymentCommand.getOrderId(), 
				validatePaymentCommand.getPaymentId());
		
		PaymentProcessedEvent paymentProcessedEvent = 
				new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId());
		
		AggregateLifecycle.apply(paymentProcessedEvent);
		
		log.info("PaymentProcessedEvent Applied");
		
	}

	@EventSourcingHandler
	public void on (PaymentProcessedEvent event) {
		this.paymentId = event.getPaymentId();
		this.orderId = event.getOrderId();
	}
	
	@CommandHandler
	public void handle (CancelPaymentCommand cancelPaymentCommand) {
		
		PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
		BeanUtils.copyProperties(cancelPaymentCommand, paymentCancelledEvent);
		
		AggregateLifecycle.apply(paymentCancelledEvent);
	}
	
	@EventSourcingHandler
	public void on (PaymentCancelledEvent event) {
		this.paymentStatus = event.getPaymentStatus();
	}
}

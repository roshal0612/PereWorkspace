package com.dailyCodeBuffer.command.api.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.dailyCodeBuffer.command.CancelOrderCommand;
import com.dailyCodeBuffer.command.CancelPaymentCommand;
import com.dailyCodeBuffer.command.CompleteOrderCommand;
import com.dailyCodeBuffer.command.ShipOrderCommand;
import com.dailyCodeBuffer.command.ValidatePaymentCommand;
import com.dailyCodeBuffer.command.api.events.OrderCreatedEvent;
import com.dailyCodeBuffer.events.OrderCancelledEvent;
import com.dailyCodeBuffer.events.OrderCompletedEvent;
import com.dailyCodeBuffer.events.OrderShippedEvent;
import com.dailyCodeBuffer.events.PaymentCancelledEvent;
import com.dailyCodeBuffer.events.PaymentProcessedEvent;
import com.dailyCodeBuffer.model.User;
import com.dailyCodeBuffer.query.GetUserPaymentDetailsQuery;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderProcessingSaga {

	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	
	public OrderProcessingSaga() {
    }
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	private void handle (OrderCreatedEvent event) {
		log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());
	
		GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());
		
		User user = null;
		
		try {
			user = queryGateway.query(getUserPaymentDetailsQuery,
					ResponseTypes.instanceOf(User.class))
					.join();
		} catch(Exception e) {
			log.error(e.getMessage());
			//compensation transaction
			cancelOrderCommand(event.getOrderId());
			
		}
		
		ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
				.cardDetails(user.getCardDetails())
				.orderId(event.getOrderId())
				.paymentId(UUID.randomUUID().toString())
				.build();
		
		commandGateway.sendAndWait(validatePaymentCommand);
	}
	
	private void cancelOrderCommand(String orderId) {

		CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId);
		commandGateway.send(cancelOrderCommand);
	}

	@SagaEventHandler(associationProperty = "orderId")
	private void handle(PaymentProcessedEvent event) {
		
		log.info("PaymentProcessedEvent in Saga for Order Id : {}", event.getOrderId());
		
		try {
			
			if (true)
				throw new Exception();
			
			ShipOrderCommand shipOrderCommand
					= ShipOrderCommand
					.builder()
					.shipmentId(UUID.randomUUID().toString())
					.orderId(event.getOrderId())
					.build();
			commandGateway.send(shipOrderCommand);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			// compensating transaction
			cancelPaymentCommand(event);
		}
		
	}
	
	private void cancelPaymentCommand(PaymentProcessedEvent event) {
		
		CancelPaymentCommand cancelPaymentCommand = 
				new CancelPaymentCommand(event.getPaymentId(), event.getOrderId());
		
		commandGateway.send(cancelPaymentCommand);
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle (OrderShippedEvent event) {
		
		log.info("OrderShippedEvent in Saga for Order Id : {}", event.getOrderId());
		
		CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
				.orderId(event.getOrderId())
				.orderStatus("COMPLETED")
				.build();
		
		commandGateway.send(completeOrderCommand);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle (OrderCompletedEvent event) {
		
		log.info("OrderCompletedEvent in Saga for Order Id : {}", event.getOrderId());
		
		// send invoice command to notification service
		
	}
	
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle (PaymentCancelledEvent event) {
		log.info("PaymentCancelledEvent in saga for order Id : {} ", 
				event.getOrderId());
		cancelOrderCommand(event.getOrderId());
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle (OrderCancelledEvent event) {
		log.info("OrderCancelledEvent in saga for order Id : {} ", 
				event.getOrderId());
	}
}
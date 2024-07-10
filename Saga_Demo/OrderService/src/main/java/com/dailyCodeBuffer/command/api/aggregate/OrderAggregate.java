package com.dailyCodeBuffer.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.dailyCodeBuffer.command.CancelOrderCommand;
import com.dailyCodeBuffer.command.CompleteOrderCommand;
import com.dailyCodeBuffer.command.api.command.CreateOrderCommand;
import com.dailyCodeBuffer.command.api.events.OrderCreatedEvent;
import com.dailyCodeBuffer.events.OrderCancelledEvent;
import com.dailyCodeBuffer.events.OrderCompletedEvent;

@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;

	private String productId;

	private String userId;

	private String addressId;

	private Integer quantity;

	private String orderStatus;
	
	public OrderAggregate() {
	}
	
	@CommandHandler
	public OrderAggregate (CreateOrderCommand createOrdercommand) {
		
		// validate business logic
		
		OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
		
		BeanUtils.copyProperties(createOrdercommand, orderCreatedEvent);
		
		AggregateLifecycle.apply(orderCreatedEvent);
	}
	
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent event) {
	
		this.orderId = event.getOrderId();
		this.orderStatus = event.getOrderStatus();
		this.addressId = event.getAddressId();
		this.userId = event.getUserId();
		this.quantity = event.getQuantity();
		this.productId = event.getProductId();
	}
	
	@CommandHandler
	public void handle (CompleteOrderCommand completeOrderCommand) {
		//Validate the command
		//publish order completed event
		
		OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
				.orderStatus(completeOrderCommand.getOrderStatus())
				.orderId(completeOrderCommand.getOrderId())
				.build();
		
		AggregateLifecycle.apply(orderCompletedEvent);
	}
	
	@EventSourcingHandler
	public void on (OrderCompletedEvent event) {
		this.orderStatus = event.getOrderStatus();
	}
	
	@CommandHandler
	public void handle (CancelOrderCommand cancelOrderCommand) {
		
		OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
		BeanUtils.copyProperties(cancelOrderCommand, orderCancelledEvent);
		
		AggregateLifecycle.apply(orderCancelledEvent);
		
	}
	
	@EventSourcingHandler
	public void on (OrderCancelledEvent event) {
		this.orderStatus = event.getOrderStatus();
	}
}

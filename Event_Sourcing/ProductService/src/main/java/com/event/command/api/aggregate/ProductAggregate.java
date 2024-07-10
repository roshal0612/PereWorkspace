package com.event.command.api.aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.event.command.api.commands.CreateProductCommand;
import com.event.command.api.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {

	@AggregateIdentifier 
	private String productId;

	private String name;

	private BigDecimal price;

	private Integer quantity;
	
	public ProductAggregate() {
		super();
	}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {

	 // Perform Validations
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
	
		AggregateLifecycle.apply(productCreatedEvent);
	}
	
	@EventSourcingHandler
	public void on (ProductCreatedEvent productCreatedEvent) {
		this.quantity = productCreatedEvent.getQuantity();
		this.productId = productCreatedEvent.getProductId();
		this.price = productCreatedEvent.getPrice();
		this.name = productCreatedEvent.getName();
	}
	
	
}

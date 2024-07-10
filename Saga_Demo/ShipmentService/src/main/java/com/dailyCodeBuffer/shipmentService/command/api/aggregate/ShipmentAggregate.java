package com.dailyCodeBuffer.shipmentService.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.dailyCodeBuffer.command.ShipOrderCommand;
import com.dailyCodeBuffer.events.OrderShippedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class ShipmentAggregate {

	@AggregateIdentifier
	private String shipmentId;
	private String orderId;
	private String shipmentStatus;
	
	public ShipmentAggregate() {
	}

	@CommandHandler
	public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
	
		//validate the command
		//publish OrderShippedEvent
		
		log.info("Executing ShipOrderCommand for " + "Order Id: {} and shipment Id: {}",
				shipOrderCommand.getOrderId(),
				shipOrderCommand.getShipmentId());
		
		OrderShippedEvent orderShippedEvent = 
				OrderShippedEvent.builder()
				.shipmentId(shipOrderCommand.getShipmentId())
				.orderId(shipOrderCommand.getOrderId())
				.shipmentStatus("COMPLETED")
				.build();
		
		AggregateLifecycle.apply(orderShippedEvent);
		
		log.info("OrderShippedEvent Applied");
	}
	
	@EventSourcingHandler
	public void on (OrderShippedEvent event) {
		this.orderId = event.getOrderId();
		this.shipmentId = event.getShipmentId();
		this.shipmentStatus = event.getShipmentStatus();
	}
}

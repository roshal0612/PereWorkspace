package com.dailyCodeBuffer.shipmentService.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dailyCodeBuffer.events.OrderShippedEvent;
import com.dailyCodeBuffer.shipmentService.command.api.data.Shipment;
import com.dailyCodeBuffer.shipmentService.command.api.data.ShipmentRepository;

@Component
public class ShipmentsEventHandler {

	@Autowired
	private ShipmentRepository shipmentRepo;
	
	@EventHandler
	public void on (OrderShippedEvent event) {
		Shipment shipment = new Shipment();
		
		BeanUtils.copyProperties(event, shipment);
		shipmentRepo.save(shipment);
	}
}

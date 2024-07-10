package com.dailyCodeBuffer.command.api.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyCodeBuffer.command.api.command.CreateOrderCommand;
import com.dailyCodeBuffer.command.api.model.OrderRestModel;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public String createOrder(@RequestBody OrderRestModel orderRestModel) {
		
		String orderId = UUID.randomUUID().toString();
		
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
				.orderId(orderId)
				.userId(orderRestModel.getUserId())
				.addressId(orderRestModel.getAddressId())
				.productId(orderRestModel.getProductId())
				.quantity(orderRestModel.getQuantity())
				.orderStatus("CREATED")
				.build();
		
		commandGateway.sendAndWait(createOrderCommand);
		
		return "Order Created";
	}
}

package com.dailyCodeBuffer.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.dailyCodeBuffer.command.api.data.Order;
import com.dailyCodeBuffer.command.api.data.OrderRepository;
import com.dailyCodeBuffer.events.OrderCancelledEvent;
import com.dailyCodeBuffer.events.OrderCompletedEvent;

@Component
public class OrderEventsHandler {

	
	private OrderRepository orderRepo;

	public OrderEventsHandler(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@EventHandler
	public void on (OrderCreatedEvent event) {
		Order order = new Order();
		BeanUtils.copyProperties(event, order);
		orderRepo.save(order);
	}
	
	@EventHandler
	public void on (OrderCompletedEvent event) {
		
		Order order = orderRepo.findById(event.getOrderId()).get();
		order.setOrderStatus(event.getOrderStatus());
		orderRepo.save(order);
	}
	
	@EventHandler
	public void on (OrderCancelledEvent event) {
		
		Order order = orderRepo.findById(event.getOrderId()).get();
		order.setOrderStatus(event.getOrderStatus());
		orderRepo.save(order);
	}
}

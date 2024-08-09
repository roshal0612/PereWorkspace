package com.mynt.cor.handler;

import com.mynt.Dto.DeliveryCostRequest;

public abstract class DeliveryCostHandler {

	protected DeliveryCostHandler next;
	
	public void setNext(DeliveryCostHandler next) {
		this.next = next;
	}
	
	public abstract double handleDeliveryCostRequest(DeliveryCostRequest request);
}

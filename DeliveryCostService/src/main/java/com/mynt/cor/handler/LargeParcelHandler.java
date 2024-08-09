package com.mynt.cor.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mynt.Dto.DeliveryCostRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LargeParcelHandler extends DeliveryCostHandler {
	
	@Value("${parcel.large.cost}")
	private double largeParcelPHP;

	@Override
	public double handleDeliveryCostRequest(DeliveryCostRequest request) {
		double volume = request.getHeight() * request.getLength() * request.getWidth();
		double cost = largeParcelPHP * volume;
		log.info("Large parcel cost calculated: {} PHP", cost);
		return cost;
	}

}

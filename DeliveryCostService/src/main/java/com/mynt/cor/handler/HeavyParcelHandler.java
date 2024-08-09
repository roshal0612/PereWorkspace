package com.mynt.cor.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mynt.Dto.DeliveryCostRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HeavyParcelHandler extends DeliveryCostHandler{
	
	@Value("${parcel.heavy.weight}")
	private double heavyWeightLimit;
	
	@Value("${parcel.heavy.cost}")
	private double heavyParcelPHP;

	@Override
	public double handleDeliveryCostRequest(DeliveryCostRequest request) {
		if (request.getWeight() > heavyWeightLimit) {
			double cost = heavyParcelPHP * request.getWeight(); 
			log.info("Heavy parcel cost calculated: {} PHP", cost);
			return cost;
		} else if (next != null) {
			return next.handleDeliveryCostRequest(request);
		}
		return 0;
	}

}

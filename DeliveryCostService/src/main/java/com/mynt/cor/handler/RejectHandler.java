package com.mynt.cor.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.exception.WeightExceededLimitException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RejectHandler extends DeliveryCostHandler{
	
	@Value("${parcel.reject.weight}")
	private double maxWeightLimit;

	@Override
	public double handleDeliveryCostRequest(DeliveryCostRequest request) {
		
		if (request.getWeight() > maxWeightLimit) {
			log.error("Parcel rejected: Weight exceeds {}kg", maxWeightLimit);
			throw new WeightExceededLimitException("Parcel weight limit Exceeded");
		} else if (this.next != null) {
			return next.handleDeliveryCostRequest(request);
		}
		return 0;
	}

}

package com.mynt.cor.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mynt.Dto.DeliveryCostRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmallParcelHandler extends DeliveryCostHandler {

	@Value("${parcel.small.volume}")
	private double maxVolumeLimit;

	@Value("${parcel.small.cost}")
	private double smallParcelPHP;

	@Override
	public double handleDeliveryCostRequest(DeliveryCostRequest request) {
		double volume = request.getHeight() * request.getLength() * request.getWidth();
		if (volume < maxVolumeLimit) {
			double cost = smallParcelPHP * volume;
			log.info("Small parcel cost calculated: {} PHP", cost);
			return cost;
		} else if (this.next != null) {
			return next.handleDeliveryCostRequest(request);
		}
		return 0;
	}

}

package com.mynt.service;

import org.springframework.stereotype.Service;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.Dto.VoucherResponse;
import com.mynt.cor.handler.HeavyParcelHandler;
import com.mynt.cor.handler.LargeParcelHandler;
import com.mynt.cor.handler.MediumParcelHandler;
import com.mynt.cor.handler.RejectHandler;
import com.mynt.cor.handler.SmallParcelHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryCostServiceImpl implements DeliveryCostService{
	
	private final RejectHandler rejectHandler;
	private final HeavyParcelHandler heavyParcelHandler;
	private final SmallParcelHandler smallParcelHandler;
	private final MediumParcelHandler mediumParcelHandler;
	private final LargeParcelHandler largeParcelHandler;
	private final VoucherService voucherService;

	public DeliveryCostServiceImpl(RejectHandler rejectHandler, HeavyParcelHandler heavyParcelHandler,
			SmallParcelHandler smallParcelHandler, MediumParcelHandler mediumParcelHandler,
			LargeParcelHandler largeParcelHandler, VoucherService voucherService) {
		this.rejectHandler = rejectHandler;
		this.heavyParcelHandler = heavyParcelHandler;
		this.smallParcelHandler = smallParcelHandler;
		this.mediumParcelHandler = mediumParcelHandler;
		this.largeParcelHandler = largeParcelHandler;
		this.voucherService = voucherService;
		
		// Set up the Chain Of Responsibility 
		rejectHandler.setNext(heavyParcelHandler);
		heavyParcelHandler.setNext(smallParcelHandler);
		smallParcelHandler.setNext(mediumParcelHandler);
		mediumParcelHandler.setNext(largeParcelHandler);
	}

	 /**
     * Calculates the delivery cost based on the parcel request.
     *
     * @param request the parcel request
     * @return calculated delivery cost
     */
	
	public double calculateDeliveryCost(DeliveryCostRequest request) {
		
		 log.info("Calculating delivery cost for parcel with weight: {} kg and dimensions: {}x{}x{} cm",
	                request.getWeight(), request.getHeight(), request.getWidth(), request.getLength());

		// Start the chain of responsibility to calculate the base cost
		double cost = rejectHandler.handleDeliveryCostRequest(request);
		
		// Apply discount if voucher code is present
		if (request.getVoucherCode() != null && !request.getVoucherCode().isEmpty()) {
			VoucherResponse voucher = voucherService.getVoucherDiscount(request.getVoucherCode());
			double discount = voucher.getDiscount() / 100;
			log.info("Applying discount of {}% ({} PHP) using voucher code: {}", voucher.getDiscount(), discount, request.getVoucherCode());
			cost -= discount;
		}
		
//		if (voucher == null || voucher.isExpired()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new VoucherResponse("Invalid voucher code"));
//        }
		 log.info("Total delivery cost calculated: {} PHP", cost);
		
		return cost;
	}
}

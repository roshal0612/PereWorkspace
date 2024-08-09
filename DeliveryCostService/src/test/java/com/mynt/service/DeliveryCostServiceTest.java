package com.mynt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.Dto.VoucherResponse;
import com.mynt.cor.handler.HeavyParcelHandler;
import com.mynt.cor.handler.LargeParcelHandler;
import com.mynt.cor.handler.MediumParcelHandler;
import com.mynt.cor.handler.RejectHandler;
import com.mynt.cor.handler.SmallParcelHandler;
import com.mynt.service.DeliveryCostServiceImpl;
import com.mynt.service.VoucherService;

public class DeliveryCostServiceTest {

	@InjectMocks
	private DeliveryCostServiceImpl deliveryCostService;
	
	@Mock
	private RejectHandler rejectHandler;
	
	@Mock
	private  HeavyParcelHandler heavyParcelHandler;
	
	@Mock
	private  SmallParcelHandler smallParcelHandler;
	
	@Mock
	private  MediumParcelHandler mediumParcelHandler;
	
	@Mock
	private  LargeParcelHandler largeParcelHandler;
	
	@Mock
	private  VoucherService voucherService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
        rejectHandler.setNext(heavyParcelHandler);
        heavyParcelHandler.setNext(smallParcelHandler);
        smallParcelHandler.setNext(mediumParcelHandler);
        mediumParcelHandler.setNext(largeParcelHandler);
	}
	
	@Test
	void calculateDeliveryCostWithoutVoucher() {
		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.weight(20.0)
				.height(25.0)
				.width(25.0)
				.length(5.0)
				.build();
		
		when(rejectHandler.handleDeliveryCostRequest(request)).thenReturn(400.0);
		
		double cost = deliveryCostService.calculateDeliveryCost(request);
		
		assertEquals(400.0, cost);
	}
	
	@Test
	void calculateDeliveryCostWithVoucher() {
		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.weight(20.0)
				.height(25.0)
				.width(25.0)
				.length(5.0)
				.voucherCode("MYNT")
				.build();
		
		VoucherResponse voucherResponse = new VoucherResponse();
		voucherResponse.setDiscount(10.0);
		
		when(rejectHandler.handleDeliveryCostRequest(request)).thenReturn(400.0);
		when(voucherService.getVoucherDiscount("MYNT")).thenReturn(voucherResponse);
		
		double cost = deliveryCostService.calculateDeliveryCost(request);
		
		assertEquals(360.0, cost);
	}
}

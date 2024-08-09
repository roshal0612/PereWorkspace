package com.mynt.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.cor.handler.LargeParcelHandler;
import com.mynt.cor.handler.MediumParcelHandler;

public class MediumParcelHandlerTest {

	@InjectMocks
	private MediumParcelHandler mediumParcelHandler;

	@Mock
	private LargeParcelHandler next;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mediumParcelHandler.setNext(next);
		ReflectionTestUtils.setField(mediumParcelHandler, "maxVolumeLimit", 2500.0);
		ReflectionTestUtils.setField(mediumParcelHandler, "mediumParcelPHP", 0.04);
	}

	@Test
	void handleDeliveryCostRequest_mediumParcel() {
		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.height(20.0).width(10.0).length(10.0).build();

		double expectedCost = 20.0 * 10.0 * 10.0 * 0.04;

		double cost = mediumParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(expectedCost, cost);
	}

	@Test
	void handleDeliveryCostRequest_notMediumParcel_withNextHandler() {
		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.height(30.0).width(30.0).length(30.0).build();

		double expectedNextCost = 150.0;

		when(next.handleDeliveryCostRequest(any(DeliveryCostRequest.class))).thenReturn(expectedNextCost);

		double cost = mediumParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(expectedNextCost, cost);
		verify(next, times(1)).handleDeliveryCostRequest(request);
	}

	@Test
	void handleDeliveryCostRequest_notMediumParcel_noNextHandler() {
		DeliveryCostRequest request = DeliveryCostRequest.builder().height(30.0).width(30.0).length(30.0).build();

		mediumParcelHandler.setNext(null);

		double result = mediumParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(0, result);
	}

	@Test
	void handleDeliveryCostRequest_delegateToNextHandler() {
		DeliveryCostRequest request = DeliveryCostRequest.builder().height(20.0).width(20.0).length(15.0).build();

		double expectedNextCost = 120.0;

		when(next.handleDeliveryCostRequest(any(DeliveryCostRequest.class))).thenReturn(expectedNextCost);

		double actualResult = mediumParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(expectedNextCost, actualResult);
		verify(next, times(1)).handleDeliveryCostRequest(request);
	}

	@Test
	void testHandleDeliveryCostRequest_noNextHandler_withinVolumeLimit() {
		DeliveryCostRequest request = DeliveryCostRequest.builder().height(20.0).width(10.0).length(10.0).build();

		mediumParcelHandler.setNext(null);

		double expectedCost = 20.0 * 10.0 * 10.0 * 0.04;

		double result = mediumParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(expectedCost, result);
	}
}

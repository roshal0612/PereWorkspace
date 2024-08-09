package com.mynt.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.cor.handler.LargeParcelHandler;

public class LargeParcelHandlerTest {

	@InjectMocks
	private LargeParcelHandler largeParcelHandler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(largeParcelHandler, "largeParcelPHP", 0.05);
	}

	@Test
	void handleDeliveryCostRequest_largeParcel() {
		DeliveryCostRequest request = DeliveryCostRequest.builder().height(30.0).width(30.0).length(30.0).build();

		double expectedCost = 30.0 * 30.0 * 30.0 * 0.05;

		double cost = largeParcelHandler.handleDeliveryCostRequest(request);

		assertEquals(expectedCost, cost);
	}

}

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
import com.mynt.cor.handler.MediumParcelHandler;
import com.mynt.cor.handler.SmallParcelHandler;

public class SmallParcelHandlerTest {
   
	@InjectMocks
    private SmallParcelHandler smallParcelHandler;

    @Mock
    private MediumParcelHandler next;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        smallParcelHandler.setNext(next);
        ReflectionTestUtils.setField(smallParcelHandler, "maxVolumeLimit", 1500.0);
        ReflectionTestUtils.setField(smallParcelHandler, "smallParcelPHP", 0.03);
    }

    @Test
    void handleDeliveryCostRequest_smallParcel() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .height(10.0)
                .width(10.0)
                .length(10.0)
                .build();
        
        double expectedCost = 10.0 * 10.0 * 10.0 * 0.03;
        
        double cost = smallParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(expectedCost, cost);
    }

    @Test
    void handleDeliveryCostRequest_notSmallParcel_withNextHandler() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .height(30.0)
                .width(30.0)
                .length(30.0)
                .build();
        
        double expectedNextCost = 100.0;

        when(next.handleDeliveryCostRequest(any(DeliveryCostRequest.class))).thenReturn(expectedNextCost);
        
        double cost = smallParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(expectedNextCost, cost);
        verify(next, times(1)).handleDeliveryCostRequest(request);
    }

    @Test
    void handleDeliveryCostRequest_notSmallParcel_noNextHandler() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .height(30.0)
                .width(30.0)
                .length(30.0)
                .build();
        
        smallParcelHandler.setNext(null);

        double result = smallParcelHandler.handleDeliveryCostRequest(request);

        assertEquals(0, result);
    }

    @Test
    void handleDeliveryCostRequest_delegateToNextHandler() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .height(20.0)
                .width(10.0)
                .length(10.0)
                .build();
        
        double expectedCost = 80.0;

        when(next.handleDeliveryCostRequest(any(DeliveryCostRequest.class))).thenReturn(expectedCost);
        
        double actualResult = smallParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(expectedCost, actualResult);
        verify(next, times(1)).handleDeliveryCostRequest(request);
    }

    @Test
    void handleDeliveryCostRequest_noNextHandler_withinVolumeLimit() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .height(10.0)
                .width(10.0)
                .length(10.0)
                .build();
        
        smallParcelHandler.setNext(null);

        double expectedCost = 10.0 * 10.0 * 10.0 * 0.03;
        
        double result = smallParcelHandler.handleDeliveryCostRequest(request);

        assertEquals(expectedCost, result);
    }
    
}

package com.mynt.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import com.mynt.cor.handler.HeavyParcelHandler;
import com.mynt.cor.handler.SmallParcelHandler;

public class HeavyParcelHandlerTest {
	
	@InjectMocks
    private HeavyParcelHandler heavyParcelHandler;
    
    @Mock
    private SmallParcelHandler next;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(heavyParcelHandler, "heavyWeightLimit", 10.0);
        ReflectionTestUtils.setField(heavyParcelHandler, "heavyParcelPHP", 20.0);
        heavyParcelHandler.setNext(next);
    }

    @Test
    void handleDeliveryCostRequest_heavyParcel() {
        DeliveryCostRequest request = new DeliveryCostRequest();
        request.setWeight(15.0);
        
        double cost = heavyParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(300.0, cost); // 15 * 20
        verify(next, never()).handleDeliveryCostRequest(any());
    }
    
    @Test
    void handleDeliveryCostRequest_notHeavyParcel_withNextHandler() {
        DeliveryCostRequest request = new DeliveryCostRequest();
        request.setWeight(8.0);
        
        when(next.handleDeliveryCostRequest(request)).thenReturn(100.0);
        
        double cost = heavyParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(100.0, cost);
        verify(next, times(1)).handleDeliveryCostRequest(request);
    }

    @Test
    void handleDeliveryCostRequest_notHeavyParcel_noNextHandler() {
        DeliveryCostRequest request = new DeliveryCostRequest();
        request.setWeight(8.0);
        
        heavyParcelHandler.setNext(null);

        double cost = heavyParcelHandler.handleDeliveryCostRequest(request);
        
        assertEquals(0, cost);
    }

}

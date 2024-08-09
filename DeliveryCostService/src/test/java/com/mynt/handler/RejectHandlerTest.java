package com.mynt.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.mynt.cor.handler.RejectHandler;
import com.mynt.exception.WeightExceededLimitException;

public class RejectHandlerTest {

	@InjectMocks
    private RejectHandler rejectHandler;
    
    @Mock
    private HeavyParcelHandler next;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(rejectHandler, "maxWeightLimit", 50.0);
        rejectHandler.setNext(next);
    }
    
    @Test
    void handleDeliveryCostRequest_pass() { // Weight Within Limit
        DeliveryCostRequest request = new DeliveryCostRequest();
        request.setWeight(20.0);
        
        when(next.handleDeliveryCostRequest(request)).thenReturn(400.0);
        
        double cost = rejectHandler.handleDeliveryCostRequest(request);
        
        assertEquals(400.0, cost);
        verify(next, times(1)).handleDeliveryCostRequest(request); // the next handler is called once when the weight is within the limit.
    }
    
    @Test
    void handleDeliveryCostRequest_reject() { // Weight Exceeding Max weight limit
        DeliveryCostRequest request = new DeliveryCostRequest();
        request.setWeight(60.0);
        
        WeightExceededLimitException thrown = assertThrows(WeightExceededLimitException.class, () -> {
            rejectHandler.handleDeliveryCostRequest(request);
        });
        
        assertEquals("Parcel weight limit Exceeded", thrown.getMessage());
    }
    
    @Test
    void handleDeliveryCostRequest_delegateToNextHandler() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .weight(5.0)
                .height(30.0)
                .width(30.0)
                .length(30.0)
                .build();
        
        when(next.handleDeliveryCostRequest(request)).thenReturn(20.0);
        
        double actualResult = rejectHandler.handleDeliveryCostRequest(request);
        
        assertEquals(20.0, actualResult);
        verify(next, times(1)).handleDeliveryCostRequest(request);
    }
    
    @Test
    void testHandleDeliveryCostRequest_noNextHandler() {
        DeliveryCostRequest request = DeliveryCostRequest.builder()
                .weight(5.0)
                .height(30.0)
                .width(30.0)
                .length(30.0)
                .build();
        
        rejectHandler.setNext(null);

        double result = rejectHandler.handleDeliveryCostRequest(request);

        assertEquals(0, result);
    }
	
}

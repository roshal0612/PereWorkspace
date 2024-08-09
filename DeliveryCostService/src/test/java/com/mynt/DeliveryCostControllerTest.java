package com.mynt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.Dto.DeliveryCostResponse;
import com.mynt.rest.DeliveryCostController;
import com.mynt.service.DeliveryCostServiceImpl;

public class DeliveryCostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private DeliveryCostController deliveryCostController;

	@Mock
	private DeliveryCostServiceImpl deliveryCostService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deliveryCostController)
                .build();
	}

	@Test
	void calculateDeliveryCost_success() throws Exception {
		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.weight(20.0)
				.height(25.0)
				.width(25.0)
				.length(5.0)
				.build();
		
		DeliveryCostResponse response = new DeliveryCostResponse();
        response.setDeliveryCost(400.0);

        when(deliveryCostService.calculateDeliveryCost(any(DeliveryCostRequest.class))).thenReturn(400.0);

        mockMvc.perform(post("/api/v1/parcels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"weight\": 10.0, \"height\": 30.0, \"width\": 30.0, \"length\": 30.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"deliveryCost\": 400.0}"))
                .andDo(print());
	}

	@Test
	void calculateDeliveryCost_invalidRequest() throws Exception {
		
		 mockMvc.perform(post("/api/v1/parcels")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"weight\": -10.0, \"height\": 30.0, \"width\": 30.0, \"length\": 30.0}"))
	                .andExpect(status().isBadRequest())
	                .andDo(print());
	}
}

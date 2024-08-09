package com.mynt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.exception.ExceptionInfo;
import com.mynt.exception.GlobalExceptionHandler;
import com.mynt.exception.VoucherApiFailureException;
import com.mynt.exception.WeightExceededLimitException;
import com.mynt.rest.DeliveryCostController;

@WebMvcTest(controllers = { DeliveryCostController.class, GlobalExceptionHandler.class })
public class GlobalExceptionHandlerTest {

//	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeliveryCostController deliveryCostController;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	ExceptionInfo exceptionInfo;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deliveryCostController)
	            .setControllerAdvice(new GlobalExceptionHandler())
	            .build();
		
		exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), "");
	}

	@Test
	void handleWeightExceededLimitException() throws Exception {

		DeliveryCostRequest request = DeliveryCostRequest.builder()
				.weight(60.0).height(20.0).width(10.0).length(10.0).build();

		when(deliveryCostController.calculateDeliveryCost(request))
				.thenThrow(new WeightExceededLimitException("Parcel weight limit exceeded"));

		ResultActions resultActions = mockMvc.perform(post("/api/v1/parcels")
				.contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exceptionInfo)));
		
		System.out.println("ResultAction" + resultActions.toString());

		resultActions
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.message").value("Parcel weight limit exceeded"));
	}

	@Test
	void handleVoucherApiFailureException() throws Exception {

		String voucherCode = "INVALID_CODE";

		DeliveryCostRequest request = DeliveryCostRequest.builder().height(20.0).width(10.0).length(10.0)
				.voucherCode(voucherCode).build();

		when(deliveryCostController.calculateDeliveryCost(request))
				.thenThrow(new VoucherApiFailureException("Voucher API Error: 400 Bad Request"));

		ResultActions resultActions = mockMvc.perform(post("/api/v1/parcels"));

		resultActions.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.message").value("Voucher API Error: 400 Bad Request"));
	}
	
	@Test
	void handleResourceAccessException() throws Exception {

		String voucherCode = "MYNT";

		DeliveryCostRequest request = DeliveryCostRequest.builder().height(20.0).width(10.0).length(10.0)
				.voucherCode(voucherCode).build();

		when(deliveryCostController.calculateDeliveryCost(request))
				.thenThrow(new ResourceAccessException("Voucher API Error: Service unavailable"));

		ResultActions resultActions = mockMvc.perform(post("/api/v1/parcels"));

		resultActions.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.message").value("Voucher API Error: Service unavailable"));
	}
}

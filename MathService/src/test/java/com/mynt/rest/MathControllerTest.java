package com.mynt.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mynt.controller.MathController;
import com.mynt.dto.RequestDto;
import com.mynt.dto.ResponseDto;
import com.mynt.service.DivisionService;

public class MathControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private MathController mathController;
	
	@Mock
	private DivisionService divisionService;
	
	private RequestDto request;
	
	private ResponseDto response;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mathController).build();

		RequestDto request = new RequestDto();
		request.setNumerator(40);
		request.setDenominator(20);
		
		response = new ResponseDto();
        response.setQuotient(5);

	}
	
	@Test
	public void divide_success() throws Exception {
		
		when(divisionService.divide(request)).thenReturn(true);
		
		mockMvc.perform(post("api/v1/division")
			.contentType(MediaType.APPLICATION_JSON)
//			.content(asJsonString(request)))
			.content("{\"numerator\" : 20, \"denominator\" : 10 }"))
			.andExpect(status().isCreated())
//			.andExpect(content().string("Operation Completed successfully"))
			.andDo(print());
	} 
	
	
	

}

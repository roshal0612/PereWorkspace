package com.mynt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mynt.dto.RequestDto;
import com.mynt.dto.ResponseDto;
import com.mynt.entity.DivisionOperation;
import com.mynt.exception.RecordNotFoundException;
import com.mynt.repo.DivisionOpRepository;
import com.mynt.serviceImpl.DivisionServiceImpl;

public class DivisionServiceImplTest {

	@InjectMocks
	private DivisionServiceImpl divisionServiceImpl;

	@Mock
	private DivisionOpRepository divisionRepo;

	private RequestDto request;

	private DivisionOperation divisionOperation;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(divisionServiceImpl);

		RequestDto request = new RequestDto();
		request.setNumerator(40);
		request.setDenominator(20);

		divisionOperation = DivisionOperation.builder().id(1).numerator(40).denominator(20).quotient(2).remainder(0)
				.build();
	}

	@Test
	public void divide_succes() {

		when(divisionRepo.save(any(DivisionOperation.class))).thenReturn(divisionOperation);
		boolean result = divisionServiceImpl.divide(request);
		
		assertTrue(result);
		verify(divisionRepo, times(1)).save(any(DivisionOperation.class));
	}

	@Test
	public void getQuotient_succes() {

		when(divisionRepo.findById(1)).thenReturn(Optional.of(divisionOperation));

		ResponseDto response = divisionServiceImpl.getQuotient(1);

		assertNotNull(response);
		assertEquals(5, response.getQuotient());
		verify(divisionRepo, times(1)).findById(1);
	}

	@Test
	public void getQuotient_failed() {

		when(divisionRepo.findById(1)).thenReturn(Optional.empty());
		
		RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> divisionServiceImpl.getQuotient(5));
		
		assertEquals("No record found for given id", thrown.getMessage());
		verify(divisionRepo, times(1)).findById(5);
	}

}

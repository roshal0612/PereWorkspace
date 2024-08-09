package com.mynt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mynt.Dto.VoucherResponse;
import com.mynt.exception.VoucherApiFailureException;

public class VoucherServiceTest {
	
	@InjectMocks
    private VoucherService voucherService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherService.voucherApiUrl = "https://mynt-exam.mocklab.io/voucher/{voucherCode}?key={apiKey}";
        voucherService.apiKey = "apikey";
    }

    @Test
    void getVoucherDiscount_success() {
        String voucherCode = "MYNT";
        String url = "https://mynt-exam.mocklab.io/voucher/MYNT?key=apikey";

        VoucherResponse expectedResponse = new VoucherResponse();
        expectedResponse.setCode(voucherCode);
        expectedResponse.setDiscount(10.0);
        expectedResponse.setExpiry("2024-07-31");

        when(restTemplate.getForObject(url, VoucherResponse.class)).thenReturn(expectedResponse);

        VoucherResponse actualResponse = voucherService.getVoucherDiscount(voucherCode);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getDiscount(), actualResponse.getDiscount());
        assertEquals(expectedResponse.getExpiry(), actualResponse.getExpiry());
    }
    
    @Test
    void getVoucherDiscount_clientError() {
        String voucherCode = "INVALID_CODE";

        HttpClientErrorException clientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");

        when(restTemplate.getForObject(anyString(), eq(VoucherResponse.class)))
            .thenThrow(clientErrorException);

        VoucherApiFailureException thrown = assertThrows(VoucherApiFailureException.class, () -> {
            voucherService.getVoucherDiscount(voucherCode);
        });

        assertTrue(thrown.getMessage().contains("Voucher API Error: Bad Request"));
    }

    
    @Test
    void getVoucherDiscount_serverError() {
        String voucherCode = "SERVER_ERROR";

        HttpServerErrorException serverErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

        when(restTemplate.getForObject(anyString(), eq(VoucherResponse.class)))
            .thenThrow(serverErrorException);

        VoucherApiFailureException thrown = assertThrows(VoucherApiFailureException.class, () -> {
            voucherService.getVoucherDiscount(voucherCode);
        });

        assertTrue(thrown.getMessage().contains("Voucher API Error: 500 Internal Server Error"));
    }
    
    @Test
    void getVoucherDiscount_serviceUnavailable() {
        String voucherCode = "SERVICE_DOWN";

        when(restTemplate.getForObject(anyString(), eq(VoucherResponse.class)))
            .thenThrow(new ResourceAccessException("Service unavailable"));

        VoucherApiFailureException thrown = assertThrows(VoucherApiFailureException.class, () -> {
            voucherService.getVoucherDiscount(voucherCode);
        });

        assertTrue(thrown.getMessage().contains("Voucher API Error: Service unavailable"));
    }

   
	
	
}

package com.mynt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mynt.Dto.VoucherResponse;
import com.mynt.exception.VoucherApiFailureException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoucherService {

	public final RestTemplate restTemplate;

	@Value("${voucher.api.url}")
	public String voucherApiUrl;

	@Value("${voucher.api.key}")
	public String apiKey;

	public VoucherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public VoucherResponse getVoucherDiscount(String voucherCode) {

		String url = voucherApiUrl.replace("{voucherCode}", voucherCode).replace("{apiKey}", apiKey);

		log.info("Fetching voucher details for code: {}", voucherCode);

		try {
			VoucherResponse response = restTemplate.getForObject(url, VoucherResponse.class);
			log.info("Voucher details fetched successfully for code: {}", voucherCode);
			return response;
		} catch (HttpServerErrorException e) {
			log.error("Voucher API returned server error: {}", e.getMessage());
			throw new VoucherApiFailureException("Voucher API Error: " + e.getMessage());
		} catch (ResourceAccessException e) {
			log.error("Resource access error while fetching voucher details: {}", e.getMessage());
			throw new VoucherApiFailureException("Voucher API Error: Service unavailable");
		} catch (Exception e) {
			log.error("Unexpected error: {}", e.getMessage());
			throw new VoucherApiFailureException("Voucher API Error: Unexpected error");
		}
	}

}

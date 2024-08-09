package com.mynt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	   RestTemplate restTemplateBean() {
	        return new RestTemplate();
	    }
}

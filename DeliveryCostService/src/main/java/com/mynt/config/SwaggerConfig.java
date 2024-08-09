package com.mynt.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
	@Bean
	GroupedOpenApi controllerApi() {
		return GroupedOpenApi.builder()
				.group("controller-api")
				.packagesToScan("com.mynt.rest")
				.build();
	}

	@Bean
	OpenAPI springShopOpenAPI() {
	    return new OpenAPI()
	            .info(new Info().title("Delivery Cost Service")
	                    .description("Delivery Cost Service Api Documentation")
	                    .version("v0.0.1")
	                    .license(new License()
	                    		.name("Apache 2.0")
	                    		.url("http://springdoc.org")))
	            .externalDocs(new ExternalDocumentation()
	                    .description("SpringShop Wiki Documentation")
	                    .url("https://springshop.wiki.github.org/docs"));
	}
}

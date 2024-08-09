package com.mynt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(
//								title = "Delivery Cost API",
//								version = "1.0", 
//								description = "Delivery Cost Calculator Service API Documentation"
//								, 
//								contact = @Contact(
//										name = "Roshal Fernandes",
//										email = "rosh@perennial.com",
//										url = "https://www.perennial.com"
//								),
//								license = @License(
//										name = "Apache 2.0",
//										url = "https://www.perennial.com"
//								)
//								),
//						externalDocs = @ExternalDocumentation(
//										description =  "Delivery Cost Service API Documentation",
//										url = "https://localhost:8080/swagger-ui.html"))
public class DeliveryCostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryCostServiceApplication.class, args);
	}

}

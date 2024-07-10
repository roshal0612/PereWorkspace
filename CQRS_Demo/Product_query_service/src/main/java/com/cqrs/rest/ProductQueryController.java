package com.cqrs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cqrs.dto.ProductDto;
import com.cqrs.entity.Product;
import com.cqrs.service.ProductQueryService;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

	@Autowired
	private ProductQueryService queryService;

	@GetMapping()
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = queryService.getProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
		ProductDto product = queryService.getProduct(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
}

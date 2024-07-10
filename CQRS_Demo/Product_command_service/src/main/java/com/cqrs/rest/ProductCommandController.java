package com.cqrs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cqrs.dto.ProductDto;
import com.cqrs.entity.Product;
import com.cqrs.service.ProductCommandService;

@RestController
@RequestMapping("/products")	
public class ProductCommandController {

	@Autowired
	private ProductCommandService commandService;

	@PostMapping("")
	public Product createProduct (@RequestBody ProductDto productDto) {
		return commandService.createProduct(productDto);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct (@PathVariable Long id, @RequestBody ProductDto productDto) {
		return commandService.updateProduct(id, productDto);
	}
	
	@GetMapping()
	public List<Product> getProductsList (){
		return commandService.getProductsList();
	}
}

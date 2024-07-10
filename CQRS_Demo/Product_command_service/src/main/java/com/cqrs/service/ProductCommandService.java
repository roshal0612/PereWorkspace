package com.cqrs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cqrs.dto.ProductDto;
import com.cqrs.dto.ProductEvent;
import com.cqrs.entity.Product;
import com.cqrs.repository.ProductRepository;

@Service
public class ProductCommandService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private KafkaTemplate<String, Object> KafkaTemplate;
	
	public Product createProduct (ProductDto productDto) {
		Product product =  new Product();
		BeanUtils.copyProperties(productDto, product);
		Product save = productRepo.save(product);
		ProductEvent event = new ProductEvent("CreateProduct", save);
		KafkaTemplate.send("product-event-topic", event);
		return save;
	} 

	public Product updateProduct (Long id, ProductDto productDto) {
		Product product = productRepo.findById(id)
				.orElseThrow(RuntimeException::new);
		BeanUtils.copyProperties(productDto, product);
		Product update = productRepo.save(product);
		ProductEvent event = new ProductEvent("UpdateProduct", update);
		KafkaTemplate.send("product-event-topic", event);
		return update;
	} 
	
	public List<Product> getProductsList(){
		return productRepo.findAll();
	} 
}

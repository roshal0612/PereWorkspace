package com.cqrs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cqrs.dto.ProductDto;
import com.cqrs.dto.ProductEvent;
import com.cqrs.entity.Product;
import com.cqrs.repository.ProductRepository;



@Service
public class ProductQueryService {

	@Autowired
	private ProductRepository productRepo;

	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	public ProductDto getProduct(Long id) {
		Product product = productRepo.findById(id).orElseThrow(RuntimeException::new);

		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	@KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
	public void processProductEvents(ProductEvent productEvent) {
		
		Product product = productEvent.getProduct();
		
		if(productEvent.getEventType().equals("CreateProduct"))
			productRepo.save(product);
		
		if (productEvent.getEventType().equals("UpdateProduct")){
			
			Product existingProduct = productRepo.findById(product.getId())
					.orElseThrow(RuntimeException::new);
			
			BeanUtils.copyProperties(product, existingProduct);
			productRepo.save(existingProduct);
		}
	}
}

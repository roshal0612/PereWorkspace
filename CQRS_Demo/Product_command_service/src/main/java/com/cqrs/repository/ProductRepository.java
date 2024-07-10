package com.cqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cqrs.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

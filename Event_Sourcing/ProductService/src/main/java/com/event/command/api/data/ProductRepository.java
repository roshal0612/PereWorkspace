package com.event.command.api.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Serializable>{

}

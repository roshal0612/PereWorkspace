package com.dailyCodeBuffer.command.api.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Serializable>{

}

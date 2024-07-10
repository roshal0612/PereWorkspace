package com.dailyCodeBuffer.shipmentService.command.api.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Serializable>{

}

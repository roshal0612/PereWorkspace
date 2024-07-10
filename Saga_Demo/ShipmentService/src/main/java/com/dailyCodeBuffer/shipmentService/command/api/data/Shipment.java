package com.dailyCodeBuffer.shipmentService.command.api.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

	@Id
	private String shipmentId;
	private String orderId;
	private String shipmentStatus;
}

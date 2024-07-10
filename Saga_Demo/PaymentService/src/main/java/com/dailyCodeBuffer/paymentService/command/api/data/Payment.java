package com.dailyCodeBuffer.paymentService.command.api.data;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	@Id
	private String paymentId;
	
	private String orderId;
	
	private LocalDate timeStamp;
	
	private String paymentStatus;
}

package com.mynt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.Dto.DeliveryCostRequest;
import com.mynt.Dto.DeliveryCostResponse;
import com.mynt.exception.ExceptionInfo;
import com.mynt.service.DeliveryCostServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeliveryCostController {

	@Autowired
	private DeliveryCostServiceImpl service;
	
	public DeliveryCostController(DeliveryCostServiceImpl service) {
		this.service = service;
	}
	
	@Operation(
            summary = "Calculate Delivery Cost REST API",
            description = "REST API to calculate parcel delivery cost"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionInfo.class)
                    )
            )
    }
    )
	
	/**
     * Endpoint to calculate the delivery cost of a parcel.
     *
     * @param request - the parcel request
     * @return ResponseEntity - with the calculated delivery cost
     */
	
	@PostMapping("/parcels")
	public ResponseEntity<DeliveryCostResponse> calculateDeliveryCost(@Valid @RequestBody DeliveryCostRequest request) {
		
		log.info("Received request to calculate parcel delivery cost"); 
		
		DeliveryCostResponse response = new DeliveryCostResponse();
		response.setDeliveryCost(service.calculateDeliveryCost(request));
		
		log.info("Delivery Cost Calculated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}

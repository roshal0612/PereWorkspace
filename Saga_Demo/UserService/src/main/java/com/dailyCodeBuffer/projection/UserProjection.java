package com.dailyCodeBuffer.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.dailyCodeBuffer.model.CardDetails;
import com.dailyCodeBuffer.model.User;
import com.dailyCodeBuffer.query.GetUserPaymentDetailsQuery;

@Component
public class UserProjection {

	@QueryHandler
	public User getUserPaymentDetails (GetUserPaymentDetailsQuery query) {
		
		// Get user data from user DB 
		
		CardDetails cardDetails = CardDetails.builder()
				.name("Roshal Fernandes")
				.validUntilMonth(12)
				.validUntilYear(2027)
				.cardNumber("12345678")
				.cvv(123)
				.build();
	
		return User.builder()
				.userId(query.getUserId())
				.firstName("Roshal")
				.lastName("Fernandes")
				.cardDetails(cardDetails)
				.build();
	}
}

package com.dailyCodeBuffer.userService.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyCodeBuffer.model.User;
import com.dailyCodeBuffer.query.GetUserPaymentDetailsQuery;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("{userId}")
	public User getUserPaymentDetails(@PathVariable String userId) {
		
		GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(userId);
		User user = queryGateway.query(getUserPaymentDetailsQuery, 
				ResponseTypes.instanceOf(User.class)).join();

	return user;
	}
}

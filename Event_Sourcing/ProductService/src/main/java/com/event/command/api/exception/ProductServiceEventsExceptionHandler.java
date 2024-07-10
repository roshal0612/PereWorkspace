package com.event.command.api.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductServiceEventsExceptionHandler implements ListenerInvocationErrorHandler{

	@Override
	public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {

		throw exception;
	}

}

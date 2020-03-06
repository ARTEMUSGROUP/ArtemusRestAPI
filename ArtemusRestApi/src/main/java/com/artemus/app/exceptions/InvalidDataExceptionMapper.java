package com.artemus.app.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.artemus.app.model.response.ErrorMessage;
import com.artemus.app.model.response.ErrorMessages;

@Provider
public class InvalidDataExceptionMapper implements ExceptionMapper<RuntimeException> {

	@Override
	public Response toResponse(RuntimeException exception) {
		exception.printStackTrace();
		ErrorMessage errorMessage = null;
		if (exception.getMessage().contains("Exception Description: Cannot add the object")) {
			errorMessage = new ErrorMessage(ErrorMessages.INVALID_JSON.getErrorMessage(), ErrorMessages.INVALID_JSON.name(), "http://");
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}

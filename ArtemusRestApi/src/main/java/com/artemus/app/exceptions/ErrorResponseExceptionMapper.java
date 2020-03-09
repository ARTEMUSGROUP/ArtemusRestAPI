package com.artemus.app.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.artemus.app.model.response.ErrorMessage;
import com.artemus.app.model.response.ErrorMessages;

@Provider
public class ErrorResponseExceptionMapper implements ExceptionMapper<ErrorResponseException> {
	public Response toResponse(ErrorResponseException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.INTERNAL_SERVER_ERROR.name(), "http://");
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}
}

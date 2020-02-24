package com.artemus.app.exceptions;

import javax.ws.rs.core.Response;

import com.artemus.app.model.response.ErrorMessage;

public class ErrorResponse {
	
	public static Response toResponse(String message,String title) {
        ErrorMessage errorMessage = new ErrorMessage(message,title, "http://");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}

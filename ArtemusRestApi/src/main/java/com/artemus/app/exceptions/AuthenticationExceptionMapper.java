/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemus.app.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.artemus.app.model.response.ErrorMessage;
import com.artemus.app.model.response.ErrorMessages;



@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    public Response toResponse(AuthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.AUTHENTICATION_FAILED.name(),
                "http://"
        );
        
        return Response.status(Response.Status.UNAUTHORIZED).
                entity(errorMessage).
                build();
    }
    
}
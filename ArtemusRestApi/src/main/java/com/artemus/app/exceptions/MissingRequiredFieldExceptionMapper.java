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
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException>{

    public Response toResponse(MissingRequiredFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.MISSING_REQUIRED_FIELD.name(), "http://appsdeveloperblog.com");
        
      return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
    
}

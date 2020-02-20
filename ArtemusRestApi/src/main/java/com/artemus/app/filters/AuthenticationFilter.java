package com.artemus.app.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import com.artemus.app.annotations.Secured;
import com.artemus.app.dao.AuthenticationDAO;
import com.artemus.app.exceptions.AuthenticationException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
    private HttpServletRequest request;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Extract Authorization header details
        String authorizationHeader
                = requestContext.getHeaderString("Authorization");
        if (authorizationHeader == null) {
                    throw new AuthenticationException("Authorization header must be provided");
        }       
		
        validateToken(authorizationHeader);
	}

	private void validateToken(String token) throws AuthenticationException {
		String scacCode = token.substring(0,4)  ;
		String extractedToken=token.substring(4).trim();
		
		System.out.println(scacCode);
		System.out.println(extractedToken);
		
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		try {
		boolean isUserValid =  authenticationDAO.checkUserAuthentication(scacCode,extractedToken);
		if (!isUserValid) {
			throw new AuthenticationException("Authorization token did not match");
		}else
		{
			HttpSession session = request.getSession(true);
			
			session.setAttribute("loginScacCode", scacCode);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			authenticationDAO.closeAll();
		}
		
	}
}

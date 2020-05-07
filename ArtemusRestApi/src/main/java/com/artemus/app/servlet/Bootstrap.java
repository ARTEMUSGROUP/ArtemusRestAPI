package com.artemus.app.servlet;

import javax.servlet.http.HttpServlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import io.swagger.v3.core.jackson.SwaggerModule;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
    info = @Info(
        title = "Artemus API Documentation", 
        version = "1.0.0", 
        description = "Defines a Bill Header.",
        termsOfService = "",
        contact = @Contact(email = ""),
        license = @License(
            name = "Akshay",
            url = "http://localhost:8080/ArtemusApi/v1"
        )
    )
)
public class Bootstrap extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		/*
		 * OpenAPI oas = new OpenAPI(); SwaggerConfiguration oasConfig = new
		 * SwaggerConfiguration() .openAPI(oas)
		 * .resourcePackages(Stream.of("com.artemus.app").collect(Collectors.toSet()));
		 * 
		 * try { new JaxrsOpenApiContextBuilder() .servletConfig(config)
		 * .openApiConfiguration(oasConfig) .buildContext(true); } catch
		 * (OpenApiConfigurationException e) { throw new
		 * ServletException(e.getMessage(), e); }
		 */

		  
		    
	}
	
}

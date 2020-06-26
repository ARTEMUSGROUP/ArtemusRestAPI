package com.artemus.app.servlet;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import io.swagger.v3.core.jackson.SwaggerModule;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
    info = @Info(
        title = "Artemus API Documentation", 
        version = "1.0.0", 
        description = "Artemus API version 1.0",
        termsOfService = "",
        contact = @Contact(url = "http://giantleapsystems.com/"),
        license = @License(
            name = "Artemus API v1",
            url = "https://localhost:8080/ArtemusApi/v1"
        )
    ),tags = {
            @Tag(name = "Vessel Service", description = "To Create and Update Vessel"),
            @Tag(name = "Voyage Service",description="To create Voyage"),
            @Tag(name = "Bill Service", description = "To Create and Update Bill"),
            @Tag(name="Japan Voyage Service",description="To create Voyage"),
        	@Tag(name="Japan Bill Service",description="To Create and Update Bill"),
        	@Tag(name = "Original Manifest Service",description="To Send Bill To Customs")
    },servers = {
            @Server(
                    description = "Test Server",
                    url = "http://52.54.244.138:8080/ArtemusApi/"
                   )
    }
)
public class Bootstrap extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Tag(name = "Vessel Service", description = "To Create and Update Vessel")
    @Tag(name = "Voyage Service",description="To create Voyage")
    @Tag(name = "Bill Service", description = "To Create and Update Bill")
	@Tag(name="Japan Voyage Service",description="To create Voyage")
	@Tag(name="Japan Bill Service",description="To Create and Update Bill")
	@Tag(name = "Original Manifest Service",description="To Send Bill To Customs")
    public Response getTags() {
        return Response.ok().entity("ok").build();
    }
	
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

package com.artemus.app.servlet;

import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
    info = @Info(
        title = "Artemus API Documentation", 
        version = "1.0.0", 
        description = "Artemus API version 1.0",
        termsOfService = "",
        contact = @Contact(),
        license = @License(
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
                    description = "Artemus API Production Server",
                    url = "https://api.artemusgroupusa.com/ArtemusApi/"
                   )
    }
)
public class Bootstrap {

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
	
}

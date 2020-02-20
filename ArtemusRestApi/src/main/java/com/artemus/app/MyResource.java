package com.artemus.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.dao.DemoDAO;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getIt() {
//    	DemoDAO objDao = new DemoDAO();
		return Response.status(200).entity("Got it! ").build();
    }
}

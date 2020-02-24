package com.artemus.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.dao.DemoDAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	static Logger logger = LogManager.getLogger();
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getIt() {
    	logger.info("myresource");
    	DemoDAO objDao = new DemoDAO();
    	String connString = objDao.getConnection();
    	objDao.closeAll();
		return Response.status(200).entity("Got it! "+connString).build();
    }
}

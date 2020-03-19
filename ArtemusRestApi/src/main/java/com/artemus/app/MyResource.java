package com.artemus.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.dao.DemoDAO;
import com.artemus.app.model.response.ResponseMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	static Logger logger = LogManager.getLogger();
	
	@Secured
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ResponseMessage getIt() {
    	DemoDAO objDao = new DemoDAO();
    	String connString = objDao.getConnection();
    	objDao.closeAll();
    	ResponseMessage objResponse = new ResponseMessage();
    	objResponse.setCode(200);
    	objResponse.setStatus(Response.Status.OK);
    	objResponse.setMessage("Got it! "+connString);
		return objResponse;
    }
}

package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.Voyage;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.JPVoyageScheduleService;
import com.artemus.app.service.impl.JPVoyageScheduleServiceImpl;




@Path("/jp/voyages")
@Consumes("application/json; charset=UTF-8")
@Produces({ MediaType.APPLICATION_JSON })
@io.swagger.v3.oas.annotations.tags.Tag(name="Japan Voyage Service",description="To create Voyage")
public class JPVoyagesEntryPoint {

	
	@POST @Secured
	public ResponseMessage createVoyage(Voyage objRequest, @HeaderParam("Authorization") String authorization) {
		String scacCode = authorization.substring(0, 4);
		objRequest.setScacCode(scacCode);
		JPVoyageScheduleService objService = new JPVoyageScheduleServiceImpl();
		objService.createVoyage(objRequest);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Voyage Created...");
		return objResponse;
	}
}

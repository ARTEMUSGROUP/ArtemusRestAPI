package com.artemus.app;

import java.util.ArrayList;

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
import com.artemus.app.service.VoyageScheduleService;
import com.artemus.app.service.impl.VoyageScheduleServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;


@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
@Path("voyages")
@Api("Voyage Service")
@SwaggerDefinition(tags= {@Tag(name="Voyage Service",description="To create Voyage")})
public class VoyagesEntryPoint {

	@Secured
	@POST
	public ResponseMessage createVoyage(Voyage objRequest, @HeaderParam("Authorization") String authorization) {
		String scacCode = authorization.substring(0, 4);
		objRequest.setScacCode(scacCode);
		VoyageScheduleService objService = new VoyageScheduleServiceImpl();
		objService.createVoyage(objRequest);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Voyage Created...");
		return objResponse;
	}
}

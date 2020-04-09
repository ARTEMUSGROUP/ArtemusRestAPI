package com.artemus.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.Vessel;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.VesselScheduleService;
import com.artemus.app.service.impl.VesselScheduleServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;



@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
@Path("/vessel")
@Api("Vessel Service")
@SwaggerDefinition(tags= {@Tag(name="Vessel Service",description="To Create and Update Vessel")})
public class VesselsEntryPoint {

	@Secured
	@POST
	@ApiOperation(value = "API for Vessel Creation")
	public ResponseMessage createVessel(Vessel objRequest, @HeaderParam("Authorization") String authorization) {
		String loginScac = authorization.substring(0, 4);
		objRequest.setLoginScac(loginScac);
		VesselScheduleService objService = new VesselScheduleServiceImpl();
		objService.createVessel(objRequest);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Vessel Created...");
		return objResponse;
	}
	
	@Secured
	@PUT
	@ApiOperation(value = "API for Vessel Update")
	public ResponseMessage updateVessel(Vessel objRequest, @HeaderParam("Authorization") String authorization) {
		String loginScac = authorization.substring(0, 4);
		objRequest.setLoginScac(loginScac);
		VesselScheduleService objService = new VesselScheduleServiceImpl();
		objService.updateVessel(objRequest);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Vessel Updated...");
		return objResponse;
	}
	
}

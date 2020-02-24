package com.artemus.app;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.PortDetails;
import com.artemus.app.model.request.Voyage;
import com.artemus.app.utils.ValidateBeanUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
@Path("voyages")
public class VoyagesEntryPoint {

	@Secured
	@POST
	public Response createVoyage() {
		Voyage objRequest = new Voyage();
		objRequest.setPortDetails(new ArrayList<PortDetails>());
		objRequest.getPortDetails().add(new PortDetails());
		objRequest.getPortDetails().get(0).setSailingDate("22/02/2020");

		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer str = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objRequest);
		System.out.println(str);
		return Response.status(200).entity("SUCCESS").build();
	}
}

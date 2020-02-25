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
import com.artemus.app.service.VoyageScheduleService;
import com.artemus.app.service.impl.VoyageScheduleServiceImpl;
import com.artemus.app.utils.ValidateBeanUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
@Path("voyages")
public class VoyagesEntryPoint {

	@Secured
	@POST
	public Response createVoyage(Voyage objRequest) {
		VoyageScheduleService objService = new VoyageScheduleServiceImpl();
		objService.createVoyage(objRequest);
		return Response.status(200).entity("SUCCESS").build();
	}
}

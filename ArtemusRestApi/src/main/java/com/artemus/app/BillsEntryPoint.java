package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.service.BillsService;
import com.artemus.app.service.impl.BillsServiceImpl;

@Path("/bills")
public class BillsEntryPoint {

	@Secured
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBill(BillHeader requestObj) {
		System.out.println(requestObj.toString());
		BillsService billsService = new BillsServiceImpl();
		billsService.createBill(requestObj);
		return Response.status(200).entity("SUCCESS").build();
	}
	
}

package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.BillStatus;
import com.artemus.app.model.response.BillStatusResponse;
import com.artemus.app.service.impl.BillStatusServiceImpl;

@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
@Path("/getBillStatus")
@io.swagger.v3.oas.annotations.tags.Tag(name="Bill Status Service",description="To get BILL status updates")
public class BillStatusEntryPoint {
	
	@Secured
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillStatusResponse getBillStatus(BillStatus objBillStatus, @HeaderParam("Authorization") String authorization) {
		String scacCode = authorization.substring(0, 4);
		BillStatusResponse objResponse = new BillStatusResponse();
		
		BillStatusServiceImpl objService = new BillStatusServiceImpl();
		objResponse = objService.getBillStatus(objBillStatus,scacCode);
		
		return objResponse;
	}
}

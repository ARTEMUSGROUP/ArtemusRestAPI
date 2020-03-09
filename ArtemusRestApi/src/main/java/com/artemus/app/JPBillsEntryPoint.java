package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.BillsService;
import com.artemus.app.service.JPBillsService;
import com.artemus.app.service.impl.BillsServiceImpl;
import com.artemus.app.service.impl.JPBillsServiceImpl;

@Path("/jp/bills")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
public class JPBillsEntryPoint {

	@Secured
	@POST
	public ResponseMessage createBill(BillHeader requestObj, @HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0, 4);
		requestObj.setLoginScac(scacCode);
		JPBillsService billsService = new JPBillsServiceImpl();
		billsService.createBill(requestObj);

		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Bill Created...");
		return objResponse;
	}
}
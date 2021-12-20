package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.JPBillHeader;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.JPBillsService;
import com.artemus.app.service.impl.JPBillsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;


@Path("/jp/bills")
@Consumes("application/json; charset=Utf-8")
@Produces({ MediaType.APPLICATION_JSON })
@io.swagger.v3.oas.annotations.tags.Tag(name="Japan Bill Service",description="To Create and Update Bill")
public class JPBillsEntryPoint {

	@Secured
	@POST
	@Operation(description = "API for Bill Creation")
	public ResponseMessage createBill(JPBillHeader requestObj, @HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0, 4);
		requestObj.setLoginScac(scacCode);
		if (requestObj.getHblScac() != null && !requestObj.getHblScac().isEmpty()) {

		} else {
			requestObj.setHblScac(scacCode);
		}
		JPBillsService billsService = new JPBillsServiceImpl();
		billsService.createBill(requestObj);

		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Bill Created...");
		return objResponse;
	}

	@Secured
	@PUT
	@Operation(description = "API for Bill Update")
	public ResponseMessage updateBill(JPBillHeader requestObj, @HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0, 4);
		requestObj.setLoginScac(scacCode);
		if (requestObj.getHblScac() != null && !requestObj.getHblScac().isEmpty()) {

		} else {
			requestObj.setHblScac(scacCode);
		}
		JPBillsService billsService = new JPBillsServiceImpl();
		billsService.updateBill(requestObj);

		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Bill Updated...");
		return objResponse;
	}
}

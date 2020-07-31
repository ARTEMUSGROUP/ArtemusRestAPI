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
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.BillsService;
import com.artemus.app.service.impl.BillsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Path("/bills")
@Consumes("application/json; charset=UTF-8")
@Produces({MediaType.APPLICATION_JSON})
@ApiResponse(responseCode = "200", description = "Bill Created Successfully")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Bill Service",description="To Create and Update Bill")
public class BillsEntryPoint {

	@Secured
	@POST
	@Operation(description = "API for Bill Creation",responses= {@ApiResponse(description = "Create a Bill",
            content = @Content(mediaType="application/json",schema=@Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "400", description = "Bill not Created")})
	public ResponseMessage createBill(BillHeader requestObj,@HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0,4)  ;
		requestObj.setLoginScac(scacCode);
		if (requestObj.getHblScac() != null && !requestObj.getHblScac().isEmpty()) {

		} else {
			requestObj.setHblScac(scacCode);
		}
		BillsService billsService = new BillsServiceImpl();
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
	public ResponseMessage updateBill(BillHeader requestObj,@HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0,4)  ;
		requestObj.setLoginScac(scacCode);
		if (requestObj.getHblScac() != null && !requestObj.getHblScac().isEmpty()) {

		} else {
			requestObj.setHblScac(scacCode);
		}
		BillsService billsService = new BillsServiceImpl();
		billsService.updateBill(requestObj);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(Response.Status.OK.getStatusCode());
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Bill Updated...");
		return objResponse;
	}
	
}

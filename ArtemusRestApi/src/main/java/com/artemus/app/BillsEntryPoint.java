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
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.BillsService;
import com.artemus.app.service.impl.BillsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;


@Path("/bills")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Api("Bill Service")
@SwaggerDefinition(tags= {@Tag(name="Bill Service",description="Rest Endpoint for Bill Service")})
public class BillsEntryPoint {

	@Secured
	@POST
	public ResponseMessage createBill(BillHeader requestObj,@HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0,4)  ;
		requestObj.setLoginScac(scacCode);
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
	public ResponseMessage updateBill(BillHeader requestObj,@HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0,4)  ;
		requestObj.setLoginScac(scacCode);
		
		BillsService billsService = new BillsServiceImpl();
		billsService.updateBill(requestObj);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(Response.Status.OK.getStatusCode());
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Bill Updated...");
		return objResponse;
	}
	
}

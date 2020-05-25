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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;


@Path("/bills")
@Consumes("application/json; charset=UTF-8")
@Produces({MediaType.APPLICATION_JSON})
@Api("Bill Service")
@SwaggerDefinition(tags= {@Tag(name="Bill Service",description="To Create and Update Bill")})
public class BillsEntryPoint {

	@Secured
	@POST
	@ApiOperation(value = "API for Bill Creation")
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
	@ApiOperation(value = "API for Bill Update")
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

package com.artemus.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.artemus.app.annotations.Secured;
import com.artemus.app.model.request.AddOriginalManifest;
import com.artemus.app.model.response.ResponseMessage;
import com.artemus.app.service.OriginalManifestService;
import com.artemus.app.service.impl.OriginalManifestServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

@Path("/addOriginalManifest")
@Consumes("application/json; charset=UTF-8")
@Produces({MediaType.APPLICATION_JSON})
@io.swagger.v3.oas.annotations.tags.Tag(name = "Original Manifest Service",description="To Send Bill To Customs")
public class OriginalManifestEntryPoint {

	@Secured
	@POST
	@Operation(description = "API to send Bill to Customs")
	public ResponseMessage sentBillToCustoms(AddOriginalManifest requestObj,@HeaderParam("Authorization") String authorization) {
		System.out.println(requestObj.toString());
		String scacCode = authorization.substring(0,4)  ;
		requestObj.setLoginScac(scacCode);
		OriginalManifestService manifestService = new OriginalManifestServiceImpl();
		manifestService.sentBillToCustoms(requestObj);
		
		ResponseMessage objResponse = new ResponseMessage();
		objResponse.setCode(200);
		objResponse.setStatus(Response.Status.OK);
		objResponse.setMessage("Information Saved Successfully ...");
		return objResponse;
	}
	
	
}

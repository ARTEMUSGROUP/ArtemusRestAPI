package com.artemus.app.model.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.main.art.Artemusrest.Dev.BillHeader;

@Path("voyageschema")
public class VoyageSchema {

	private static ObjectMapper mapper = new ObjectMapper();
    public static final String JSON_$SCHEMA_DRAFT4_VALUE = "http://json-schema.org/draft-04/schema#";
    public static final String JSON_$SCHEMA_ELEMENT = "$schema";
	private static final String json = null;
    
    static {
        // required for pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
	
	    @GET
	    @Path("schema")
	    //(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response  JJSchema() {
	    	
	    	// get the draft 4 schema builder
	        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
	        
	        // use the schema builder to generate JSON schema from Java class
	        JsonNode schemaNode = v4generator.generateSchema(Voyage.class);
	        
	        // add the $schema node to the schema. By default, JJSchema v0.6 does not add it 
	        ((ObjectNode) schemaNode).put(JSON_$SCHEMA_ELEMENT, JSON_$SCHEMA_DRAFT4_VALUE);
	        
	        // print the generated schema 
	        try {
				System.out.println(mapper.writeValueAsString(schemaNode));
				
				String json = mapper.writeValueAsString(schemaNode);
				
				return Response.ok() //200
						.entity(json)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
						.allow("OPTIONS").build();

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok() //200
					.entity(json)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.allow("OPTIONS").build();
	    
	    }
	
}

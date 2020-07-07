package com.artemus.app.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

public class SwaggerConfigServlet extends HttpServlet{

	static Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		BeanConfig swaggerConfig=new BeanConfig();
		
		swaggerConfig.setTitle("Artemus API Documentation");
		swaggerConfig.setVersion("1.0");
		//swaggerConfig.setSchemes(new String[]{"https"});
		swaggerConfig.setSchemes(new String[]{"http"});
        //swaggerConfig.setHost("localhost:8080");
		swaggerConfig.setHost("52.54.244.138:8080");
		swaggerConfig.setBasePath("/ArtemusApi/v1");
	    swaggerConfig.setResourcePackage("com.artemus.app");
	    swaggerConfig.setPrettyPrint(true);
	    swaggerConfig.setScan(true);
		
	 // this makes Swagger honor JAXB annotations
        Json.mapper().registerModule(new JaxbAnnotationModule());
        Yaml.mapper().registerModules(new JaxbAnnotationModule());
        logger.info("Swagger Initialization Done");
	    
	}
	
}

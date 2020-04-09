package com.artemus.app.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Xml;
import io.swagger.util.Json;
import io.swagger.util.Yaml;


public class SwaggerConfigServlet extends HttpServlet {
	static Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		
		  BeanConfig beanconfig = new BeanConfig();
		  beanconfig.setBasePath("/ArtemusApi/v1");
		  beanconfig.setHost("localhost:8080");
		  beanconfig.setTitle("Artemus API Documentation");
		  beanconfig.setResourcePackage("com.artemus.app");
		  beanconfig.setPrettyPrint(true); beanconfig.setScan(true);
		  beanconfig.setSchemes(new String[] { "http" }); 
		  beanconfig.setVersion("1.0.0");
		// Needed to register these modules to get Swagger to use JAXB annotations
		    // (see https://github.com/swagger-api/swagger-core/issues/960 for explanation)
		    Json.mapper().registerModule(new JaxbAnnotationModule());
		    Yaml.mapper().registerModule(new JaxbAnnotationModule());
		    
		    logger.debug("Application started!");
		    System.out.println("Swagger Initialization");
		    
	}

}

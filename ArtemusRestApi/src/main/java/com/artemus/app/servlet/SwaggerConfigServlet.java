package com.artemus.app.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import io.swagger.jaxrs.config.BeanConfig;


public class SwaggerConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		
		  BeanConfig beanconfig = new BeanConfig();
		  beanconfig.setBasePath("/ArtemusApi/v1");
		  beanconfig.setHost("localhost:8080");
		  beanconfig.setTitle("Maven Swagger API Docs");
		  beanconfig.setResourcePackage("com.artemus.app");
		  beanconfig.setPrettyPrint(true); beanconfig.setScan(true);
		  beanconfig.setSchemes(new String[] { "http" }); 
		  beanconfig.setVersion("2.0");
		 
	}

}

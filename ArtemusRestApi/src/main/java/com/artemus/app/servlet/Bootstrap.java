package com.artemus.app.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "Swagger Server", 
        version = "1.0.0", 
        description = "Defines a Bill Header.",
        termsOfService = "",
        contact = @Contact(email = ""),
        license = @License(
            name = "",
            url = "http://localhost:8080/v1/ArtemusApi"
        )
    )
)
public class Bootstrap {
}

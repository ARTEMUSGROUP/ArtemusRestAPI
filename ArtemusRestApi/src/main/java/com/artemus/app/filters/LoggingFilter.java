package com.artemus.app.filters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringWriter;

import javax.sound.sampled.AudioFormat.Encoding;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static Logger logger = LogManager.getLogger();
	@Override
	public void filter(ContainerRequestContext requestContext) {
		StringBuilder sb = new StringBuilder();
		String string;
		try {
			String bodyStr = IOUtils.toString(requestContext.getEntityStream());
			requestContext.setEntityStream(new ByteArrayInputStream((bodyStr).getBytes()));
			sb.append("URI : " + requestContext.getUriInfo().getAbsolutePath()+" - ");
			sb.append("HEADERS : " + " Authorization : " + requestContext.getHeaderString("Authorization"));
			sb.append("\n REQUEST BODY : " + bodyStr);
			logger.debug("HTTP REQUEST :" + sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		if (responseContext.hasEntity()) {
			sb.append("Entity : ").append(responseContext.getEntity());
		}
		logger.debug("HTTP RESPONSE : " + sb.toString());
	}
}

package com.javafee.elibrary.rest.api.controller.monitor;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.security.JWTTokenNeeded;

@Path("/monitor")
@Produces(TEXT_PLAIN)
public interface MonitorApi {
	@GET
	@Path("health")
	@JWTTokenNeeded
	Response health();
}

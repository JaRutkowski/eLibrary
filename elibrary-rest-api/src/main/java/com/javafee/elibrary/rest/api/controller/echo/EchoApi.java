package com.javafee.elibrary.rest.api.controller.echo;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.security.JWTTokenNeeded;

@Path("/echo")
@Produces(TEXT_PLAIN)
public interface EchoApi {
	@GET
	Response echo(@QueryParam("message") String message);

	@GET
	@Path("jwt")
	@JWTTokenNeeded
	Response echoWithJWTToken(@QueryParam("message") String message);
}

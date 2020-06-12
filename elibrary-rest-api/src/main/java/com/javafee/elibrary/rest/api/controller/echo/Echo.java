package com.javafee.elibrary.rest.api.controller.echo;

import javax.ws.rs.core.Response;

public class Echo implements EchoApi {
	public Response echo(String message) {
		return Response.ok().entity(message == null ? "no message" : message).build();
	}

	public Response echoWithJWTToken(String message) {
		return Response.ok().entity(message == null ? "no message" : message).build();
	}
}

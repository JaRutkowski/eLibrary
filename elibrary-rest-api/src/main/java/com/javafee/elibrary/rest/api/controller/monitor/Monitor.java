package com.javafee.elibrary.rest.api.controller.monitor;

import javax.ws.rs.core.Response;

public class Monitor implements MonitorApi {
	@Override
	public Response health() {
		//TODO: 500 - in case of DB connection error
		return Response.ok().build();
	}
}

package com.javafee.elibrary.rest.api.controller.heroku;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.security.JWTTokenNeeded;

@Path("/heroku-management")
@Produces(APPLICATION_JSON)
public interface HerokuManagementApi {
	@GET
	@Path("latest-builds")
	@JWTTokenNeeded
	Response latestBuilds(@QueryParam("apiId") String token);

	@GET
	@Path("build-number")
	@JWTTokenNeeded
	Response buildNumber(@QueryParam("apiId") String token);

	@GET
	@Path("installation-date")
	@JWTTokenNeeded
	Response installationDate(@QueryParam("apiId") String token);

	@GET
	@Path("version")
	@JWTTokenNeeded
	Response version(@QueryParam("apiId") String token);
}

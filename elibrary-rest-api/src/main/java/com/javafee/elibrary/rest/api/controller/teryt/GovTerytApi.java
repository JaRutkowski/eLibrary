package com.javafee.elibrary.rest.api.controller.teryt;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.security.JWTTokenNeeded;

@Local
@Path("/teryt")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface GovTerytApi {
	@GET
	@Path("/get-cities-from-file")
	@JWTTokenNeeded
	Response getCitiesFromFile(@QueryParam("from") Integer from, @QueryParam("to") Integer to);
}

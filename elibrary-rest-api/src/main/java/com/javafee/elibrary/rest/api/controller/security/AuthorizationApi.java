package com.javafee.elibrary.rest.api.controller.security;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/authorization")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface AuthorizationApi {
	@POST
	@Path("/register")
	@Consumes(APPLICATION_FORM_URLENCODED)
	@Produces(TEXT_PLAIN)
	Response authenticate(@FormParam("login") String login, @FormParam("password") String password);
}

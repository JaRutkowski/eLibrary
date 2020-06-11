package com.javafee.elibrary.rest.api.controller.security;

import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.security.AuthorizationService;

@Stateless
public class Authorization implements AuthorizationApi {
	@Inject
	private AuthorizationService authorizationService;

	public Response authenticate(String login, String password) {
		String token;
		try {
			token = authorizationService.authorize(login, password);
		} catch (NoSuchAlgorithmException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return Response.ok(token).build();
	}
}

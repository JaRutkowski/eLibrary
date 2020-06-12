package com.javafee.elibrary.rest.api.service.security;

import java.security.Key;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.javafee.elibrary.rest.api.repository.repository.AuthorizationRepository;
import com.javafee.elibrary.rest.api.utils.HttpHeadersUtils;
import com.javafee.elibrary.rest.api.utils.Utils;

import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;

@Log
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
	@Inject
	private AuthorizationRepository authorizationRepository;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String authorizationLogin = requestContext.getHeaderString(HttpHeadersUtils.LOGIN);

		String token = null;

		try {
			// Extract the token from the HTTP Authorization header
			token = authorizationHeader.substring("Bearer".length()).trim();
			// Extract private key for user
			String secretKey = authorizationRepository.findPrivateKeyByLogin(authorizationLogin);
			// Mapping a secretKey from the String to java.security.Key
			Key key = Utils.decodeKey(secretKey, "DES");
			// Validate the token
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		} catch (Exception e) {
			log.severe("Invalid token: " + token);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}

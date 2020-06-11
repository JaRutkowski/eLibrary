package com.javafee.elibrary.rest.api.service.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.AuthenticationException;

import com.javafee.elibrary.rest.api.repository.dto.api.Authorization;
import com.javafee.elibrary.rest.api.repository.repository.AuthorizationRepository;
import com.javafee.elibrary.rest.api.utils.Constants;
import com.javafee.elibrary.rest.api.utils.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ApplicationScoped
public class AuthorizationService {
	@Inject
	private AuthorizationRepository authorizationRepository;

	private KeyGenerator keyGenerator;

	public String authorize(String login, String password) throws NoSuchAlgorithmException, AuthenticationException {
		// Authenticate the user using the credentials provided
		authenticate(login, password);

		// Issue a token for the user
		String token = issueToken(login);

		// Return the token on the response
		return Constants.BEARER_PREFIX + token;
	}

	public void authenticate(String login, String password) throws AuthenticationException {
		if (!authorizationRepository.authenticate(login, password))
			throw new AuthenticationException();
	}

	private String issueToken(String login) throws NoSuchAlgorithmException {
		this.keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = this.keyGenerator.generateKey();

		persistAuthorizationData(login, secretKey);

		String jwtToken = Jwts.builder()
				.setSubject(login)
				.setIssuedAt(new Date())
				.setExpiration(Utils.convertToDateViaInstant(LocalDateTime.now().plusYears(10L).toLocalDate()))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
		return jwtToken;
	}

	private void persistAuthorizationData(String login, Key key) {
		Authorization authorization = new Authorization();
		authorization.setLogin(login);
		authorization.setPrivateKey(Utils.encodeKey(key));
		this.authorizationRepository.create(authorization);
	}
}

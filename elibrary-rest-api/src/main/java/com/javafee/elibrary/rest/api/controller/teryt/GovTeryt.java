package com.javafee.elibrary.rest.api.controller.teryt;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.teryt.GovTerytService;

@Stateless
public class GovTeryt implements GovTerytApi {
	@Inject
	private GovTerytService govTerytService;

	@Override
	public Response getCitiesFromFile() {
		try {
			govTerytService.getCitiesFromFile();
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}
}

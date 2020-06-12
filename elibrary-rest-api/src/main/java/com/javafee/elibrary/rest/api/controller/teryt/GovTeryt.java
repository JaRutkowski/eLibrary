package com.javafee.elibrary.rest.api.controller.teryt;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.repository.dto.pojo.City;
import com.javafee.elibrary.rest.api.service.teryt.GovTerytService;

@Stateless
public class GovTeryt implements GovTerytApi {
	@Inject
	private GovTerytService govTerytService;

	@Override
	public Response getCitiesFromFile(Integer from, Integer to) {
		List<City> cities;
		try {
			cities = govTerytService.getCitiesFromFile(from, to);
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().entity(cities).build();
	}
}

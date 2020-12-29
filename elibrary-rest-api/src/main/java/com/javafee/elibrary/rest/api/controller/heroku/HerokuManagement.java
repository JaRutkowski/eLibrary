package com.javafee.elibrary.rest.api.controller.heroku;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.javafee.elibrary.rest.api.service.heroku.HerokuManagementService;

@Stateless
public class HerokuManagement implements HerokuManagementApi {
	@Inject
	private HerokuManagementService herokuManagementService;

	@Override
	public Response latestBuilds(String token) {
		return Response.ok(herokuManagementService.getLatestBuild(token)).build();
	}

	@Override
	public Response buildNumber(String token) {
		return Response.ok(herokuManagementService.getBuildNumber(token)).build();
	}

	@Override
	public Response installationDate(String token) {
		return Response.ok(herokuManagementService.getInstallationDate(token)).build();
	}

	@Override
	public Response version(String token) {
		return Response.ok(herokuManagementService.getVersion(token)).build();
	}
}

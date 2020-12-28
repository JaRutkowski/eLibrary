package com.javafee.elibrary.rest.api.service.heroku;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.javafee.elibrary.rest.api.repository.dto.api.Build;


@ApplicationScoped
public class HerokuManagementService {
	public Build[] getLatestBuild(String token) {
		Client client = ClientBuilder.newBuilder().build();
		return client.target("https://api.heroku.com/pipelines/ce064da7-84b3-49ac-8715-a934cd468de2/latest-builds")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.header("Accept", "application/vnd.heroku+json; version=3")
				.get(Build[].class);
	}

	//TODO Implement logs fetching method
}

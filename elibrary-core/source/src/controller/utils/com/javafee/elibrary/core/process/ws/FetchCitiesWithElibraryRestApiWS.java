package com.javafee.elibrary.core.process.ws;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.extern.java.Log;

@Log
public class FetchCitiesWithElibraryRestApiWS implements Process {
	@Override
	public void execute() {
		initializeCitiesWithElibraryRestApi();
	}

	private void initializeCitiesWithElibraryRestApi() {
		Common.setCities(invokeElibraryRestApi());
	}

	private List<City> invokeElibraryRestApi() {
		List<City> responseCities = new ArrayList();
		JsonNode response = null;
		HttpResponse<JsonNode> uniResponse;

		Long fromQueryParam = SystemProperties.getInstance().getCitiesFromIndex();
		Long toQueryParam = fromQueryParam + (SystemProperties.getInstance().getCitiesPackageSize() - 1);
		try {
			uniResponse = Unirest.get("http://localhost:8080/elibrary-rest-api-1.0-SNAPSHOT/teryt/get-cities-from-file?from=" + fromQueryParam + "&to=" + toQueryParam)
					.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUZXN0UEpSIiwiaWF0IjoxNTkxOTY2MTc4LCJleHAiOjE5MDc0NDU2MDB9.Auuj3yTfITni-50m0X8jRq9dtqp_WQYvajgOUFYaanjkGoWXZ-XXbT0zULn_wljT58r1Oej--xUvh8L1MmDZvA")
					.header("login", "TestPJR")
					.queryString("out", "json")
					.asJson();
			response = uniResponse.getBody();
		} catch (UnirestException e) {
			log.severe(e.getMessage());
		}

		JSONArray cities = response.getArray();

		for (var i = 0; i < cities.length(); i++) {
			JSONObject jsonCity = cities.getJSONObject(i);
			responseCities.add(createCity(jsonCity.getString("name")));
		}

		return responseCities;
	}

	private City createCity(String name) {
		City city = new City();
		city.setName(name);
		return city;
	}
}

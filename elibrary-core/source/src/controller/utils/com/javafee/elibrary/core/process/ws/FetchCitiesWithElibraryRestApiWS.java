package com.javafee.elibrary.core.process.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class FetchCitiesWithElibraryRestApiWS implements Process, Runnable {
	@Setter
	private Consumer reloadAction;

	@Override
	public void execute() {
		Thread t = new Thread(this);
		t.start();
	}

	private void initializeCitiesWithElibraryRestApi() {
		List<City> citiesPackage = invokeElibraryRestApi();
		if (Optional.ofNullable(citiesPackage).isPresent() && !citiesPackage.isEmpty())
			Common.getCities().addAll(citiesPackage);
		Optional.ofNullable(reloadAction).ifPresent(e -> e.accept(null));
	}

	private List<City> invokeElibraryRestApi() {
		List responseCities = new ArrayList<City>();
		JsonNode response;
		HttpResponse<JsonNode> uniResponse;

		long fromQueryParam = SystemProperties.getInstance().getCitiesFromIndex() + (Common.getCitiesPackageSize() * SystemProperties.getInstance().getCitiesDataPackageNumber()),
				toQueryParam = fromQueryParam + (Common.getCitiesPackageSize() - 1);
		try {
			uniResponse = Unirest.get(SystemProperties.getConfigProperties().getProperty("app.api.url") + "/teryt/get-cities-from-file?from=" + fromQueryParam + "&to=" + toQueryParam)
					.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU5MTk5NTUwOCwiZXhwIjoxOTA3NDQ1NjAwfQ.X5QudAylDMyXy-mUQEsevQDOv9Wv4YOK8OCruvamGiIcu74SB8hmeOh3VA-7vz9RZZZaanbocVudV72DsMZZVg")
					.header("login", "admin")
					.queryString("out", "json")
					.asJson();
			response = uniResponse.getBody();
			JSONArray cities = response.getArray();

			if (cities.getJSONObject(0).length() > 0) {
				for (var i = 0; i < cities.length(); i++) {
					JSONObject jsonCity = cities.getJSONObject(i);
					responseCities.add(createCity(jsonCity.getString("name")));
				}
				incrementCitiesPackageNumber();
			} else {
				log.warning("Not able to get response from WS teryt/get-cities-from method");
			}
		} catch (UnirestException e) {
			log.severe(e.getMessage());
		}

		return responseCities;
	}

	private City createCity(String name) {
		City city = new City();
		city.setName(name);
		return city;
	}

	private void incrementCitiesPackageNumber() {
		SystemProperties.getInstance().setCitiesDataPackageNumber(SystemProperties.getInstance().getCitiesDataPackageNumber() + 1);
	}

	@Override
	public void run() {
		initializeCitiesWithElibraryRestApi();
	}
}

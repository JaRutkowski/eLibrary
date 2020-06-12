package com.javafee.elibrary.rest.api.service.teryt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.javafee.elibrary.rest.api.repository.dto.pojo.City;
import com.javafee.elibrary.rest.api.utils.Constants;
import com.opencsv.CSVReader;

@ApplicationScoped
public class GovTerytService {
	public List<City> getCitiesFromFile(Integer from, Integer to) throws IOException {
		List<City> result = new ArrayList<>();
		InputStreamReader govTerytCitiesFile = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(Constants.GOV_TERYT_CITIES_FILE_NAME),
				Constants.GOV_TERYT_CITIES_FILE_ENCODING);
		CSVReader reader = new CSVReader(govTerytCitiesFile);

		String[] line, cityData;
		int index = 0;
		while ((line = reader.readNext()) != null) {
			if (index != 0 && (index >= from && index <= to)) {
				cityData = line[0].split(Constants.GOV_TERYT_CITIES_FILE_SEPARATOR);
				System.out.println(cityData);
				result.add(mapCityCSVDataToCity(cityData));
			}
			index++;
		}
		return result;
	}

	private City mapCityCSVDataToCity(String[] cityData) {
		return new City(cityData[Constants.GOV_TERYT_CITIES_FILE_CITY_NAME_COL_NUMBER]);
	}
}

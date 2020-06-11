package com.javafee.elibrary.rest.api.service.teryt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.javafee.elibrary.rest.api.repository.dto.association.City;
import com.javafee.elibrary.rest.api.utils.Constants;
import com.opencsv.CSVReader;

@ApplicationScoped
public class GovTerytService {
	public List<City> getCitiesFromFile() throws IOException {
		File govTerytCitiesFile = new File(getClass().getClassLoader().getResource(Constants.GOV_TERYT_CITIES_FILE_NAME).getFile());

		CSVReader reader = new CSVReader(new FileReader(govTerytCitiesFile));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			System.out.println(nextLine[0] + nextLine[1] + "etc...");
		}
		return null;
	}
}

package com.javafee.elibrary.core.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.Getter;
import lombok.Setter;

public class HTMLProcessor {

	@Getter
	@Setter
	private String htmlString = null;
	private Document htmlDocument = null;

	public HTMLProcessor(String htmlDocument) {
		this.htmlString = htmlDocument;
	}

	public boolean validateWhitelistTags(Safelist whitelist) {
		return Jsoup.isValid(htmlString, whitelist);
	}

	public List<String> getMessagesList(boolean saneDocument) {
		if (saneDocument) {
			this.htmlDocument = Jsoup.parse(htmlString);
			this.htmlString = htmlDocument.toString();
		}

		List<String> resposeMessages = new ArrayList<String>();
		JsonNode response = null;
		HttpResponse<JsonNode> uniResponse;
		try {
			uniResponse = Unirest.post("https://validator.w3.org/nu/?out=json").header("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36")
					.header("Content-Type", "text/html; charset=UTF-8").queryString("out", "json")
					.body(htmlDocument.toString()).asJson();
			response = uniResponse.getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = response.getObject();
		JSONArray messages = jsonObject.getJSONArray("messages");

		for (var i = 0; i < messages.length(); i++) {
			JSONObject error = messages.getJSONObject(i);
			resposeMessages.add(error.getString("type").toUpperCase() + "\t (line: " + error.getInt("lastLine") + ") : "
					+ error.getString("message") + " Near: [" + error.getString("extract") + "]");
		}

		return resposeMessages;
	}

}

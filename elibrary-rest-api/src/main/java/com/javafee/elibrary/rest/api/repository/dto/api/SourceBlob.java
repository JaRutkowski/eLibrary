package com.javafee.elibrary.rest.api.repository.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SourceBlob {
	@JsonProperty
	private String url;
	@JsonProperty
	private String version;
	@JsonProperty
	private String checksum;
}

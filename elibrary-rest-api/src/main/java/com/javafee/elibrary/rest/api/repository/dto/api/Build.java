package com.javafee.elibrary.rest.api.repository.dto.api;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Build {
	@JsonProperty
	private String id;
	@JsonIgnore
	private String app;
	@JsonIgnore
	private String slug;
	@JsonIgnore
	private String user;
	@JsonProperty
	private String stack;
	@JsonProperty
	private String status;
	@JsonIgnore
	private String release;
	@JsonIgnore
	private String metadata;
	@JsonIgnore
	private String buildpacks;
	@JsonProperty(value = "created_at")
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date createdAt;
	@JsonProperty(value = "updated_at")
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date updatedAt;
	@JsonProperty(value = "source_blob")
	private SourceBlob sourceBlob;
	@JsonProperty(value = "output_stream_url")
	private String outputStreamUrl;
}

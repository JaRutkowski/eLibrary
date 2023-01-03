package com.javafee.elibrary.micro.service.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ELibraryMicroExceptionDto {
	public Date timestamp;
	public int status;
	public List<String> messages;
	public String path;
	public String method;
}

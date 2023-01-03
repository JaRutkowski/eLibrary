package com.javafee.elibrary.micro.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TestDto {
	private Integer id;
	private String name;
	private String text;
}

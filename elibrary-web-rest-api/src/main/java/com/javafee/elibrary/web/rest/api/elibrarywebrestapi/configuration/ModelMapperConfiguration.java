package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.UserData;

@RequestMapping("/api/v1/user-data")
public interface UserDataApi {
	@GetMapping
	List<UserData> findAll();
}

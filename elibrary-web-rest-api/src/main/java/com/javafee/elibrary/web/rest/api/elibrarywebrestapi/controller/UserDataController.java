package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller.api.UserDataApi;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.UserData;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.service.UserDataService;

@RestController
public class UserDataController implements UserDataApi {
	@Autowired
	private UserDataService userDataService;

	@Override
	public List<UserData> findAll() {
		return userDataService.findAll();
	}
}

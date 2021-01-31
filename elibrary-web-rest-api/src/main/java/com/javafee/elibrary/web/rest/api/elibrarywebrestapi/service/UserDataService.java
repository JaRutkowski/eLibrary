package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.UserData;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository.UserDataRepository;

@Service
public class UserDataService {
	@Autowired
	private UserDataRepository userDataRepository;

	public List<UserData> findAll() {
		return userDataRepository.findAll();
	}
}

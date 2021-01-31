package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
}

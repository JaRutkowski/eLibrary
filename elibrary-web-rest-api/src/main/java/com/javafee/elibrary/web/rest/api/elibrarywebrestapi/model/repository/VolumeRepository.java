package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.Volume;

public interface VolumeRepository extends JpaRepository<Volume, Long> {
}

package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller.api.ReservationApi;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.dto.LendDto;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.service.ReservationService;

import lombok.extern.java.Log;

@RestController
@Log
public class ReservationController implements ReservationApi {
	@Autowired
	private ReservationService reservationService;

	@Override
	public List<LendDto> findAllActiveLends(Integer idClient) {
		return reservationService.findAllActiveLends(idClient);
	}
}

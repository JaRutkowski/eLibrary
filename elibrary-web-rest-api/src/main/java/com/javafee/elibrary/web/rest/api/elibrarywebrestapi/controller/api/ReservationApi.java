package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.controller.api;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.dto.LendDto;

@RequestMapping("/api/v1/reservations")
public interface ReservationApi {
	@GetMapping("/active-lends/{idClient}")
	List<LendDto> findAllActiveLends(@PathVariable @NotNull @DecimalMin("0") Integer idClient);
}

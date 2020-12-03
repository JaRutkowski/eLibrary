package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lib_reservation")
public class Reservation {
	@Id
	@Column(name = "id_reservation", nullable = false)
	private Integer idReservation;

	@Column(name = "is_cancelled")
	private Boolean isCancelled = false;

	@Column(name = "is_active")
	private Boolean isActive = true;
}

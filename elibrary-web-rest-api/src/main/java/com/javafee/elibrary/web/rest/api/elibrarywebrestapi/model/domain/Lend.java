package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lib_lend")
public class Lend {
	@Id
	@Column(name = "id_lend", nullable = false)
	private Integer idLend;

	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;

	@Temporal(TemporalType.DATE)
	@Column(name = "lend_date", length = 13)
	private Date lendDate;

	@ManyToOne
	@JoinColumn(name = "id_volume")
	private Volume volume;

	@Temporal(TemporalType.DATE)
	@Column(name = "returned_date", length = 13)
	private Date returnDate;

	@Column(name = "is_returned")
	private Boolean isReturned = false;

	@ManyToOne
	@JoinColumn(name = "id_reservation")
	private Reservation reservation;
}

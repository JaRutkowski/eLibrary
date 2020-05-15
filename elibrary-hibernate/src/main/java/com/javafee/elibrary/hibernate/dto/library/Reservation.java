package com.javafee.elibrary.hibernate.dto.library;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@NamedQueries({
		@NamedQuery(name = "Reservation.checkIfVolumeActiveReservationExists", query = "from Reservation where id_volume = :idVolume " +
				"and is_active is not null and is_active = true and (is_cancelled is null or (is_cancelled is not null and is_cancelled = false))"),
		@NamedQuery(name = "Reservation.countActiveClientReservations", query = "select count(*) from Reservation where id_client = :idClient " +
				"and is_active is not null and is_active = true and (is_cancelled is null or (is_cancelled is not null and is_cancelled = false))")})
@Table(name = "lib_reservation")
@SequenceGenerator(name = "seq_lib_reservation", sequenceName = "seq_lib_reservation", allocationSize = 1)
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_reservation")
	@Column(name = "id_reservation", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idReservation;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_client", unique = false, nullable = true, insertable = true, updatable = true)
	private Client client = new Client();

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_volume", unique = false, nullable = true, insertable = true, updatable = true)
	private Volume volume = new Volume();

	@Temporal(TemporalType.DATE)
	@Column(name = "reservation_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date reservationDate;

	@Column(name = "is_cancelled", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isCancelled = false;

	@Column(name = "is_active", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isActive = true;
}

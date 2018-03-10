package com.javafee.hibernate.dto.library;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "lib_lend")
@SequenceGenerator(name = "seq_lib_lend", sequenceName = "seq_lib_lend", allocationSize = 1)
public class Lend implements java.lang.Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_lend")
	@Column(name = "id_lend", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idLend;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_client", unique = false, nullable = true, insertable = true, updatable = true)
	private Client client = new Client();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_volume")
	private Volume volume;

	@Temporal(TemporalType.DATE)
	@Column(name = "lend_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date lendDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "returned_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date returnedDate;

	@Column(name = "prolognation", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer prolognation;

	@Column(name = "is_returned", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isReturned = false;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_client", unique = false, nullable = true, insertable = true, updatable = true)
	private Client reservationClient;

	@Column(name = "queue_client", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer queueClient;

	@Override
	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}
}

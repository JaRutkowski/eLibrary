package com.javafee.hibernate.dto.library;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "lib_penalty")
@SequenceGenerator(name = "seq_lib_penalty", sequenceName = "seq_lib_penalty", allocationSize = 1)
public class Penalty {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_penalty")
	@Column(name = "id_penalty", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idPenalty;

	@Column(name = "penalty_value", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private BigDecimal penaltyValue;

	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date paymentDate;

	@OneToOne
	@JoinColumn(name = "id_lend")
	private Lend lend;
}

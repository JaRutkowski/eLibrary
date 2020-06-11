package com.javafee.elibrary.rest.api.repository.dto.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "com_authorization")
@SequenceGenerator(name = "seq_com_authorization", sequenceName = "seq_com_authorization", allocationSize = 1)
public class Authorization {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_authorization")
	@Column(name = "id_authorization", nullable = false)
	private Integer idAuthorization;

	@Column(name = "login", length = 80)
	private String login;

	@Column(name = "private_key", length = 80)
	private String privateKey;
}

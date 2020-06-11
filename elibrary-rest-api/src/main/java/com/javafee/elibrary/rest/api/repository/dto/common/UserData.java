package com.javafee.elibrary.rest.api.repository.dto.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "com_user_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
@SequenceGenerator(name = "seq_com_user_data", sequenceName = "seq_com_user_data", allocationSize = 1)
public class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_user_data")
	@Column(name = "id_user_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUserData;
	
	@Column(name = "login", nullable = false, length = 15)
	private String login;

	@Column(name = "password", nullable = false, length = 80)
	private String password;
}

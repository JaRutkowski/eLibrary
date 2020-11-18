package com.javafee.elibrary.rest.api.repository.dto.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "com_user_account", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class UserAccount {
	@Id
	@Column(name = "id_user_account", nullable = false)
	private Integer idUserAccount;

	@Column(name = "login", nullable = false, length = 15)
	private String login;

	@Column(name = "password", nullable = false, length = 80)
	private String password;
}

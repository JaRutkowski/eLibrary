package com.javafee.elibrary.hibernate.dto.common;

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
@Table(name = "com_user_account")
@SequenceGenerator(name = "seq_com_user_account", sequenceName = "seq_com_user_account", allocationSize = 1)
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_user_account")
	@Column(name = "id_user_account", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUserAccount;

	@Column(name = "number_of_failed_password_attempts", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Integer numberOfFailedPasswordAttempts = 0;

	@Column(name = "block_reason", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String blockReason;

	@Temporal(TemporalType.DATE)
	@Column(name = "block_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date blockDate;

	@Column(name = "blocked", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean blocked = false;
}

package com.javafee.elibrary.hibernate.dto.common;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
// TODO: checkWithComparingIdIfUserDataPeselExist
@NamedQueries({
		@NamedQuery(name = "UserAccount.checkIfUserDataLoginExist", query = "from UserAccount where login = :login")})
@Table(name = "com_user_account", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
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

	@Column(name = "registered", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean registered;

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date registrationDate;

	@Column(name = "login", unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	private String login;

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_system_properties")
	private SystemProperties systemProperties;
}

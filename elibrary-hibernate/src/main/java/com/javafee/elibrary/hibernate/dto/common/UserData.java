package com.javafee.elibrary.hibernate.dto.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
		@NamedQuery(name = "UserData.checkIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber"),
		@NamedQuery(name = "UserData.checkWithComparingIdIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber and idUserData != :id")})
@Table(name = "com_user_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"pesel"})})
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_user_data", sequenceName = "seq_com_user_data", allocationSize = 1)
public class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_user_data")
	@Column(name = "id_user_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUserData;

	@Column(name = "e_mail", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String eMail;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String name;

	@Column(name = "surname", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String surname;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date birthDate;

	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private Character sex;

	@Column(name = "pesel", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	private String peselNumber;

	@Column(name = "document_number", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String documentNumber;

	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String address;

	@Column(name = "city", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String city;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_user_account")
	private UserAccount userAccount;

	@Override
	public String toString() {
		String result = "";
		if (surname != null && name == null)
			result = surname;
		else if (surname == null && name != null)
			result = name;
		else if (surname != null && name != null)
			result = surname + " " + name;
		return result;
	}
}

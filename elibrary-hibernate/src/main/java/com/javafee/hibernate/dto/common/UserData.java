package com.javafee.hibernate.dto.common;

import com.javafee.hibernate.dto.association.City;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NamedQueries({
		@NamedQuery(name = "UserData.checkWithComparingIdIfUserDataLoginExist", query = "from UserData where login = :login and id != :id"),
		@NamedQuery(name = "UserData.checkIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber"),
		@NamedQuery(name = "UserData.checkWithComparingIdIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber and id != :id")})
@Table(name = "com_user_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"login", "pesel"})})
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_user_data", sequenceName = "seq_com_user_data", allocationSize = 1)
public class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_user_data")
	@Column(name = "id_user_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUserData;

	@Column(name = "login", unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	private String login;

	@Column(name = "e_mail", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String eMail;

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
	private String password;

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

	@Column(name = "registered", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean registered;

	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String address;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_city", unique = false, nullable = true, insertable = true, updatable = true)
	private City city;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_system_properties")
	private SystemProperties systemProperties;

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

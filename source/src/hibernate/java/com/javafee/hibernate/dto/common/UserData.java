package com.javafee.hibernate.dto.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.javafee.hibernate.dto.association.City;

@Entity
@NamedQueries({ @NamedQuery(name = "UserData.checkWithComparingIdIfUserDataLoginExist", query = "from UserData where login = :login and id != :id"), @NamedQuery(name = "UserData.checkIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber"),
		@NamedQuery(name = "UserData.checkWithComparingIdIfUserDataPeselExist", query = "from UserData where peselNumber = :peselNumber and id != :id") })
@Table(name = "com_user_data", uniqueConstraints = { @UniqueConstraint(columnNames = { "login", "pesel" }) })
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_user_data", sequenceName = "seq_com_user_data", allocationSize = 1)
public abstract class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_user_data")
	@Column(name = "id_user_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUserData;

	@Column(name = "login", unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	private String login;
	
	@Column(name = "e_mail", unique = true, nullable = false, insertable = true, updatable = true, length = 80)
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

	public Integer getIdUserData() {
		return idUserData;
	}

	public void setIdUserData(Integer idUserData) {
		this.idUserData = idUserData;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}
	
	public String getEMail() {
		return eMail;
	}
	
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPeselNumber() {
		return peselNumber;
	}

	public void setPeselNumber(String peselNumber) {
		this.peselNumber = peselNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}

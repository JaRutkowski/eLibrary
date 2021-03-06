package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "com_user_data")
public class UserData {
	@Id
	@Column(name = "id_user_data")
	private Integer idUserData;

	@Column(name = "e_mail", length = 80)
	private String eMail;
}

package com.javafee.hibernate.dto.common.message;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.javafee.hibernate.dto.common.UserData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mes_recipient")
@SequenceGenerator(name = "seq_mes_recipient", sequenceName = "seq_mes_recipient", allocationSize = 1)
public class Recipient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mes_recipient")
	@Column(name = "id_recipiet", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idRecipient;

	@OneToOne
	@JoinColumn(name = "id_message_recipient")
	private UserData userData;

	@Column(name = "is_cc", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isCC;

	@Column(name = "is_bcc", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isBCC;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_message", unique = false, nullable = true, insertable = true, updatable = true)
	private Message message;

	@Override
	public String toString() {
		return userData.toString();
	}

}
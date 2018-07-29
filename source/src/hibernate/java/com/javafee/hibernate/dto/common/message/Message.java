package com.javafee.hibernate.dto.common.message;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.javafee.hibernate.dto.common.UserData;

import lombok.Data;

@Data
@Entity
@Table(name = "mes_message")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_message", sequenceName = "seq_com_message", allocationSize = 1)
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_message")
	@Column(name = "id_message", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idMessage;

	@OneToOne
	@JoinColumn(name = "id_sender")
	private UserData sender;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pk.message")
	private Set<Recipient> recipient = new HashSet<Recipient>(0);

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	private String title;

	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 1200)
	private String content;

	@Temporal(TemporalType.DATE)
	@Column(name = "send_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date sendDate;
	
	@Column(name = "is_draft", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isDraft;
}

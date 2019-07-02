package com.javafee.hibernate.dto.common.message;

import com.javafee.hibernate.dto.association.MessageType;
import com.javafee.hibernate.dto.common.UserData;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mes_message")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_mes_message", sequenceName = "seq_mes_message", allocationSize = 1)
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mes_message")
	@Column(name = "id_message", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idMessage;

	@OneToOne
	@JoinColumn(name = "id_sender")
	private UserData sender;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_message_type", unique = false, nullable = true, insertable = true, updatable = true)
	private MessageType messageType;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "message")
	private Set<Recipient> recipient = new HashSet<Recipient>(0);

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	private String title;

	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true)
	@Lob
	private String content;

	@Temporal(TemporalType.DATE)
	@Column(name = "send_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date sendDate;

	@Column(name = "is_draft", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isDraft;
}
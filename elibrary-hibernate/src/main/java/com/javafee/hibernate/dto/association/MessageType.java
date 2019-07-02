package com.javafee.hibernate.dto.association;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mes_message_type")
@SequenceGenerator(name = "seq_mes_message_type", sequenceName = "seq_mes_message_type", allocationSize = 1)
public class MessageType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mes_message_type")
	@Column(name = "id_message_type", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idMessageType;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String description;
}

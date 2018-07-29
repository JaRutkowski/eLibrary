package com.javafee.hibernate.dto.common.message;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "message" })
@Embeddable
public class RecipientId implements Serializable {

	private static final long serialVersionUID = -7590781562455372972L;
	
	@ManyToOne
	private Message message;
}

package com.javafee.hibernate.dto.common.message;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.javafee.hibernate.dto.common.UserData;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(exclude = "pk")
@Table(name = "mes_recipient")
@AssociationOverrides({ @AssociationOverride(name = "pk.message", joinColumns = @JoinColumn(name = "id_message")),
		@AssociationOverride(name = "pk.libraryData", joinColumns = @JoinColumn(name = "id_library_data")) })
public class Recipient {
	@EmbeddedId
	private RecipientId pk;
	
	@OneToOne
	@JoinColumn(name = "id_recipient")
	private UserData userData;
	
	@Column(name = "is_cc", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isCC;
	
	@Column(name = "is_bcc", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isBCC;
}

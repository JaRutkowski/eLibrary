package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lib_volume")
public class Volume {
	@Id
	@Column(name = "id_volume", nullable = false)
	private Integer idVolume;

	@Column(name = "inventory_number", length = 13)
	private String inventoryNumber;

	@ManyToOne
	@JoinColumn(name = "id_book")
	private Book book;
}

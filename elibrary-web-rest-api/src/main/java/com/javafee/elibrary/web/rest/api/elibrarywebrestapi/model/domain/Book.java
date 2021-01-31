package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lib_book")
public class Book {
	@Id
	@Column(name = "id_book", nullable = false)
	private Integer idBook;

	@Column(name = "isbn_number", length = 13)
	private String isbnNumber;

	@Column(name = "title", length = 200)
	private String title;
}

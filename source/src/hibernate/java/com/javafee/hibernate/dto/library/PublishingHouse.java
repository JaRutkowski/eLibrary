package com.javafee.hibernate.dto.library;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lib_publishing_house")
@SequenceGenerator(name = "seq_lib_publishing_house", sequenceName = "seq_lib_publishing_house", allocationSize = 1)
public class PublishingHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_publishing_house")
	@Column(name = "id_publishing_house", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idPublishingHouse;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "publishingHouse")
	private Set<Book> book = new HashSet<Book>(0);

	public Integer getIdPublishingHouse() {
		return idPublishingHouse;
	}

	public void setIdPublishingHouse(Integer idPublishingHouse) {
		this.idPublishingHouse = idPublishingHouse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBook() {
		return book;
	}

	public void setBook(Set<Book> book) {
		this.book = book;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}

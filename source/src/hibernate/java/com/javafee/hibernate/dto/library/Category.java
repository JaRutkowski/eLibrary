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
@Table(name = "lib_category")
@SequenceGenerator(name = "seq_lib_category", sequenceName = "seq_lib_category", allocationSize = 1)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_category")
	@Column(name = "id_category", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idCategory;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Book> book = new HashSet<Book>(0);

	public Integer getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Integer idCategory) {
		this.idCategory = idCategory;
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

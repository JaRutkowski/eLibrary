package com.javafee.hibernate.dto.library;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Book> book = new HashSet<Book>(0);

	@Override
	public String toString() {
		return this.name;
	}
}

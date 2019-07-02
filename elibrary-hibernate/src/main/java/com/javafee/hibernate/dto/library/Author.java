package com.javafee.hibernate.dto.library;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "lib_author")
@SequenceGenerator(name = "seq_lib_author", sequenceName = "seq_lib_author", allocationSize = 1)
public class Author implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_author")
	@Column(name = "id_author", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idAuthor;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String name;

	@Column(name = "surname", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String surname;

	@Column(name = "nickname", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String nickname;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date birthDate;

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Book> book = new HashSet<Book>(0);

	@Override
	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		if (surname != null && name == null)
			result = surname;
		else if (surname == null && name != null)
			result = name;
		else if (surname != null && name != null)
			result = surname + " " + name;
		return result;
	}
}

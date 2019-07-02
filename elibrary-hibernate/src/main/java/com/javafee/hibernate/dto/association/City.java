package com.javafee.hibernate.dto.association;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "com_city")
@SequenceGenerator(name = "seq_com_city", sequenceName = "seq_com_city", allocationSize = 1)
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_city")
	@Column(name = "id_city", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idCity;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@Column(name = "postal_code", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	private String postalCode;

	@Override
	public String toString() {
		return this.name;
	}
}

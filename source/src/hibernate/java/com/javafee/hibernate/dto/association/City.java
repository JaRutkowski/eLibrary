package com.javafee.hibernate.dto.association;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

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

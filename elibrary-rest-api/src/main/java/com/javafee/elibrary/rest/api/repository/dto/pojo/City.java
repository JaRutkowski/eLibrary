package com.javafee.elibrary.rest.api.repository.dto.pojo;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City {
	@Column(name = "name", length = 200)
	private String name;

	@Override
	public String toString() {
		return this.name;
	}
}

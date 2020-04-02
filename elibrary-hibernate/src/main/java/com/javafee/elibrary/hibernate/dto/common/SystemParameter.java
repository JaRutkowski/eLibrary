package com.javafee.elibrary.hibernate.dto.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@NamedQuery(name = "SystemParameter.getByName", query = "from SystemParameter where name = :name")
@Table(name = "com_system_parameter")
@SequenceGenerator(name = "seq_com_system_parameter", sequenceName = "seq_com_system_parameter", allocationSize = 1)
public class SystemParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_system_parameter")
	@Column(name = "id_system_parameter", nullable = false)
	private Integer idSystemData;

	@Column(name = "name", length = 80, unique = true)
	private String name;

	@Column(name = "value", length = 80)
	private String value;

	@Column(name = "default_value", length = 80)
	private String defaultValue;

	public SystemParameter _this() {
		return this;
	}

	@Override
	public String toString() {
		String result = "[";
		if (name != null && value != null)
			result += (name + ", " + value);
		result += "]";
		return result;
	}
}

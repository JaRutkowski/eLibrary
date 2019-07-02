package com.javafee.hibernate.dto.common;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "com_system_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"license_number"})})
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_system_data", sequenceName = "seq_com_system_data", allocationSize = 1)
public abstract class SystemData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_system_data")
	@Column(name = "id_system_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idSystemData;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String name;

	@Column(name = "version", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String version;

	@Column(name = "build_number", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String buildNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "installation_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date installationDate;

	@Column(name = "has_license", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean hasLicense = false;

	@Column(name = "license_number", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String licenseNumber;
}

package com.javafee.hibernate.dto.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "com_system_data", uniqueConstraints = { @UniqueConstraint(columnNames = { "license_number" }) })
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

	public Integer getIdSystemData() {
		return idSystemData;
	}

	public void setIdSystemData(Integer idSystemData) {
		this.idSystemData = idSystemData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public Boolean getHasLicense() {
		return hasLicense;
	}

	public void setHasLicense(Boolean hasLicense) {
		this.hasLicense = hasLicense;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
}

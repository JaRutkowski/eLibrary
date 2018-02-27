package com.javafee.hibernate.dto.library;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.javafee.hibernate.dto.association.City;

@Entity
@Table(name = "lib_library_data")
@SequenceGenerator(name = "seq_lib_library_data", sequenceName = "seq_lib_library_data", allocationSize = 1)
public class LibraryData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_library_data")
	@Column(name = "id_library_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idLibraryData;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@Column(name = "branch", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String branch;

	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String address;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_city", unique = false, nullable = true, insertable = true, updatable = true)
	private City city;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.libraryData")
	private Set<LibraryWorker> libraryWorker = new HashSet<LibraryWorker>(0);

	public Integer getIdLibraryData() {
		return idLibraryData;
	}

	public void setIdLibraryData(Integer idLibraryData) {
		this.idLibraryData = idLibraryData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<LibraryWorker> getLibraryWorker() {
		return this.libraryWorker;
	}

	public void setLibraryWorker(Set<LibraryWorker> libraryWorker) {
		this.libraryWorker = libraryWorker;
	}
}
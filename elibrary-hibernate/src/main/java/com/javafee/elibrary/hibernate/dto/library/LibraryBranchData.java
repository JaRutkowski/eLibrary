package com.javafee.elibrary.hibernate.dto.library;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.javafee.elibrary.hibernate.dto.association.City;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "libraryData")
@EqualsAndHashCode(exclude = "libraryData")
@Table(name = "lib_library_branch_data")
@SequenceGenerator(name = "seq_lib_library_branch_data", sequenceName = "seq_lib_library_branch_data", allocationSize = 1)
public class LibraryBranchData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_library_branch_data")
	@Column(name = "id_library_branch_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idLibraryBranchData;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String name;

	@Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String address;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_city", unique = false, nullable = true, insertable = true, updatable = true)
	private City city;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "id_library_data")
	private LibraryData libraryData;
}

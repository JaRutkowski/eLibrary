package com.javafee.elibrary.hibernate.dto.library;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "libraryData",
			cascade = {CascadeType.DETACH, CascadeType.MERGE,//
					CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<LibraryBranchData> libraryBranchData = new HashSet<LibraryBranchData>(0);
}
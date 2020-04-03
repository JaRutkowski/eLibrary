package com.javafee.elibrary.hibernate.dto.library;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.javafee.elibrary.hibernate.dto.common.SystemData;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "libraryData")
@Entity
@Table(name = "lib_library_system_data")
@PrimaryKeyJoinColumn(name = "id_library_system_data", referencedColumnName = "id_system_data")
public class LibrarySystemData extends SystemData {
	@ManyToOne
	@JoinColumn(name = "id_library_data", unique = false, nullable = true, insertable = true, updatable = true)
	private LibraryData libraryData;
}

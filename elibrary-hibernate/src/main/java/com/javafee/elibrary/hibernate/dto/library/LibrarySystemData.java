package com.javafee.elibrary.hibernate.dto.library;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.javafee.elibrary.hibernate.dto.common.SystemData;

@Entity
@Table(name = "lib_library_system_data")
@PrimaryKeyJoinColumn(name = "id_library_system_data", referencedColumnName = "id_system_data")
public class LibrarySystemData extends SystemData {

}
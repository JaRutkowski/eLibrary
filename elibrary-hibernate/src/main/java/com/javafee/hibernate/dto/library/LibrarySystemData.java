package com.javafee.hibernate.dto.library;

import com.javafee.hibernate.dto.common.SystemData;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "lib_library_system_data")
@PrimaryKeyJoinColumn(name = "id_library_system_data", referencedColumnName = "id_system_data")
public class LibrarySystemData extends SystemData {

}

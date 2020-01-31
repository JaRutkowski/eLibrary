package com.javafee.elibrary.hibernate.dto.library;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.javafee.elibrary.hibernate.dto.common.SystemProperties;

@Entity
@Table(name = "lib_system_properties")
@PrimaryKeyJoinColumn(name = "id_library_system_properties", referencedColumnName = "id_system_properties")
public class LibrarySystemProperties extends SystemProperties {

}

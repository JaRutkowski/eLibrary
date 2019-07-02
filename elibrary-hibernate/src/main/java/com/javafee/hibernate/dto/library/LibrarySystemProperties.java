package com.javafee.hibernate.dto.library;

import com.javafee.hibernate.dto.common.SystemProperties;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "lib_system_properties")
@PrimaryKeyJoinColumn(name = "id_library_system_properties", referencedColumnName = "id_system_properties")
public class LibrarySystemProperties extends SystemProperties {

}

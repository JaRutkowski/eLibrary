package com.javafee.hibernate.dto.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.javafee.hibernate.dto.association.Language;

@Entity
@Table(name = "com_system_properties")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_system_properties", sequenceName = "seq_com_system_properties", allocationSize = 1)
public abstract class SystemProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_system_properties")
	@Column(name = "id_system_properties", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idSystemData;

	@OneToOne
	@JoinColumn(name = "id_language")
	private Language language;

	@Column(name = "font_size", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private Integer fontSize;
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
}

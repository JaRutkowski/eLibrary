package com.javafee.elibrary.hibernate.dto.common;

import com.javafee.elibrary.hibernate.dto.association.Language;

import javax.persistence.CascadeType;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@ToString(exclude = "userAccount")
@EqualsAndHashCode(exclude = "userAccount")
@Table(name = "com_system_properties")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_com_system_properties", sequenceName = "seq_com_system_properties", allocationSize = 1)
public class SystemProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_com_system_properties")
	@Column(name = "id_system_properties", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idSystemData;

	@OneToOne
	@JoinColumn(name = "id_language")
	private Language language;

	@Column(name = "color", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	private String color;

	@Column(name = "font_name", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String fontName;

	@Column(name = "font_size", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private Integer fontSize;

	@Column(name = "template_directory", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String templateDirectory;

	@OneToOne(mappedBy = "systemProperties", cascade = CascadeType.ALL)
	private UserAccount userAccount;
}

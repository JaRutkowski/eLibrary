package com.javafee.hibernate.dto.common;

import com.javafee.hibernate.dto.association.Language;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
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

	@Column(name = "font_size", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private Integer fontSize;

	@Column(name = "template_directory", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String templateDirectory;

	@OneToOne(mappedBy = "systemProperties", cascade = CascadeType.ALL)
	private UserData userData;
}

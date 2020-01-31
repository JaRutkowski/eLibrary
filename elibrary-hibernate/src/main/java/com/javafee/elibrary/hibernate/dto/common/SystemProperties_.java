package com.javafee.elibrary.hibernate.dto.common;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.javafee.elibrary.hibernate.dto.association.Language;

@StaticMetamodel(SystemProperties.class)
public class SystemProperties_ {
	public static volatile SingularAttribute<SystemProperties, Long> idSystemData;
	public static volatile SingularAttribute<SystemProperties, Language> language;
	public static volatile SingularAttribute<SystemProperties, String> color;
	public static volatile SingularAttribute<SystemProperties, Integer> fontSize;
	public static volatile SingularAttribute<SystemProperties, String> templateDirectory;
	public static volatile SingularAttribute<SystemProperties, UserData> userData;
}

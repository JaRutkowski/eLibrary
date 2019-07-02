package com.javafee.hibernate.dto.common;

import com.javafee.hibernate.dto.association.Language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SystemProperties.class)
public class SystemProperties_ {
	public static volatile SingularAttribute<SystemProperties, Long> idSystemData;
	public static volatile SingularAttribute<SystemProperties, Language> language;
	public static volatile SingularAttribute<SystemProperties, Integer> fontSize;
	public static volatile SingularAttribute<SystemProperties, String> templateDirectory;
	public static volatile SingularAttribute<SystemProperties, UserData> userData;
}

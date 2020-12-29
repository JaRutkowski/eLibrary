package com.javafee.elibrary.hibernate.dto.association;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Language.class)
public class Language_ {
	public static volatile SingularAttribute<Language, Long> idLanguage;
	public static volatile SingularAttribute<Language, String> name;
}

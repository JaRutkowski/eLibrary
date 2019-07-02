package com.javafee.hibernate.dto.association;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MessageType.class)
public class MessageType_ {
	public static volatile SingularAttribute<MessageType, Long> idMessageType;
	public static volatile SingularAttribute<MessageType, String> name;
	public static volatile SingularAttribute<MessageType, Integer> description;
}

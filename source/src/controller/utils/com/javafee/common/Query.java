package com.javafee.common;

public class Query {

	public enum TabOutboxPageEventQuery {
<<<<<<< HEAD
		MESSAGE_ALL("from Message mes where mes.isDraft is null or mes.isDraft is false"), //
		MESSAGE_BY_SENDER_LOGIN(
				"from Message mes where mes.sender.login = ? and mes.isDraft is null or mes.isDraft is false"), //
		DRAFT_MESSAGE_BY_SENDER_LOGIN("from Message mes where mes.sender.login = ? and mes.isDraft is true"), //
		MESSAGE_BY_MESSAGE_TYPE(
				"from Message mes where mes.messageType = ? and mes.isDraft is null or mes.isDraft is false"), //
		DRAFT_MESSAGE_BY_MESSAGE_TYPE("from Message mes where mes.messageType = ? and mes.isDraft is true"), //
		MESSAGE_BY_SENDER_LOGIN_AND_MESSAGE_TYPE("from Message mes where mes.sender.login = ? and mes.messageType = ?"), //
		DISTINCT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login " //
						+ "and rec.message.isDraft is null or rec.message.isDraft is false"), //
		DISTINCT_DRAFT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login " //
						+ "and rec.message.isDraft is true"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender.login = ? " //
						+ "and mes.isDraft is null or mes.isDraft is false"), //
		DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender.login = ? " //
						+ "and mes.isDraft is true");
=======
		MESSAGE_ALL("from Message"), //
		MESSAGE_ALL_SYSTEM("from Message mes where mes.sender is null"), //
		MESSAGE_BY_SENDER_LOGIN("from Message mes where mes.sender.login = ?"), //
		DRAFT_MESSAGE_BY_SENDER_LOGIN("from Message mes where mes.sender.login = ? and mes.isDraft is true"), //
		MESSAGE_BY_MESSAGE_TYPE("from Message mes where mes.messageType = ?"), //
		DRAFT_MESSAGE_BY_MESSAGE_TYPE("from Message mes where mes.messageType = ? and mes.isDraft is true"), //
		MESSAGE_BY_SENDER_LOGIN_AND_MESSAGE_TYPE("from Message mes where mes.sender.login = ? and mes.messageType = ?"), //
		DISTINCT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login"),
		DISTINCT_DRAFT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login and rec.message.isDraft is true"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_FROM_SYSTEM(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender is null"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender.login = ?"), //
		DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender.login = ? and mes.isDraft is true");
>>>>>>> 459512eb151387298a33e1b9275396e13e875ec6

		private final String value;

		TabOutboxPageEventQuery(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

}

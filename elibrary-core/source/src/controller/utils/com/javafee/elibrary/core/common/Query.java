package com.javafee.elibrary.core.common;

public class Query {

	public enum TabOutboxPageEventQuery {
		MESSAGE_ALL("from Message mes where mes.isDraft is null or mes.isDraft is false"), //
		MESSAGE_BY_SENDER_LOGIN(
				"from Message mes where mes.sender.userAccount.login = ?0 and (mes.isDraft is null or mes.isDraft is false)"), //
		DRAFT_MESSAGE_BY_SENDER_LOGIN("from Message mes where mes.sender.userAccount.login = ?0 and mes.isDraft is true"), //
		MESSAGE_BY_MESSAGE_TYPE(
				"from Message mes where mes.messageType = ?0 and (mes.isDraft is null or mes.isDraft is false)"), //
		DRAFT_MESSAGE_BY_MESSAGE_TYPE("from Message mes where mes.messageType = ?0 and mes.isDraft is true"), //
		MESSAGE_BY_SENDER_LOGIN_AND_MESSAGE_TYPE(
				"from Message mes where mes.sender.userAccount.login = ?0 and mes.messageType = ?1"), //
		DISTINCT_MESSAGE_RECIPIENT_ALL(
				"select distinct rec.userData from Recipient rec where rec.message.isDraft is null or rec.message.isDraft is false"), //
		DISTINCT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.userAccount.login = :login " //
						+ "and rec.message.isDraft is null or rec.message.isDraft is false"), //
		DISTINCT_DRAFT_MESSAGE_RECIPIENT_ALL(
				"select distinct rec.userData from Recipient rec where rec.message.isDraft is true"), //
		DISTINCT_DRAFT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.userAccount.login = :login " //
						+ "and rec.message.isDraft is true"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ?0 " //
						+ "and mes.isDraft is null or mes.isDraft is false"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ?0 and mes.sender.userAccount.login = ?1 " //
						+ "and mes.isDraft is null or mes.isDraft is false"), //
		DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ?0 " //
						+ "and mes.isDraft is true"),
		DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ?0 and mes.sender.userAccount.login = ?1 " //
						+ "and mes.isDraft is true");

		private final String value;

		TabOutboxPageEventQuery(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ActionsTabbedForm {
		SYSTEM_PROPERTIES_BY_USER_ACCOUNT_ID("from SystemProperties sp where sp.userAccount.idUserAccount = :idUserAccount");

		private final String value;

		ActionsTabbedForm(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}
}

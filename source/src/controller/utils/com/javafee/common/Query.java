package com.javafee.common;

public class Query {

	public enum TabOutboxPageEventQuery {
		MESSAGE_ALL("from Message"), //
		MESSAGE_ALL_SYSTEM("from Message mes where mes.sender is null"), //
		MESSAGE_BY_SENDER_LOGIN("from Message mes where mes.sender.login = ?"), //
		DISTINCT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN(
				"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_FROM_SYSTEM(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender is null"),
		DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN(
				"select distinct mes from Message mes join fetch mes.recipient r where r.userData = ? and mes.sender.login = ?");

		private final String value;

		TabOutboxPageEventQuery(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

}

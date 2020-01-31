package com.javafee.elibrary.hibernate.dao.common;

public class Query {
	public enum ProcessQuery {
		FEED_ADMINISTRATOR_DATA("insert into public.com_user_data" +
				"(id_user_data, address, birth_date, document_number, e_mail, login, name, password, pesel, registered, sex, surname, id_city, id_system_properties)" +
				" values(?0, null, null, null, null, ?1, null, ?2, null, null, null, null, null, null)"),
		FEED_MESSAGE_TYPES("insert into public.mes_message_type" +
				"(id_message_type, description, name)" +
				" values(0, '', ?0)," +
				"(1, '', ?1)," +
				"(2, '', ?2)");

		private final String value;

		ProcessQuery(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}
}

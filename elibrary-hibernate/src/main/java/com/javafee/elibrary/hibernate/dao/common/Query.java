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
				"(2, '', ?2)"),
		FEED_SYSTEM_PARAMETERS_DATA("insert into public.com_system_parameter" +
				"(id_system_parameter, name, value, default_value)" +
				" values(1, ?0, ?1, ?1)," +
				"(2, ?2, ?3, ?3)," +
				"(3, ?4, ?5, ?5)," +
				"(4, ?6, ?7, ?7)," +
				"(5, ?8, ?9, ?9)"),
		FEED_SYSTEM_PARAMETERS_DATA_UPDATE_SYSTEM_DATA("update public.com_system_data" +
				" set number_of_system_parameters = ?0" +
				" where id_system_data = ?1"
		);

		private final String value;

		ProcessQuery(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}
}

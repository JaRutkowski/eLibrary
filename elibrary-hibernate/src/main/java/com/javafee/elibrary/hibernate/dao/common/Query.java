package com.javafee.elibrary.hibernate.dao.common;

public class Query {
	public enum ProcessQuery {
		FEED_ADMINISTRATOR_DATA("insert into public.com_user_data" +
				"(id_user_data, login, password)" +
				" values(?0, ?1, ?2)"),
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
				"(5, ?8, ?9, ?9)," +
				"(6, ?10, ?11, ?11)," +
				"(7, ?12, ?13, ?13)," +
				"(8, ?14, ?15, ?15)," +
				"(9, ?16, ?17, ?17)"),
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

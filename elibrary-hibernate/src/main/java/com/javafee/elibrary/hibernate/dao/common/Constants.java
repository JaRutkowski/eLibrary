package com.javafee.elibrary.hibernate.dao.common;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import javax.swing.KeyStroke;

public final class Constants {
	public enum ClientTableColumn {
		COL_PESEL_NUMBER(0), COL_DOCUMENT_NUMBER(1), COL_LOGIN(2), COL_E_MAIL(3), COL_NAME(4), COL_SURNAME(5),
		COL_ADDRESS(6), COL_CITY(7), COL_SEX(8), COL_BIRTH_DATE(9), COL_REGISTERED(10);

		private final Integer value;

		ClientTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static ClientTableColumn getByNumber(int clientTableSelectedIndex) {
			return Stream.of(ClientTableColumn.values())
					.filter(item -> item.getValue().equals(clientTableSelectedIndex)).findFirst().get();
		}
	}

	public enum BookTableColumn {
		COL_TITLE(0), COL_ISBN_NUMBER(1), COL_NUMBER_OF_PAGES(2), COL_NUMBER_OF_TOMES(3);

		private final Integer value;

		BookTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static BookTableColumn getByNumber(int bookTableSelectedIndex) {
			return Stream.of(BookTableColumn.values()).filter(item -> item.getValue().equals(bookTableSelectedIndex))
					.findFirst().get();
		}
	}

	public enum VolumeTableColumn {
		COL_BOOK_TITLE(0), AUTHOR(1), CATEGORY(2), PUBLISHING_HOUSE(3), COL_BOOK_ISBN_NUMBER(4),
		COL_INVENTORY_NUMBER(5), COL_BOOK_NUMBER_OF_PAGE(6), COL_BOOK_NUMBER_OF_TOME(7), COL_IS_READING_ROOM(8),
		COL_IS_RESERVERD(9), COL_IS_LOANED(10);

		private final Integer value;

		VolumeTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static VolumeTableColumn getByNumber(int volumeTableSelectedIndex) {
			return Stream.of(VolumeTableColumn.values())
					.filter(item -> item.getValue().equals(volumeTableSelectedIndex)).findFirst().get();
		}
	}

	public enum LendTableColumn {
		COL_CLIENT_BASIC_DATA(0), COL_CLIENT_PESEL_NUMBER(1), COL_CLIENT_DOCUMENT_NUMBER(2), COL_VOLUME_BOOK_TITLE(3),
		COL_VOLUME_BOOK_ISBN_NUMBER(4), COL_VOLUME_INVENTORY_NUMBER(5), COL_LEND_DATE(6), COL_RETURNED_DATE(7),
		COL_PENALTY(8), LENDED(9);

		private final Integer value;

		LendTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static LendTableColumn getByNumber(int lendTableSelectedIndex) {
			return Stream.of(LendTableColumn.values()).filter(item -> item.getValue().equals(lendTableSelectedIndex))
					.findFirst().get();
		}
	}

	public enum AuthorTableColumn {
		COL_NAME(0), COL_SURNAME(1), COL_BIRTH_DATE(2);

		private final Integer value;

		AuthorTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static AuthorTableColumn getByNumber(int authorTableSelectedIndex) {
			return Stream.of(AuthorTableColumn.values())
					.filter(item -> item.getValue().equals(authorTableSelectedIndex)).findFirst().get();
		}
	}

	public enum CategoryTableColumn {
		COL_NAME(0);

		private final Integer value;

		CategoryTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static CategoryTableColumn getByNumber(int categoryTableSelectedIndex) {
			return Stream.of(CategoryTableColumn.values())
					.filter(item -> item.getValue().equals(categoryTableSelectedIndex)).findFirst().get();
		}
	}

	public enum PublishingHouseTableColumn {
		COL_NAME(0);

		private final Integer value;

		PublishingHouseTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static PublishingHouseTableColumn getByNumber(int publishingHouseTableSelectedIndex) {
			return Stream.of(PublishingHouseTableColumn.values())
					.filter(item -> item.getValue().equals(publishingHouseTableSelectedIndex)).findFirst().get();
		}
	}

	public enum OutboxTableColumn {
		COL_RECIPIENT_SIMPLE_DATA(0), COL_RECIPIENT_EMAIL(1), COL_TOPIC(2), COL_CONTENT(3), COL_SENDER_SIMPLE_DATA(4),
		COL_DATE(5);

		private final Integer value;

		OutboxTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static OutboxTableColumn getByNumber(int outboxTableSelectedIndex) {
			return Stream.of(OutboxTableColumn.values())
					.filter(item -> item.getValue().equals(outboxTableSelectedIndex)).findFirst().get();
		}
	}

	public enum Role {
		ADMIN, WORKER_ACCOUNTANT, WORKER_LIBRARIAN, CLIENT
	}

	public enum Context {
		ADDITION, MODIFICATION, CANCELED, LOAN, READING_ROOM
	}

	public static final int MAIN_SPLASH_SCREEN_DURATION = 1000;
	public static final String MAIN_SPLASH_SCREEN_IMAGE = "source/resources/images/splashScreen.jpg";

	public static final String APPLICATION_NAME = "e-library";
	public static String APPLICATION_LANGUAGE = "pl";
	public static final String APPLICATION_LANGUAGE_PL = "pl";
	public static final String APPLICATION_LANGUAGE_EN = "en";
	public static final int APPLICATION_NETWORK_SERVICE_LISTENER_DURATION = 2;
	public static final Object APPLICATION_COMBO_BOX_BLANK_OBJECT = null;
	public static final SimpleDateFormat APPLICATION_TIME_FORMAT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat APPLICATION_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat APPLICATION_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	public static final String APPLICATION_CURRENCY = "PLN";
	public static final Integer APPLICATION_GENERATE_PASSWORD_LENGTH = 16;
	public static final String APPLICATION_EMAIL = "nreply.elibrary@gmail.com";
	public static final String APPLICATION_EMAIL_PASSWORD = "Admin95!";
	public static final String APPLICATION_TEMPLATE_EXTENSION = ".html";
	public static final String APPLICATION_TEMPLATE_EXTENSION_DESCRIPTION = "HTML file";
	public static final String APPLICATION_TEMPLATE_DIRECTORY_NAME = "eLib-mes-templates";
	public static final String APPLICATION_TEMPLATE_ENCODING = "UTF-8";

	public static final Dimension START_FORM_MINIMUM_SIZE = new Dimension(300, 200);
	public static final Dimension EMAIL_FORM_MINIMUM_SIZE = new Dimension(800, 700);

	public static final KeyStroke SHURTCUT_SAVE_TEMPLATE = KeyStroke.getKeyStroke(KeyEvent.VK_T,
			Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	public static final KeyStroke SHURTCUT_LOAD_TEMPLATE = KeyStroke.getKeyStroke(KeyEvent.VK_L,
			Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	public static final KeyStroke SHURTCUT_MANAGE_TEMPLATE = KeyStroke.getKeyStroke(KeyEvent.VK_M,
			Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

	public static final Integer MAX_PROLONGNATION = 5;

	public static final String RADIO_BUTTON_AUTHOR = "Author";
	public static final String RADIO_BUTTON_CATEGORY = "Category";
	public static final String RADIO_BUTTON_PUBLISHING_HOUSE = "Publishing house";

	public static final String LANGUAGE_RESOURCE_BUNDLE = "messages";
	public static final String APPLICATION_PROPERTIES = "application.properties";

	public static final String DATA_BASE_PACKAGE_TO_SCAN = "com.javafee.elibrary.hibernate.dto";
	public static final Character DATA_BASE_MALE_SIGN = 'M';
	public static final Character DATA_BASE_FEMALE_SIGN = 'F';
	public static final Boolean DATA_BASE_REGISTER_DEFAULT_FLAG = false;
	public static final Integer DATA_BASE_ADMIN_ID = 0;
	public static final String DATA_BASE_ADMIN_LOGIN = "admin";
	public static final String DATA_BASE_ADMIN_PASSWORD = "f7e3c24e1a04758097f69be41aa3cf18";
	public static final Integer DATA_BASE_INVENTORY_NUMBER_LENGTH = 13;
	public static final Integer DATA_BASE_ISBN_NUMBER_LENGTH = 13;
	public static final Integer DATA_BASE_PESEL_NUMBER_LENGHT = 11;
	public static final String DATA_BASE_MESSAGE_TYPE_USR_MESSAGE = "usr_message";
	public static final String DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE = "sys_message";
	public static final String DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION = "sys_notification";
	public static final String DATA_BASE_DELETED_MESSAGE_RECIPIENT_VALUE = "null";
	public static final String DATA_BASE_DELETED_MESSAGE_SENDER_VALUE = "null";
	public static final String DATA_BASE_SYSTEM_PARAMETER_PENALTY_NAME = "APPLICATION_PENALTY_VALUE";
	public static final String DATA_BASE_SYSTEM_PARAMETER_PENALTY_VALUE = "0.6";
	public static final String DATA_BASE_SYSTEM_PARAMETER_RESERVATIONS_LIMIT_NAME = "APPLICATION_RESERVATIONS_LIMIT";
	public static final String DATA_BASE_SYSTEM_PARAMETER_RESERVATIONS_LIMIT_VALUE = "3";
	public static final String DATA_BASE_SYSTEM_PARAMETER_EMAIL_NAME = "APPLICATION_EMAIL_ADDRESS";
	public static final String DATA_BASE_SYSTEM_PARAMETER_EMAIL_VALUE = "nreply.elibrary@gmail.com";
	public static final String DATA_BASE_SYSTEM_PARAMETER_EMAIL_PASSWORD_NAME = "APPLICATION_EMAIL_PASSWORD";
	public static final String DATA_BASE_SYSTEM_PARAMETER_EMAIL_PASSWORD_VALUE = "Admin95!";
	public static final String DATA_BASE_SYSTEM_PARAMETER_GENERATE_PASSWORD_LENGTH_NAME = "APPLICATION_GENERATED_PASSWORD_LENGTH";
	public static final String DATA_BASE_SYSTEM_PARAMETER_GENERATE_PASSWORD_LENGTH_VALUE = "16";
	public static final String DATA_BASE_SYSTEM_PARAMETER_TEMPLATE_DIRECTORY_NAME_NAME = "APPLICATION_TEMPLATE_DIRECTORY_NAME";
	public static final String DATA_BASE_SYSTEM_PARAMETER_TEMPLATE_DIRECTORY_NAME_VALUE = "eLib-mes-templates";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MIN_PASSWORD_LENGTH_NAME = "APPLICATION_MIN_PASSWORD_LENGTH";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MIN_PASSWORD_LENGTH_VALUE = "8";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MAX_PASSWORD_LENGTH_NAME = "APPLICATION_MAX_PASSWORD_LENGTH";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MAX_PASSWORD_LENGTH_VALUE = "16";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE_NAME = "APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE_VALUE = "20000";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT_NAME = "APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT_VALUE = "3";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY_NAME = "APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY";
	public static final String DATA_BASE_SYSTEM_PARAMETER_APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY_VALUE = "true";
	public static final Integer DATA_BASE_NUMBER_OF_SYSTEM_PARAMETERS = 11;
	public static final Integer DATA_BASE_SYSTEM_DATA_ID = 1;
	public static final Date DATA_BASE_SYSTEM_DATA_INITIALIZATION_DATE = new Date();
	public static final Integer DATA_BASE_LIBRARY_DATA_ID = 1;
	public static final String DATA_BASE_LIBRARY_DATA_NAME = "Library";
	public static final Integer DATA_BASE_LIBRARY_BRANCH_DATA_ID = 1;
	public static final String DATA_BASE_LIBRARY_BRANCH_DATA_NAME = "Main branch";

	public static double PENALTY_VALUE = 0.60;
}

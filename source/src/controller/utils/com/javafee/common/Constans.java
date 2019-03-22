package com.javafee.common;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import javax.swing.KeyStroke;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class Constans {
	@Getter
	@AllArgsConstructor
	public enum Tab_Client {
		TAB_LIBRARY(0);

		private final Integer value;

		public static Tab_Client getByNumber(int tabbedPaneSelectedIndex) {
			return Stream.of(Tab_Client.values()).filter(item -> item.getValue().equals(tabbedPaneSelectedIndex))
					.findFirst().get();
		}
	}

	@Getter
	@AllArgsConstructor
	public enum Tab_Worker {
		TAB_CLIENT(0), TAB_LIBRARY(1), TAB_BOOK(2), TAB_LOAN_SERVICE(3);

		private final Integer value;

		public static Tab_Worker getByNumber(int tabbedPaneSelectedIndex) {
			return Stream.of(Tab_Worker.values()).filter(item -> item.getValue().equals(tabbedPaneSelectedIndex))
					.findFirst().get();
		}
	}

	@Getter
	@AllArgsConstructor
	public enum Tab_Accountant {
		TAB_CLIENT(0), TAB_LIBRARY(1), TAB_BOOK(2), TAB_LOAN_SERVICE(3), TAB_ADM_DICTIONARY(4), TAB_ADM_WORKER(5);

		private final Integer value;

		public static Tab_Accountant getByNumber(int tabbedPaneSelectedIndex) {
			return Stream.of(Tab_Accountant.values()).filter(item -> item.getValue().equals(tabbedPaneSelectedIndex))
					.findFirst().get();
		}
	}

	@Getter
	@AllArgsConstructor
	public enum Tab_Email {
		TAB_CREATE_PAGE(0), TAB_SENDED_PAGE(1), TAB_DRAFT_PAGE(2), TAB_TEMPLATE_PAGE(3);

		private final Integer value;

		public static Tab_Email getByNumber(int tabbedPaneSelectedIndex) {
			return Stream.of(Tab_Email.values()).filter(item -> item.getValue().equals(tabbedPaneSelectedIndex))
					.findFirst().get();
		}
	}

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
		ADMIN, WORKER_ACCOUNTANT, WORKER_LIBRARIAN, CLIENT;
	}

	public enum Context {
		ADDITION, MODIFICATION, CANCELED, LOAN, READING_ROOM;
	}

	public static final int MAIN_SPLASH_SCREEN_DURATION = 1000;
	public static final String MAIN_SPLASH_SCREEN_IMAGE = "source/resources/images/splashScreen.jpg";

	public static final String APPLICATION_NAME = "e-library";
	public static String APPLICATION_LANGUAGE = "pl";
	public static final String APPLICATION_LANGUAGE_PL = "pl";
	public static final String APPLICATION_LANGUAGE_EN = "en";
	public static final int APPLICATION_NETWORK_SERVICE_LISTENER_DURATION = 2;
	public static final Object APPLICATION_COMBO_BOX_BLANK_OBJECT = null;
	public static final SimpleDateFormat APPLICATION_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat APPLICATION_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	public static final String APPLICATION_CURRENCY = "PLN";
	public static final Integer APPLICATION_MIN_PASSWORD_LENGTH = 8;
	public static final Integer APPLICATION_MAX_PASSWORD_LENGTH = 16;
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

	public static final String ROLE_ADMIN = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.ROLE_ADMIN");
	public static final String WORKER_ACCOUNTANT = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.WORKER_ACCOUNTANT");
	public static final String WORKER_LIBRARIAN = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.WORKER_LIBRARIAN");
	public static final String CLIENT = SystemProperties.getInstance().getResourceBundle().getString("constans.CLIENT");

	public static final Integer MAX_PROLONGNATION = 5;

	public static final String RADIO_BUTTON_AUTHOR = "Author";
	public static final String RADIO_BUTTON_CATEGORY = "Category";
	public static final String RADIO_BUTTON_PUBLISHING_HOUSE = "Publishing house";

	public static final String LANGUAGE_RESOURCE_BUNDLE = "messages";

	public static final String DATA_BASE_PACKAGE_TO_SCAN = "com.javafee.hibernate.dto";
	public static final String DATA_BASE_URL = "127.0.0.1:5432/library";
	public static final String DATA_BASE_USER = "postgres";
	public static final String DATA_BASE_PASSWORD = "admin123";
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
	public static final String DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION = "sys_notifiaction";

	public static double PENALTY_VALUE = 0.60;
}

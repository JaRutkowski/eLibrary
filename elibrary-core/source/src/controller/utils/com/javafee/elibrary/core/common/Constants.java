package com.javafee.elibrary.core.common;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.Stream;

import javax.swing.KeyStroke;
import javax.swing.UIManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class Constants {
	@Getter
	@AllArgsConstructor
	public enum Panel_Settings {
		ROOT(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuRoot")),
		GENERAL_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuGeneral")),
		THEME_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuTheme")),
		FONT_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuFont")),
		ACCOUNT_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuAccount")),
		PERSONAL_DATA_CHANGE_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuDataChange")),
		PASSWORD_CHANGE_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuPasswordChange")),
		SYSTEM_DATA_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuSystemData")),
		SYSTEM_PARAMETERS_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuSystemParameters")),
		SYSTEM_DATA_FEEDING_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuSystemDataFeeding")),
		SYSTEM_PROCESSES_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuProcesses")),
		SYSTEM_MONITOR_PANEL(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuMonitor"));

		private final String name;

		public static Panel_Settings getByName(String panelSettingsSelectedName) {
			return Stream.of(Panel_Settings.values()).filter(item -> item.getName().equals(panelSettingsSelectedName))
					.findFirst().get();
		}
	}

	@Getter
	@AllArgsConstructor
	public enum Tab_Client {
		TAB_LIBRARY(0), TAB_CLIENT_LOAN(1), TAB_CLIENT_RESERVATION(2);

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

	@Getter
	@AllArgsConstructor
	public enum Tab_ClientReservations {
		TAB_CREATE_RESERVATIONS(0), TAB_BROWSE_RESERVATIONS(1);

		private final Integer value;

		public static Tab_ClientReservations getByNumber(int tabbedPaneSelectedIndex) {
			return Stream.of(Tab_ClientReservations.values()).filter(item -> item.getValue().equals(tabbedPaneSelectedIndex))
					.findFirst().get();
		}
	}

	@Getter
	@AllArgsConstructor
	public enum SystemDataFeedingTableData {
		ADMINISTRATOR_DATA(new String[]{SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.administratorData.feedTypeCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.administratorData.dataCol")}),
		MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA(new String[]{SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.messagesAndNotificationsDictionaryData.feedTypeCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.messagesAndNotificationsDictionaryData.dataCol")}),
		SYSTEM_PARAMETERS_DATA(new String[]{SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.systemParametersData.feedTypeCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.systemParametersData.dataCol")}),
		SYSTEM_DATA(new String[]{SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.systemData.feedTypeCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.systemData.dataCol")}),
		LIBRARY_DATA(new String[]{SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.libraryData.feedTypeCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableData.libraryData.dataCol")});

		private final String[] value;

		@Getter
		@AllArgsConstructor
		public enum SystemDataFeedingTableRow {
			ROW_ADMINISTRATOR_DATA(0), ROW_MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA(1), ROW_SYSTEM_PARAMETERS_DATA(2), ROW_SYSTEM_DATA(3), ROW_LIBRARY_DATA(4);

			private final Integer index;

			public static SystemDataFeedingTableRow getByNumber(int systemDataFeedingTableRowIndex) {
				return Stream.of(SystemDataFeedingTableRow.values())
						.filter(item -> item.getIndex().equals(systemDataFeedingTableRowIndex)).findFirst().get();
			}
		}

		public static SystemDataFeedingTableData getByName(String systemDataFeedingTableDataName) {
			return Stream.of(SystemDataFeedingTableData.values()).filter(item -> item.getValue().equals(systemDataFeedingTableDataName))
					.findFirst().get();
		}
	}


	public enum ClientTableColumn {
		COL_PESEL_NUMBER(0), COL_DOCUMENT_NUMBER(1), COL_LOGIN(2), COL_E_MAIL(3), COL_NAME(4), COL_SURNAME(5),
		COL_ADDRESS(6), COL_CITY(7), COL_SEX(8), COL_BIRTH_DATE(9), COL_REGISTERED(10), COL_BLOCKED(11);

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

	public enum ClientLoanTableColumn {
		COL_VOLUME_BOOK_TITLE(0), COL_VOLUME_BOOK_ISBN_NUMBER(1), COL_VOLUME_INVENTORY_NUMBER(2), COL_LEND_DATE(3), COL_RETURNED_DATE(4),
		COL_PENALTY(5), COL_IS_PROLONGATED(6);

		private final Integer value;

		ClientLoanTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static ClientLoanTableColumn getByNumber(int lendTableSelectedIndex) {
			return Stream.of(ClientLoanTableColumn.values()).filter(item -> item.getValue().equals(lendTableSelectedIndex))
					.findFirst().get();
		}
	}


	public enum LendClientReservationTableColumn {
		COL_VOLUME_BOOK_TITLE(0), COL_VOLUME_BOOK_ISBN_NUMBER(1), COL_VOLUME_INVENTORY_NUMBER(2), COL_LEND_DATE_OR_RESERVATION_DATE(3), COL_RETURNED_DATE_OR_IS_CANCELLED(4);

		private final Integer value;

		LendClientReservationTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static LendClientReservationTableColumn getByNumber(int lendClientReservationTableSelectedIndex) {
			return Stream.of(LendClientReservationTableColumn.values())
					.filter(item -> item.getValue().equals(lendClientReservationTableSelectedIndex))
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

	public enum SystemDataFeedingTableColumn {
		COL_FEEDING_TYPE(0), COL_DATA(1), COL_ACTION(2);

		private final Integer value;

		SystemDataFeedingTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static SystemDataFeedingTableColumn getByNumber(int systemDataFeedingTableSelectedIndex) {
			return Stream.of(SystemDataFeedingTableColumn.values())
					.filter(item -> item.getValue().equals(systemDataFeedingTableSelectedIndex)).findFirst().get();
		}
	}

	public enum Button_Type {
		ACCEPT, DENY
	}

	public enum Role {
		ADMIN, WORKER_ACCOUNTANT, WORKER_LIBRARIAN, CLIENT
	}

	public enum Context {
		ADDITION, MODIFICATION, CANCELED, LOAN, READING_ROOM
	}

	@AllArgsConstructor
	@Getter
	public enum BlockReason {
		WRONG_PASSWORD("Wrong password");

		private final String value;

		public static BlockReason getByNumber(String blockReason) {
			return Stream.of(BlockReason.values())
					.filter(item -> item.getValue().equals(blockReason)).findFirst().get();
		}
	}

	public static final String LANGUAGE_RESOURCE_BUNDLE = "messages";
	public static final String APPLICATION_PROPERTIES = "application.properties";

	public static final int MAIN_SPLASH_SCREEN_DURATION = 1000;
	public static final String MAIN_SPLASH_SCREEN_IMAGE = "source/resources/images/splashScreen.jpg";

	public static final String APPLICATION_NAME = "e-library";
	public static String APPLICATION_LANGUAGE = "pl";
	public static final String APPLICATION_LANGUAGE_PL = "pl";
	public static final String APPLICATION_LANGUAGE_EN = "en";
	public static final String APPLICATION_CSV_EXTENSION = ".csv";
	public static final String APPLICATION_XLS_EXTENSION = ".xls";
	public static final String APPLICATION_XLSX_EXTENSION = ".xlsx";
	public static final String APPLICATION_PDF_EXTENSION = ".pdf";
	public static final char APPLICATION_CSV_SEPARATOR = ',';
	public static final Color APPLICATION_DEFAULT_COLOR = new Color(237, 245, 248);
	public static final Font APPLICATION_DEFAULT_FONT = UIManager.getDefaults().getFont("TabbedPane.font");
	public static final int APPLICATION_NETWORK_SERVICE_LISTENER_DURATION = 2;
	public static final Object APPLICATION_COMBO_BOX_BLANK_OBJECT = null;
	public static final SimpleDateFormat APPLICATION_TIME_FORMAT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat APPLICATION_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat APPLICATION_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	public static final Integer APPLICATION_MAX_PROLONGNATION = 5;
	public static final Long APPLICATION_PREDEFINED_COMBO_BOX_PACKAGE_SIZE = 20000L;
	public static Pair<Integer, Double> APPLICATION_PROLONGATION_PERIOD = new Pair<>(Calendar.MONTH, 1.0);
	public static final String APPLICATION_CURRENCY = "PLN";
	public static final String APPLICATION_TEMPLATE_EXTENSION = ".html";
	public static final String APPLICATION_TEMPLATE_EXTENSION_DESCRIPTION = "HTML file";
	public static final String APPLICATION_TEMPLATE_ENCODING = "UTF-8";

	public static final String APPLICATION_PENALTY_VALUE = "APPLICATION_PENALTY_VALUE";
	public static final String APPLICATION_RESERVATIONS_LIMIT = "APPLICATION_RESERVATIONS_LIMIT";
	public static final String APPLICATION_EMAIL_ADDRESS = "APPLICATION_EMAIL_ADDRESS";
	public static final String APPLICATION_EMAIL_PASSWORD = "APPLICATION_EMAIL_PASSWORD";
	public static final String APPLICATION_TEMPLATE_DIRECTORY_NAME = "APPLICATION_TEMPLATE_DIRECTORY_NAME";
	public static final String APPLICATION_GENERATED_PASSWORD_LENGTH = "APPLICATION_GENERATED_PASSWORD_LENGTH";
	public static final String APPLICATION_MIN_PASSWORD_LENGTH = "APPLICATION_MIN_PASSWORD_LENGTH";
	public static final String APPLICATION_MAX_PASSWORD_LENGTH = "APPLICATION_MAX_PASSWORD_LENGTH";
	public static final String APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE = "APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE";
	public static final String APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT = "APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT";
	public static final String APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY = "APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY";

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

	public static final String RADIO_BUTTON_AUTHOR = "Author";
	public static final String RADIO_BUTTON_CATEGORY = "Category";
	public static final String RADIO_BUTTON_PUBLISHING_HOUSE = "Publishing house";

	public static final double SPINNER_INITIAL_VALUE_PENALTY = 0;
	public static final double DOUBLE_SPINNER_STEP_VALUE_PENALTY = 0.1;
	public static final double SPINNER_MINIMUM_VALUE_PENALTY = 0;
	public static final double SPINNER_MAXIMUM_VALUE_PENALTY = 100;
	public static final Integer SPINNER_MINIMUM_VALUE_RESERVATION_LIMIT = 0;
	public static final Integer SPINNER_MAXIMUM_VALUE_RESERVATION_LIMIT = 100;
	public static final Integer SPINNER_MINIMUM_VALUE_PASSWORD_LENGTH = 1;
	public static final Integer SPINNER_MAXIMUM_VALUE_PASSWORD_LENGTH = 30;
	public static final Integer SPINNER_MINIMUM_VALUE_COMBO_BOX_DATA_PACKAGE_SIZE = 1;
	public static final Integer SPINNER_MAXIMUM_VALUE_COMBO_BOX_DATA_PACKAGE_SIZE = 300000;
	public static final Integer SPINNER_MINIMUM_VALUE_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT = 1;
	public static final Integer SPINNER_MAXIMUM_VALUE_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT = 20;

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
	public static final Integer DATA_BASE_SYSTEM_DATA_ID = 1;
}

package com.javafee.elibrary.core.common;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.javafee.elibrary.core.common.networkservice.NetworkServiceListener;
import com.javafee.elibrary.core.common.timerservice.TimerServiceListener;
import com.javafee.elibrary.core.common.watchservice.WatchServiceListener;
import com.javafee.elibrary.core.emailform.TabTemplatePageEvent;
import com.javafee.elibrary.core.mail.MailSender;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.tabbedform.Actions;
import com.javafee.elibrary.core.tabbedform.clients.ClientTablePanel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Worker;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.CharacterRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordGenerator;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public final class Common {

	private static WatchServiceListener watchServiceListener = null;

	private static NetworkServiceListener networkServiceListener = null;

	private static TimerServiceListener timerServiceListener = null;

	@Getter
	@Setter
	private static List<City> cities = new ArrayList<>();

	public static final String createMd5(String password) {
		String md5 = null;
		if (password != null)
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				digest.update(password.getBytes(), 0, password.length());
				md5 = new BigInteger(1, digest.digest()).toString(16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		return md5;
	}

	public static final int diffDatesByPeriodType(Date first, Date second, int period) {
		Calendar firstCalendar = Calendar.getInstance(),
				secondCalender = Calendar.getInstance();
		firstCalendar.setTime(first);
		secondCalender.setTime(second);
		return secondCalender.get(period) - firstCalendar.get(period);
	}

	public static final String generatePassword() {
		PasswordGenerator generator = new PasswordGenerator();

		// create character rules to generate passwords with
		List<CharacterRule> rules = new ArrayList<CharacterRule>();
		rules.add(new DigitCharacterRule(1));
		rules.add(new NonAlphanumericCharacterRule(1));
		rules.add(new UppercaseCharacterRule(1));
		rules.add(new LowercaseCharacterRule(1));

		return generator.generatePassword(Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH).getValue()), rules);
	}

	public static final boolean checkPasswordStrength(String password) {
		boolean result = false;
		// password must be between APPLICATION_MIN_PASSWORD_LENGTH and APPLICATION_MAX_PASSWORD_LENGTH chars long
		LengthRule lengthRule = new LengthRule(Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()));
		// don't allow whitespace
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		// control allowed characters
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		// require at least 1 digit in passwords
		charRule.getRules().add(new DigitCharacterRule(1));
		// require at least 1 non-alphanumeric char
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		// require at least 1 upper case char
		charRule.getRules().add(new UppercaseCharacterRule(1));
		// require at least 1 lower case char
		charRule.getRules().add(new LowercaseCharacterRule(1));
		// require at least 3 of the previous rules be met
		charRule.setNumberOfCharacteristics(3);
		// don't allow alphabetical sequences
		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();
		// don't allow numerical sequences of length 3
		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3, true);
		// don't allow qwerty sequences
		QwertySequenceRule qwertySeqRule = new QwertySequenceRule();
		// don't allow 4 repeat characters
		RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);
		// group all rules together in a List
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		ruleList.add(charRule);
		ruleList.add(alphaSeqRule);
		ruleList.add(numSeqRule);
		ruleList.add(qwertySeqRule);
		ruleList.add(repeatRule);

		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(password));
		RuleResult ruleResult = validator.validate(passwordData);

		if (ruleResult.isValid()) {
			log.info("Valid password");
			result = true;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> void prepareBlankComboBoxElement(List<T> comboBoxDataList) {
		comboBoxDataList.add((T) Constants.APPLICATION_COMBO_BOX_BLANK_OBJECT);
	}

	public static void prepareMoreComboBoxCityElement(List comboBoxDataList) {
		City moreElement = new City();
		moreElement.setName(SystemProperties.getInstance().getResourceBundle().getString("comboBoxMoreElement"));
		comboBoxDataList.add(comboBoxDataList.size(), moreElement);
	}

	public static Long getCitiesPackageSize() {
		return SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE) != null
				? Long.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE).getValue())
				: Constants.APPLICATION_PREDEFINED_COMBO_BOX_PACKAGE_SIZE;
	}

	public static void removeMoreComboBoxCityElementIfExists() {
		if (!Common.getCities().isEmpty() &&
				(Common.getCities().size() % ((getCitiesPackageSize() * (SystemProperties.getInstance().getCitiesDataPackageNumber())) + 1)) == 0)
			Common.getCities().remove(Common.getCities().size() - 1);
	}

	public static List prepareIconListForExportImportComboBox() {
		List itemList = null;
		try {
			itemList = List.of(
					new ImageIcon(new ImageIcon(ImageIO.read(ClientTablePanel.class.getResource("/images/csv-ico.svg")))
							.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH), Constants.APPLICATION_CSV_EXTENSION),
					new ImageIcon(new ImageIcon(ImageIO.read(ClientTablePanel.class.getResource("/images/excel-ico.svg")))
							.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH), Constants.APPLICATION_XLSX_EXTENSION),
					new ImageIcon(new ImageIcon(ImageIO.read(ClientTablePanel.class.getResource("/images/pdf-ico.svg")))
							.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH), Constants.APPLICATION_PDF_EXTENSION));
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return itemList;
	}

	public static void fillUserDataPanel(RegistrationPanel registrationPanel, UserData userData) {
		// Pesel number
		registrationPanel.getTextFieldPeselNumber().setText(userData.getPeselNumber() != null ? userData.getPeselNumber() : "");
		// Document number
		registrationPanel.getTextFieldDocumentNumber().setText(userData.getDocumentNumber() != null ? userData.getDocumentNumber() : "");
		// Login
		registrationPanel.getTextFieldLogin().setText(userData.getLogin() != null ? userData.getLogin() : "");
		// Email
		registrationPanel.getTextFieldEMail().setText(userData.getEMail() != null ? userData.getEMail() : "");
		// Name
		registrationPanel.getTextFieldName().setText(userData.getName() != null ? userData.getName() : "");
		// Surname
		registrationPanel.getTextFieldSurname().setText(userData.getSurname() != null ? userData.getSurname() : "");
		// Address
		registrationPanel.getTextFieldAddress().setText(userData.getAddress() != null ? userData.getAddress() : "");
		// City
		registrationPanel.getComboBoxCity().setSelectedItem(userData.getCity());
		// Sex
		if (userData.getSex() != null && Constants.DATA_BASE_MALE_SIGN.toString().equals(userData.getSex().toString()))
			registrationPanel.getGroupRadioButtonSex().setSelected(registrationPanel.getRadioButtonMale().getModel(), true);
		else if (userData.getSex() != null && Constants.DATA_BASE_FEMALE_SIGN.toString().equals(userData.getSex().toString()))
			registrationPanel.getGroupRadioButtonSex().setSelected(registrationPanel.getRadioButtonFemale().getModel(), true);
		// Birth date
		try {
			registrationPanel.getDateChooserBirthDate().setDate(userData.getBirthDate() != null
					? Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT.format(userData.getBirthDate()))
					: null);
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}
	}

	public static List<List<Object>> extractDataFromTableModel(AbstractTableModel defaultTableModel, boolean withHeader) {
		Vector<Vector> dataVector = new Vector<>();
		if (withHeader) insertHeaderRow(dataVector, defaultTableModel);
		extractDataFromAbstractTableModel(dataVector, defaultTableModel);
		List<List<Object>> resultList = new ArrayList<>();
		for (Vector row : dataVector) {
			resultList.add(new ArrayList<>(row));
		}
		return resultList;
	}

	private static Vector<Vector> insertHeaderRow(Vector<Vector> dataVector, AbstractTableModel abstractTableModel) {
		Vector row = new Vector();
		for (var columnIndex = 0; columnIndex < abstractTableModel.getColumnCount(); columnIndex++) {
			row.add(abstractTableModel.getColumnName(columnIndex));
		}
		dataVector.add(row);
		return dataVector;
	}

	private static Vector<Vector> extractDataFromAbstractTableModel(Vector<Vector> dataVector, AbstractTableModel abstractTableModel) {
		for (var rowIndex = 0; rowIndex < abstractTableModel.getRowCount(); rowIndex++) {
			Vector row = new Vector();
			for (var columnIndex = 0; columnIndex < abstractTableModel.getColumnCount(); columnIndex++) {
				row.add(abstractTableModel.getValueAt(rowIndex, columnIndex));
			}
			dataVector.add(row);
		}
		return dataVector;
	}

	public static Integer clearMessagesRecipientData(Integer idUserData) {
		HibernateUtil.beginTransaction();
		Integer recordsUpdatedCount = HibernateUtil.getSession().createQuery("update Recipient set userData = " + Constants.DATA_BASE_DELETED_MESSAGE_RECIPIENT_VALUE + " where userData.idUserData = ?0")
				.setParameter(0, idUserData).executeUpdate();
		HibernateUtil.commitTransaction();
		return recordsUpdatedCount;
	}

	public static Integer clearMessagesSenderData(Integer idUserData) {
		if (HibernateUtil.getSession().getTransaction().getStatus() != TransactionStatus.ACTIVE)
			HibernateUtil.beginTransaction();
		Integer recordsUpdatedCount = HibernateUtil.getSession().createQuery("update Message set sender = " + Constants.DATA_BASE_DELETED_MESSAGE_SENDER_VALUE + " where sender.idUserData = ?0")
				.setParameter(0, idUserData).executeUpdate();
		HibernateUtil.commitTransaction();
		return recordsUpdatedCount;
	}

	public static boolean isAdmin(String login, String password) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(login)
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(Common.createMd5(password));
	}

	public static boolean isAdmin(Worker worker) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(worker.getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(worker.getPassword());
	}

	public static boolean isAdmin(Client client) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(client.getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(client.getPassword());
	}

	public static boolean isAdmin(UserData userData) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(userData.getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(userData.getPassword());
	}

	public static boolean checkInternetConnectivity() {
		Process process;
		try {
			process = java.lang.Runtime.getRuntime().exec("ping www.geeksforgeeks.org");
			return process.waitFor() == 0;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	public static String checkEmailServerConnectivity() {
		String result = null;
		MailSender mailSender = new MailSender();
		try {
			mailSender.validateConnection();
		} catch (MessagingException e) {
			result = e.getMessage();
		}
		return result;
	}

	public static void registerWatchServiceListener(TabTemplatePageEvent tabTemplatePageEvent,
	                                                Consumer<TabTemplatePageEvent> c) {
		watchServiceListener = new WatchServiceListener();
		watchServiceListener.initialize(tabTemplatePageEvent, c);
	}

	public static void unregisterWatchServiceListener() {
		if (watchServiceListener != null)
			watchServiceListener.destroy();
	}

	public static boolean isWatchServiceRunning() {
		return watchServiceListener != null && watchServiceListener.isRunning();
	}

	public static void registerNetworkServiceListener(Actions actions) {
		networkServiceListener = new NetworkServiceListener();
		networkServiceListener.initialize(actions);
	}

	public static void unregisterNetworkServiceListener() {
		if (networkServiceListener != null)
			networkServiceListener.destroy();
	}

	public static boolean isNetworkServiceRunning() {
		return networkServiceListener != null && networkServiceListener.isRunning();
	}

	public static void registerTimerServiceListenerSingleton(JLabel label) {
		if (timerServiceListener == null)
			timerServiceListener = new TimerServiceListener();
		timerServiceListener.initialize(label);
	}

	public static void unregisterTimerServiceListenerSingleton() {
		if (timerServiceListener != null)
			timerServiceListener.destroy();
	}
}

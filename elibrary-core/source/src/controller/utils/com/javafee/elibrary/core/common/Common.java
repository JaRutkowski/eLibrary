package com.javafee.elibrary.core.common;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.swing.JLabel;

import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.javafee.elibrary.core.common.networkservice.NetworkServiceListener;
import com.javafee.elibrary.core.common.timerservice.TimerServiceListener;
import com.javafee.elibrary.core.common.watchservice.WatchServiceListener;
import com.javafee.elibrary.core.emailform.TabTemplatePageEvent;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.tabbedform.Actions;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
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
import lombok.extern.java.Log;

@Log
public final class Common {

	private static WatchServiceListener watchServiceListener = null;

	private static NetworkServiceListener networkServiceListener = null;

	private static TimerServiceListener timerServiceListener = null;

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
			return !process.waitFor(100, TimeUnit.MILLISECONDS);
		} catch (IOException | InterruptedException e) {
			return false;
		}
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

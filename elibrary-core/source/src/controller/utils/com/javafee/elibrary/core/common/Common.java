package com.javafee.elibrary.core.common;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import org.apache.http.HttpStatus;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.javafee.elibrary.core.common.dto.Build;
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
import com.javafee.elibrary.hibernate.dto.common.UserAccount;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Worker;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public final class Common {

	private WatchServiceListener watchServiceListener = null;

	private NetworkServiceListener networkServiceListener = null;

	private TimerServiceListener timerServiceListener = null;

	@Getter
	@Setter
	private List<City> cities = new ArrayList<>();

	public String createMd5(String password) {
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

	public int diffDatesByPeriodType(Date first, Date second, int period) {
		Calendar firstCalendar = Calendar.getInstance(),
				secondCalender = Calendar.getInstance();
		firstCalendar.setTime(first);
		secondCalender.setTime(second);
		return secondCalender.get(period) - firstCalendar.get(period);
	}

	public String getMethodReference(String methodName) {
		StackWalker walker = StackWalker.getInstance();
		StackWalker.StackFrame stackFrame = walker.walk(frames -> frames.collect(Collectors.toList()).get(1));
		return stackFrame.getClassName() + "#" + stackFrame.getMethodName() + "#" + methodName;
	}

	public String generatePassword() {
		PasswordGenerator generator = new PasswordGenerator();

		// create character rules to generate passwords with
		List<CharacterRule> rules = new ArrayList<CharacterRule>();
		rules.add(new DigitCharacterRule(1));
		rules.add(new NonAlphanumericCharacterRule(1));
		rules.add(new UppercaseCharacterRule(1));
		rules.add(new LowercaseCharacterRule(1));

		return generator.generatePassword(Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH).getValue()), rules);
	}

	public boolean checkPasswordStrength(String password) {
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

	public static void blockUserAccount(UserData userData, boolean withParam, Date blockDate, String blockReason) {
		UserAccount userAccount = userData.getUserAccount();
		userAccount.setBlocked(Boolean.TRUE);
		userAccount.setBlockDate(blockDate);
		userAccount.setBlockReason(blockReason);

		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(userAccount);
		HibernateUtil.commitTransaction();

		if (withParam)
			Params.getInstance().add("BLOCKED", Constants.LogInFailureCause.BLOCKED);
	}

	public static void unblockUserAccount(UserData userData) {
		UserAccount userAccount = userData.getUserAccount();
		userAccount.setNumberOfFailedPasswordAttempts(0);
		userAccount.setBlocked(Boolean.FALSE);
		userAccount.setBlockDate(null);
		userAccount.setBlockReason(null);

		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(userAccount);
		HibernateUtil.commitTransaction();
	}

	@SuppressWarnings("unchecked")
	public <T> void prepareBlankComboBoxElement(List<T> comboBoxDataList) {
		comboBoxDataList.add((T) Constants.APPLICATION_COMBO_BOX_BLANK_OBJECT);
	}

	public void prepareMoreComboBoxCityElement(List comboBoxDataList) {
		if (Optional.ofNullable(comboBoxDataList).isPresent() && !comboBoxDataList.isEmpty()) {
			City moreElement = new City();
			moreElement.setName(SystemProperties.getInstance().getResourceBundle().getString("comboBoxMoreElement"));
			comboBoxDataList.add(comboBoxDataList.size(), moreElement);
		}
	}

	public Long getCitiesPackageSize() {
		return SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE) != null
				? Long.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE).getValue())
				: Constants.APPLICATION_PREDEFINED_COMBO_BOX_PACKAGE_SIZE;
	}

	public void removeMoreComboBoxCityElementIfExists() {
		if (!Common.getCities().isEmpty() &&
				SystemProperties.getInstance().getResourceBundle().getString("comboBoxMoreElement")
						.equals(Common.getCities().get(Common.getCities().size() - 1).getName()))
			Common.getCities().remove(Common.getCities().size() - 1);
	}

	public List prepareIconListForExportImportComboBox() {
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

	public void fillUserDataPanel(RegistrationPanel registrationPanel, UserData userData) {
		// Pesel number
		registrationPanel.getTextFieldPeselNumber().setText(userData.getPeselNumber() != null ? userData.getPeselNumber() : "");
		// Document number
		registrationPanel.getTextFieldDocumentNumber().setText(userData.getDocumentNumber() != null ? userData.getDocumentNumber() : "");
		// Login
		registrationPanel.getTextFieldLogin().setText(userData.getUserAccount().getLogin() != null ? userData.getUserAccount().getLogin() : "");
		// Email
		registrationPanel.getTextFieldEMail().setText(userData.getEMail() != null ? userData.getEMail() : "");
		// Name
		registrationPanel.getTextFieldName().setText(userData.getName() != null ? userData.getName() : "");
		// Surname
		registrationPanel.getTextFieldSurname().setText(userData.getSurname() != null ? userData.getSurname() : "");
		// Address
		registrationPanel.getTextFieldAddress().setText(userData.getAddress() != null ? userData.getAddress() : "");
		// City
		City city = new City();
		city.setName(userData.getCity());
		registrationPanel.getComboBoxCity().setSelectedItem(city);
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

	public List<List<Object>> extractDataFromTableModel(AbstractTableModel defaultTableModel, boolean withHeader) {
		Vector<Vector> dataVector = new Vector<>();
		if (withHeader) insertHeaderRow(dataVector, defaultTableModel);
		extractDataFromAbstractTableModel(dataVector, defaultTableModel);
		List<List<Object>> resultList = new ArrayList<>();
		for (Vector row : dataVector) {
			resultList.add(new ArrayList<>(row));
		}
		return resultList;
	}

	private Vector<Vector> insertHeaderRow(Vector<Vector> dataVector, AbstractTableModel abstractTableModel) {
		Vector row = new Vector();
		for (var columnIndex = 0; columnIndex < abstractTableModel.getColumnCount(); columnIndex++) {
			row.add(abstractTableModel.getColumnName(columnIndex));
		}
		dataVector.add(row);
		return dataVector;
	}

	private Vector<Vector> extractDataFromAbstractTableModel(Vector<Vector> dataVector, AbstractTableModel abstractTableModel) {
		for (var rowIndex = 0; rowIndex < abstractTableModel.getRowCount(); rowIndex++) {
			Vector row = new Vector();
			for (var columnIndex = 0; columnIndex < abstractTableModel.getColumnCount(); columnIndex++) {
				row.add(abstractTableModel.getValueAt(rowIndex, columnIndex));
			}
			dataVector.add(row);
		}
		return dataVector;
	}

	public Integer clearMessagesRecipientData(Integer idUserData) {
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

	public Map getEmailModuleEventsMethodsMapParam() {
		return Optional.ofNullable(Params.getInstance().get("EMAIL_MODULE_EVENTS_METHODS")).isPresent()
				? ((Map<String, Consumer>) Params.getInstance().get("EMAIL_MODULE_EVENTS_METHODS"))
				: new HashMap<String, Consumer>();
	}

	public void invokeEmailModuleEventsMethods() {
		if (Params.getInstance().contains("EMAIL_MODULE_EVENTS_METHODS"))
			((Map<String, Consumer>) Params.getInstance().get("EMAIL_MODULE_EVENTS_METHODS")).entrySet()
					.stream().forEach(entry -> entry.getValue().accept(null));
	}

	public boolean isAdminLogin(String login) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(login);
	}

	public boolean isAdmin(String login, String password) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(login)
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(Common.createMd5(password));
	}

	public boolean isAdmin(Worker worker) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(worker.getUserAccount().getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(worker.getUserAccount().getPassword());
	}

	public boolean isAdmin(Client client) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(client.getUserAccount().getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(client.getUserAccount().getPassword());
	}

	public boolean isAdmin(UserData userData) {
		return Constants.DATA_BASE_ADMIN_LOGIN.equals(userData.getUserAccount().getLogin())
				&& Constants.DATA_BASE_ADMIN_PASSWORD.equals(userData.getUserAccount().getPassword());
	}

	public boolean checkInternetConnectivity() {
		Process process;
		try {
			process = java.lang.Runtime.getRuntime().exec("ping www.geeksforgeeks.org");
			return process.waitFor() == 0;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	public Pair<Boolean, String> checkELibraryApiConnectivityAndGetHealthStatus() {
		StringBuilder response = new StringBuilder();
		Boolean connectivity = false;
		try {
			HttpResponse httpResponse = Unirest.get(SystemProperties.getConfigProperties().getProperty("app.api.url") + "/monitor/health")
					.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU5MTk5NTUwOCwiZXhwIjoxOTA3NDQ1NjAwfQ.X5QudAylDMyXy-mUQEsevQDOv9Wv4YOK8OCruvamGiIcu74SB8hmeOh3VA-7vz9RZZZaanbocVudV72DsMZZVg")
					.header("login", "admin").asString();
			connectivity = HttpStatus.SC_OK == httpResponse.getStatus();
			response.append(httpResponse.getStatus()).append(" ").append(httpResponse.getStatusText());
		} catch (UnirestException e) {
			response.append(e.getCause().getMessage());
		}
		return new Pair<>(connectivity, response.toString());
	}

	public Build fetchSystemVersion() {
		Build[] responseVersion = null;
		HttpResponse<Build[]> uniResponse;
		//TODO Move to the properties or fetch from API
		String apiId = "03fd2411-2ff3-476e-af88-efad0dee8fc0";
		try {
			uniResponse = Unirest.get(SystemProperties.getConfigProperties().getProperty("app.api.url") + "/heroku-management/latest-builds")
					.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU5MTk5NTUwOCwiZXhwIjoxOTA3NDQ1NjAwfQ.X5QudAylDMyXy-mUQEsevQDOv9Wv4YOK8OCruvamGiIcu74SB8hmeOh3VA-7vz9RZZZaanbocVudV72DsMZZVg")
					.header("login", "admin")
					.queryString("out", "json")
					.queryString("apiId", apiId)
					.asObject(Build[].class);
			responseVersion = uniResponse.getBody();
		} catch (UnirestException e) {
			log.warning("Not able to get response from WS heroku-management/latest-builds method");
		}
		return Optional.ofNullable(responseVersion).isPresent() ? responseVersion[0] : null;
	}

	public String constructVersionString(Build build) {
		String version = build.getSourceBlob().getVersion();
		SimpleDateFormat versionDateFormat = new SimpleDateFormat("yyyy-dd-MM");
		String versionDateString = versionDateFormat.format(build.getCreatedAt());
		String versionText = MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.version"),
				versionDateString, version);
		return versionText;
	}

	public Pair<Boolean, String> checkELibraryDbConnectivityAndGetHealthStatus() {
		StringBuilder status = new StringBuilder();
		Boolean connectivity = false;
		String dbUrl = SystemProperties.getInstance().getConfigProperties().getProperty("db.url")
				+ "?user=" + SystemProperties.getInstance().getConfigProperties().getProperty("db.username")
				+ "&password=" + SystemProperties.getInstance().getConfigProperties().getProperty("db.password");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(dbUrl);

			connectivity = connection.getMetaData().getDatabaseProductName() != null;
			status.append(MessageFormat.format(
					SystemProperties.getInstance().getResourceBundle().getString("common.dbConnectivitySuccessStatus"),
					connection.getMetaData().getDatabaseProductName(),
					connection.getMetaData().getDatabaseProductVersion()));
		} catch (Exception e) {
			status.append(e.getMessage());
		}
		return new Pair<>(connectivity, status.toString());
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

	public void registerWatchServiceListener(TabTemplatePageEvent tabTemplatePageEvent,
	                                         Consumer<TabTemplatePageEvent> c) {
		watchServiceListener = new WatchServiceListener();
		watchServiceListener.initialize(tabTemplatePageEvent, c);
	}

	public void unregisterWatchServiceListener() {
		if (watchServiceListener != null)
			watchServiceListener.destroy();
	}

	public boolean isWatchServiceRunning() {
		return watchServiceListener != null && watchServiceListener.isRunning();
	}

	public void registerNetworkServiceListener(Actions actions) {
		networkServiceListener = new NetworkServiceListener();
		networkServiceListener.initialize(actions);
	}

	public void unregisterNetworkServiceListener() {
		if (networkServiceListener != null)
			networkServiceListener.destroy();
	}

	public boolean isNetworkServiceRunning() {
		return networkServiceListener != null && networkServiceListener.isRunning();
	}

	public void registerTimerServiceListenerSingleton(JLabel label) {
		if (timerServiceListener == null)
			timerServiceListener = new TimerServiceListener();
		timerServiceListener.initialize(label);
	}

	public void unregisterTimerServiceListenerSingleton() {
		if (timerServiceListener != null)
			timerServiceListener.destroy();
	}
}

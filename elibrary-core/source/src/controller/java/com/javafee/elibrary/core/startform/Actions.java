package com.javafee.elibrary.core.startform;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IRegistrationForm;
import com.javafee.elibrary.core.emailform.MailSenderEvent;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedLogInException;
import com.javafee.elibrary.core.exception.RefusedRegistrationException;
import com.javafee.elibrary.core.startform.RegistrationEvent.RegistrationFailureCause;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.common.message.Message;
import com.javafee.elibrary.hibernate.dto.common.message.Recipient;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.LibraryData;
import com.javafee.elibrary.hibernate.dto.library.Worker;

public class Actions implements IRegistrationForm {
	private StartForm startForm = new StartForm();

	private LogInEvent logInEvent;

	public void control() {
		if (MainSplashScreen.isNull())
			MainSplashScreen.getInstance(Constants.MAIN_SPLASH_SCREEN_IMAGE, startForm.getFrame(),
					Constants.MAIN_SPLASH_SCREEN_DURATION);
		else
			startForm.getFrame().setVisible(true);

		startForm.getLogInPanel().getBtnForgotPassword().addActionListener(e -> onClickBtnForgotPassword());
		startForm.getBtnLogIn().addActionListener(e -> onClickBtnLogIn());
		startForm.getBtnRegistrationMode().addActionListener(e -> onClickBtnRegistrationMode());
		startForm.getNavigationPanel().getBtnBack().addActionListener(e -> onClickBtnBack());
		startForm.getRegistrationPanel().getBtnRegisterNow().addActionListener(e -> onClickBtnRegisterNow());
	}

	@Override
	public void reloadRegistrationPanel() {
	}

	private void onClickBtnForgotPassword() {
		if (validateForgotPassword()) {
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.continueQuestion"),
					"") == JOptionPane.YES_OPTION) {
				UserData userData = (UserData) Params.getInstance().get("USER_DATA");
				String generatedPassword = com.javafee.elibrary.core.common.Common.generatePassword();
				userData.setPassword(com.javafee.elibrary.core.common.Common.createMd5(generatedPassword));

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(UserData.class.getName(), userData);
				HibernateUtil.commitTransaction();

				sendMailWithPassword(generatedPassword, userData);
				Params.getInstance().remove("USER_DATA");

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.passwordRecoverySuccess"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.passwordRecoverySuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void onClickBtnLogIn() {
		if (validateLogIn()) {
			try {
				logInEvent = LogInEvent.getInstance(startForm.getLogInPanel().getTextFieldLogin().getText(),
						String.valueOf(startForm.getLogInPanel().getPasswordField().getPassword()));
			} catch (RefusedLogInException e) {
				StringBuilder errorBuilder = new StringBuilder();

				if (Params.getInstance().get("NO_USER") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError3"));
					Params.getInstance().remove("NO_USER");
				}
				if (Params.getInstance().get("BAD_PASSWORD") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError2"));
					Params.getInstance().remove("BAD_PASSWORD");
				}
				if (Params.getInstance().get("NOT_REGISTERED") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError4"));
					Params.getInstance().remove("NOT_REGISTERED");
				}
				if (Params.getInstance().get("NOT_HIRED") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError9"));
					Params.getInstance().remove("NOT_HIRED");
				}

				LogGuiException.logError(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInErrorTitle"),
						errorBuilder.toString(), e);

				clearLogInFailsParams();
			}

			if (logInEvent != null) {
				JOptionPane.showMessageDialog(startForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInSuccess1"),
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInSuccess1Title"),
						JOptionPane.INFORMATION_MESSAGE);
				openTabbedForm();
			}
		}
	}

	private void clearLogInFailsParams() {
		Params.getInstance().remove("NO_USER");
		Params.getInstance().remove("BAD_PASSWORD");
		Params.getInstance().remove("NOT_REGISTERED");
		Params.getInstance().remove("NOT_HIRED");
	}

	@Override
	public void onClickBtnRegisterNow() {
		reloadRegistrationPanel();
		if (validateRegistration()) {
			switchPerspectiveToRegistrationOrLogIn(false);
			try {
				Character sex = startForm.getRegistrationPanel().getGroupRadioButtonSex().getSelection() != null
						? startForm.getRegistrationPanel().getGroupRadioButtonSex().getSelection().getActionCommand()
						.charAt(0)
						: null;
				Date birthDate = startForm.getRegistrationPanel().getDateChooserBirthDate().getDate() != null
						? new SimpleDateFormat("dd-MM-yyyy").parse(new SimpleDateFormat("dd-MM-yyyy")
						.format(startForm.getRegistrationPanel().getDateChooserBirthDate().getDate()))
						: null;
				RegistrationEvent.forceClearRegistrationEvenet();
				if (birthDate == null || birthDate.before(new Date())) {
					RegistrationEvent.getInstance(
							startForm.getRegistrationPanel().getTextFieldPeselNumber().getText(),
							startForm.getRegistrationPanel().getTextFieldDocumentNumber().getText(),
							startForm.getRegistrationPanel().getTextFieldName().getText(),
							startForm.getRegistrationPanel().getTextFieldSurname().getText(),
							startForm.getRegistrationPanel().getTextFieldAddress().getText(),
							Optional.ofNullable(startForm.getRegistrationPanel().getComboBoxCity().getSelectedItem()).isPresent()
									? ((City) startForm.getRegistrationPanel().getComboBoxCity().getSelectedItem()).getName() : null, sex, birthDate,
							startForm.getRegistrationPanel().getTextFieldLogin().getText(),
							startForm.getRegistrationPanel().getTextFieldEMail().getText(),
							String.valueOf(startForm.getRegistrationPanel().getPasswordField().getPassword()),
							Role.WORKER_LIBRARIAN);
				} else {
					Params.getInstance().add("INCORRECT_BIRTH_DATE", RegistrationFailureCause.INCORRECT_BIRTH_DATE);
					throw new RefusedRegistrationException("Cannot register to the system");
				}
			} catch (RefusedRegistrationException e) {
				StringBuilder errorBuilder = new StringBuilder();

				if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError5"));
					Params.getInstance().remove("ALREADY_REGISTERED");
				}
				if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError6"));
				}
				if (Params.getInstance().get("WEAK_PASSWORD") != null) {
					errorBuilder.append(MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
									.getString("startForm.registrationError7"),
							SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue(),
							SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()));
				}
				if (Params.getInstance().get("INCORRECT_BIRTH_DATE") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError9"));
				}

				LogGuiException.logError(SystemProperties.getInstance().getResourceBundle()
						.getString("startForm.registrationErrorTitle"), errorBuilder.toString(), e);
				clearRegistrationFailsInParams();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (RegistrationEvent.getRegistrationEvent() != null)
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.registrationSuccess2Title"),
						JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearRegistrationFailsInParams() {
		Params.getInstance().remove("ALREADY_REGISTERED");
		Params.getInstance().remove("PARAMETERS_ERROR");
		Params.getInstance().remove("WEAK_PASSWORD");
		Params.getInstance().remove("INCORRECT_BIRTH_DATE");

	}

	private void onClickBtnRegistrationMode() {
		switchPerspectiveToRegistrationOrLogIn(true);
		reloadRegistrationPanel();
	}

	private void onClickBtnBack() {
		switchPerspectiveToRegistrationOrLogIn(false);
	}

	private void openTabbedForm() {
		startForm.getFrame().setVisible(false);
		com.javafee.elibrary.core.tabbedform.Actions actions = new com.javafee.elibrary.core.tabbedform.Actions();
		actions.control();
	}

	private void switchPerspectiveToRegistrationOrLogIn(boolean registration) {
		startForm.getLogInPanel().setEnabled(!registration);
		startForm.getLogInPanel().setVisible(!registration);
		startForm.getBtnLogIn().setEnabled(!registration);
		startForm.getBtnLogIn().setVisible(!registration);
		startForm.getBtnRegistrationMode().setEnabled(!registration);
		startForm.getBtnRegistrationMode().setVisible(!registration);
		startForm.getNavigationPanel().setEnabled(registration);
		startForm.getNavigationPanel().setVisible(registration);
		startForm.getRegistrationPanel().setEnabled(registration);
		startForm.getRegistrationPanel().setVisible(registration);
		startForm.getFrame().getRootPane().setDefaultButton(registration
				? startForm.getRegistrationPanel().getBtnRegisterNow() : startForm.getBtnLogIn());
		startForm.getFrame().pack();
	}

	private void sendMailWithPassword(String generatedPassword, UserData recipient) {
		List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipientArg = new ArrayList<SimpleEntry<javax.mail.Message.RecipientType, UserData>>();
		recipientArg.add(new SimpleEntry<javax.mail.Message.RecipientType, UserData>(javax.mail.Message.RecipientType.TO, recipient));
		String subject = SystemProperties.getInstance().getResourceBundle()
				.getString("startForm.forgotPasswordEmailSubject");
		String content = MessageFormat.format(
				SystemProperties.getInstance().getResourceBundle().getString("startForm.forgotPasswordEmailContent"),
				generatedPassword);

		if (new MailSenderEvent().control(recipientArg, subject, content))
			createEmail(recipientArg, subject, content);
	}

	private void createEmail(List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients, String subject,
	                         String text) {
		try {
			MessageType messageType = Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE).get();

			HibernateUtil.beginTransaction();
			Message message = new Message();

			message.setSender(null);
			message.setMessageType(messageType);
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getValue());
				newRecipient.setMessage(message);
				message.getRecipient().add(newRecipient);
			});
			message.setTitle(subject);
			message.setContent(text);
			message.setSendDate(
					Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT.format(new Date())));

			HibernateUtil.getSession().save(message);
			HibernateUtil.commitTransaction();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private boolean validateLogIn() {
		boolean result = false;
		if (startForm.getLogInPanel().getTextFieldLogin().getText().isEmpty()
				|| startForm.getLogInPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1"),
					SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1Title"),
					JOptionPane.ERROR_MESSAGE);
		else
			result = true;

		return result;
	}

	private boolean validateForgotPassword() {
		boolean result = false;
		if (!com.javafee.elibrary.core.common.Common.checkInternetConnectivity() ||
				!Strings.isEmpty(com.javafee.elibrary.core.common.Common.checkEmailServerConnectivity()))
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPasswordError4"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPassword4Title"),
					JOptionPane.ERROR_MESSAGE);
		else if (startForm.getLogInPanel().getTextFieldLogin().getText().isEmpty())
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPasswordError1"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPassword1Title"),
					JOptionPane.ERROR_MESSAGE);
		else {
			Client client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
					.setParameter("login", startForm.getLogInPanel().getTextFieldLogin().getText()).uniqueResult();
			Worker worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
					.setParameter("login", startForm.getLogInPanel().getTextFieldLogin().getText()).uniqueResult();

			if (client == null && worker == null)
				JOptionPane.showMessageDialog(startForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.validateForgotPasswordError5"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.validateForgotPassword5Title"),
						JOptionPane.ERROR_MESSAGE);
			else {
				if (client != null)
					result = validateAdministratorPrivilegesAndEmail(client);
				else if (worker != null)
					result = validateAdministratorPrivilegesAndEmail(worker);
			}
		}
		return result;
	}

	private boolean validateAdministratorPrivilegesAndEmail(UserData userData) {
		boolean result = false;
		if (com.javafee.elibrary.core.common.Common.isAdmin(userData))
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPasswordError2"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPassword1Title"),
					JOptionPane.ERROR_MESSAGE);
		else if (userData.getEMail() != null) {
			Params.getInstance().add("USER_DATA", userData);
			result = true;
		} else
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPasswordError3"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateForgotPassword1Title"),
					JOptionPane.ERROR_MESSAGE);
		return result;
	}

	@Override
	public boolean validateRegistration() {
		Optional<LibraryData> libraryData =
				Common.findLibraryDataById(com.javafee.elibrary.hibernate.dao.common.Constants.DATA_BASE_LIBRARY_DATA_ID);
		boolean result = false,
				isLoginAndPasswordNotProvided = startForm.getRegistrationPanel().getTextFieldLogin().getText().isEmpty()
						|| startForm.getRegistrationPanel().getPasswordField().getPassword().length == 0,
				isSystemInitialized = libraryData.isPresent();
		if (isLoginAndPasswordNotProvided || !isSystemInitialized) {
			StringBuilder errorBuilder = new StringBuilder();
			if (isLoginAndPasswordNotProvided)
				errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
						.getString("startForm.validateRegistrationError8"));
			if (!isSystemInitialized)
				errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
						.getString("startForm.validateRegistrationError9"));
			JOptionPane.showMessageDialog(startForm.getFrame(),
					errorBuilder.toString(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("errorDialog.title"),
					JOptionPane.ERROR_MESSAGE);
		} else
			result = true;
		switchPerspectiveToRegistrationOrLogIn(true);
		return result;
	}
}

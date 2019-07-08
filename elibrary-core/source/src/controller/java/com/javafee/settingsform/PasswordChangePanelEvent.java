package com.javafee.settingsform;

import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.common.Common;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class PasswordChangePanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static PasswordChangePanelEvent passwordChangePanelEvent = null;

	private PasswordChangePanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static PasswordChangePanelEvent getInstance(SettingsForm settingsForm) {
		if (passwordChangePanelEvent == null)
			passwordChangePanelEvent = new PasswordChangePanelEvent(settingsForm);

		return passwordChangePanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getPasswordChangePanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
	}

	public void initializeForm() {
	}

	private void onClickBtnAccept() {
		if (validatePasswordChange()) {
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.continueQuestion"),
					"") == JOptionPane.YES_OPTION)
				persistPasswordChange();
		}
	}

	private void persistPasswordChange() {
		String newPassword = String.valueOf(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew());
		UserData userData = LogInEvent.getUserData();
		userData.setPassword(Common.createMd5(newPassword));

		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(UserData.class.getName(), userData);
		HibernateUtil.commitTransaction();

		Utils.displayOptionPane(
				com.javafee.common.SystemProperties.getInstance().getResourceBundle()
						.getString("passwordChangePanelEvent.passwordChangeSuccess"),
				com.javafee.common.SystemProperties.getInstance().getResourceBundle()
						.getString("passwordChangePanelEvent.passwordChangeSuccessTitle"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void clearPasswordChangeFailsParams() {
		Params.getInstance().remove("WEAK_PASSWORD");
	}

	private boolean validateNewAndConfirmedPasswords(char[] newPassword, char[] confirmedPassword) {
		return newPassword != null && confirmedPassword != null && String.valueOf(newPassword).equals(String.valueOf(confirmedPassword));
	}

	private boolean validatePasswordChange() {
		boolean result = true;
		boolean passwordFiledOldEmpty = Strings.isEmpty(String.valueOf(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldOld().getPassword())),
				passwordFiledNewEmpty = Strings.isEmpty(String.valueOf(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldNew().getPassword())),
				passwordFieldConfirmNewEmpty = Strings.isEmpty(String.valueOf(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().getPassword())),
				validateNewAndConfirmedPasswords = !passwordFiledNewEmpty && !passwordFieldConfirmNewEmpty
						&& validateNewAndConfirmedPasswords(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldNew().getPassword(),
						settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().getPassword()),
				newPasswordStrength = validateNewAndConfirmedPasswords
						&& Common.checkPasswordStrength(String.valueOf(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().getPassword()));
		StringBuilder errorBuilder = new StringBuilder();

		if (passwordFiledOldEmpty) {
			errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
					.getString("passwordChangePanelEvent.passwordChangeError1"));
			result = false;
		}
		if (passwordFiledNewEmpty) {
			errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
					.getString("passwordChangePanelEvent.passwordChangeError2"));
			result = false;
		}
		if (passwordFieldConfirmNewEmpty) {
			errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
					.getString("passwordChangePanelEvent.passwordChangeError3"));
			result = false;
		}
		if (!validateNewAndConfirmedPasswords) {
			errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
					.getString("passwordChangePanelEvent.passwordChangeError4"));
			result = false;
		}
		if (!newPasswordStrength) {
			errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
					.getString("startForm.registrationError7")); // WEAK_PASSWORD resource
			result = false;
		}

		if (result)
			result = true;
		else
			LogGuiException.logError(SystemProperties.getInstance().getResourceBundle()
					.getString("passwordChangePanelEvent.passwordChangeErrorTitle"), errorBuilder.toString());
		clearPasswordChangeFailsParams();

		return result;
	}
}

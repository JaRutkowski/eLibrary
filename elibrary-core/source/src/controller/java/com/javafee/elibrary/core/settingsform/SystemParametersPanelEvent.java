package com.javafee.elibrary.core.settingsform;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.mail.MailSender;
import com.javafee.elibrary.core.unicomponent.jspinner.DoubleJSpinner;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.common.SystemParameter;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class SystemParametersPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static SystemParametersPanelEvent systemParametersPanelEvent = null;

	private SystemParametersPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static SystemParametersPanelEvent getInstance(SettingsForm settingsForm) {
		if (systemParametersPanelEvent == null)
			systemParametersPanelEvent = new SystemParametersPanelEvent(settingsForm);

		return systemParametersPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getSystemParametersPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getBtnRestoreDefaultValues().addActionListener(e -> onClickBtnRestoreDefaultValues());
	}

	@Override
	public void initializeForm() {
		reloadParametersValues();
		setEnableControls();
	}

	private void reloadParametersValues() {
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationPenaltyValue().setValue(
				Double.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_PENALTY_VALUE).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationEmailAddress().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS).getValue());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getPasswordField().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD).getValue());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationGeneratedPasswordLength().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationTemplatesDirectoryName().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue());
	}

	private void onClickBtnAccept() {
		SystemParameter systemParameter;

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_PENALTY_VALUE);
		systemParameter.setValue(((DoubleJSpinner) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationPenaltyValue()).getDouble().toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS);
		systemParameter.setValue(settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationEmailAddress().getText());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD);
		systemParameter.setValue(String.valueOf(settingsForm.getSettingsPanel().getSystemParametersPanel().getPasswordField().getPassword()));
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationGeneratedPasswordLength().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME);
		systemParameter.setValue(settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationTemplatesDirectoryName().getText());
		updateSystemParameter(systemParameter);

		if (settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxValidateEmailServerConnection().isSelected())
			validateEmailServerConnection();

		Utils.displayOptionPane(
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.parametersModificationSuccess"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.parametersModificationSuccessSuccessTitle"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void onClickBtnRestoreDefaultValues() {
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_PENALTY_VALUE);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_EMAIL_ADDRESS);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_EMAIL_PASSWORD);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME);
		reloadParametersValues();

		Utils.displayOptionPane(
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.restoringDefaultsSuccess"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.restoringDefaultsSuccessTitle"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void updateSystemParameter(SystemParameter systemParameter) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(systemParameter);
		HibernateUtil.commitTransaction();
	}

	private void restoreAndUpdateDefaultSystemParameterValue(String systemParameterName) {
		SystemParameter systemParameter = SystemProperties.getInstance().getSystemParameters().get(systemParameterName);
		systemParameter.setValue(systemParameter.getDefaultValue());
		SystemProperties.getInstance().getSystemParameters().put(systemParameterName, systemParameter);
		updateSystemParameter(systemParameter);
	}

	private void setEnableControls() {
		setNetworkControls(Common.checkInternetConnectivity());
	}

	private void setNetworkControls(boolean enable) {
		settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxValidateEmailServerConnection().setEnabled(enable);
	}

	private void validateEmailServerConnection() {
		MailSender mailSender = new MailSender();
		try {
			mailSender.validateConnection();
		} catch (MessagingException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("errorDialog.title"), e);
			log.severe(e.getMessage());
		}
	}
}
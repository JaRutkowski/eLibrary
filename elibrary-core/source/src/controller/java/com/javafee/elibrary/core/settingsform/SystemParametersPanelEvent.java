package com.javafee.elibrary.core.settingsform;

import java.text.MessageFormat;

import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
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
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationReservationLimit().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_RESERVATIONS_LIMIT).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationEmailAddress().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS).getValue());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getPasswordField().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD).getValue());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationGeneratedPasswordLength().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationTemplatesDirectoryName().setText(
				SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue());
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationMinPasswordLength().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationMaxPasswordLength().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationComboBoxDataPackageSize().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationNumberOfAttemptsLimit().setValue(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT).getValue()));
		settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxWrongAttemptsAccountBlocking().setSelected(
				Boolean.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY).getValue()));
	}

	private void onClickBtnAccept() {
		SystemParameter systemParameter;

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_PENALTY_VALUE);
		systemParameter.setValue(((DoubleJSpinner) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationPenaltyValue()).getDouble().toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_RESERVATIONS_LIMIT);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationReservationLimit().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS);
		String currentEmailAddress = systemParameter.getValue();
		systemParameter.setValue(settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationEmailAddress().getText());

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD);
		String currentEmailPassword = systemParameter.getValue();
		systemParameter.setValue(String.valueOf(settingsForm.getSettingsPanel().getSystemParametersPanel().getPasswordField().getPassword()));

		String validationError = validateEmailServerConnection();
		if ((settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxValidateEmailServerConnection().isSelected()
				&& !Strings.isEmpty(validationError)
				&& Utils.displayConfirmDialog(
				MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.noEmailServerConnectionConfirmDialog"), validationError),
				SystemProperties.getInstance().getResourceBundle().getString("errorDialog.title")) == JOptionPane.YES_OPTION)
				|| !settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxValidateEmailServerConnection().isSelected()
				|| Strings.isEmpty(validationError)) {
			systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS);
			updateSystemParameter(systemParameter);

			systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD);
			updateSystemParameter(systemParameter);
		} else {
			systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS);
			systemParameter.setValue(currentEmailAddress);
			settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationEmailAddress().setText(currentEmailAddress);

			systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_PASSWORD);
			systemParameter.setValue(currentEmailPassword);
			settingsForm.getSettingsPanel().getSystemParametersPanel().getPasswordField().setText(currentEmailPassword);
		}

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationGeneratedPasswordLength().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationMinPasswordLength().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationMaxPasswordLength().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME);
		systemParameter.setValue(settingsForm.getSettingsPanel().getSystemParametersPanel().getTextFieldApplicationTemplatesDirectoryName().getText());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationComboBoxDataPackageSize().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT);
		systemParameter.setValue(Integer.valueOf((Integer) settingsForm.getSettingsPanel().getSystemParametersPanel().getSpinnerApplicationNumberOfAttemptsLimit().getValue()).toString());
		updateSystemParameter(systemParameter);

		systemParameter = SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY);
		systemParameter.setValue(Boolean.valueOf(settingsForm.getSettingsPanel().getSystemParametersPanel().getChckbxWrongAttemptsAccountBlocking().isSelected()).toString());
		updateSystemParameter(systemParameter);

		Utils.displayOptionPane(
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.parametersModificationSuccess"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("systemParametersPanelEvent.parametersModificationSuccessSuccessTitle"),
				JOptionPane.INFORMATION_MESSAGE);

		settingsForm.getSettingsPanel().getSystemParametersPanel().reloadMinMaxDefaultInSpinners();
	}

	private void onClickBtnRestoreDefaultValues() {
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_PENALTY_VALUE);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_RESERVATIONS_LIMIT);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_EMAIL_ADDRESS);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_EMAIL_PASSWORD);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_MIN_PASSWORD_LENGTH);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_MAX_PASSWORD_LENGTH);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT);
		restoreAndUpdateDefaultSystemParameterValue(Constants.APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY);
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

	private String validateEmailServerConnection() {
		return Common.checkEmailServerConnectivity();
	}
}

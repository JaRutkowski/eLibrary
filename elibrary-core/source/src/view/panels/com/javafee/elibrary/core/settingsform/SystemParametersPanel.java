package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Button_Type;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButtonFactory;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jpasswordfield.CustomJPasswordField;
import com.javafee.elibrary.core.unicomponent.jspinner.CustomJSpinner;
import com.javafee.elibrary.core.unicomponent.jspinner.DoubleJSpinner;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;

import lombok.Getter;

@Getter
public class SystemParametersPanel extends BasePanel {
	private JSpinner spinnerApplicationPenaltyValue;
	private JSpinner spinnerApplicationReservationLimit;
	private JTextField textFieldApplicationEmailAddress;
	private JPasswordField passwordField;
	private JCheckBox chckbxValidateEmailServerConnection;
	private JSpinner spinnerApplicationGeneratedPasswordLength;
	private JTextField textFieldApplicationTemplatesDirectoryName;
	private JButton btnAccept;
	private JButton btnRestoreDefaultValues;
	private JSpinner spinnerApplicationMinPasswordLength;
	private JSpinner spinnerApplicationMaxPasswordLength;
	private JLabel lblApplicationComboBoxDataPackageSize;
	private JSpinner spinnerApplicationComboBoxDataPackageSize;
	private JLabel lblApplicationNumberOfAttempts;
	private JSpinner spinnerApplicationNumberOfAttemptsLimit;
	private JCheckBox chckbxWrongAttemptsAccountBlocking;

	public SystemParametersPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblApplicationPenaltyValue = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationPenaltyValue"));
		GridBagConstraints gbc_lblApplicationPenaltyValue = new GridBagConstraints();
		gbc_lblApplicationPenaltyValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationPenaltyValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationPenaltyValue.gridx = 0;
		gbc_lblApplicationPenaltyValue.gridy = 0;
		add(lblApplicationPenaltyValue, gbc_lblApplicationPenaltyValue);

		spinnerApplicationPenaltyValue = new DoubleJSpinner(
				Constants.SPINNER_INITIAL_VALUE_PENALTY,
				Constants.SPINNER_MINIMUM_VALUE_PENALTY,
				Constants.SPINNER_MAXIMUM_VALUE_PENALTY,
				Constants.DOUBLE_SPINNER_STEP_VALUE_PENALTY);
		GridBagConstraints gbc_spinnerApplicationPenaltyValue = new GridBagConstraints();
		gbc_spinnerApplicationPenaltyValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationPenaltyValue.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationPenaltyValue.gridx = 1;
		gbc_spinnerApplicationPenaltyValue.gridy = 0;
		add(spinnerApplicationPenaltyValue, gbc_spinnerApplicationPenaltyValue);

		JLabel lblApplicationReservationLimit = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationReservationLimit"));
		GridBagConstraints gbc_lblApplicationReservationLimit = new GridBagConstraints();
		gbc_lblApplicationReservationLimit.anchor = GridBagConstraints.WEST;
		gbc_lblApplicationReservationLimit.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationReservationLimit.gridx = 0;
		gbc_lblApplicationReservationLimit.gridy = 1;
		add(lblApplicationReservationLimit, gbc_lblApplicationReservationLimit);

		spinnerApplicationReservationLimit = new CustomJSpinner(
				Constants.SPINNER_MINIMUM_VALUE_RESERVATION_LIMIT,
				Constants.SPINNER_MINIMUM_VALUE_RESERVATION_LIMIT,
				Constants.SPINNER_MAXIMUM_VALUE_RESERVATION_LIMIT);
		GridBagConstraints gbc_spinnerApplicationReservationLimit = new GridBagConstraints();
		gbc_spinnerApplicationReservationLimit.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationReservationLimit.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationReservationLimit.gridx = 1;
		gbc_spinnerApplicationReservationLimit.gridy = 1;
		add(spinnerApplicationReservationLimit, gbc_spinnerApplicationReservationLimit);

		JLabel lblApplicationEmailAddress = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationEmailAddress"));
		GridBagConstraints gbc_lblApplicationEmailAddress = new GridBagConstraints();
		gbc_lblApplicationEmailAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationEmailAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationEmailAddress.gridx = 0;
		gbc_lblApplicationEmailAddress.gridy = 2;
		add(lblApplicationEmailAddress, gbc_lblApplicationEmailAddress);

		textFieldApplicationEmailAddress = new CustomJTextField();
		GridBagConstraints gbc_textFieldApplicationEmailAddress = new GridBagConstraints();
		gbc_textFieldApplicationEmailAddress.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldApplicationEmailAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApplicationEmailAddress.gridx = 1;
		gbc_textFieldApplicationEmailAddress.gridy = 2;
		add(textFieldApplicationEmailAddress, gbc_textFieldApplicationEmailAddress);
		textFieldApplicationEmailAddress.setColumns(10);

		JLabel lblApplicationEmailPassword = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationEmailPassword"));
		GridBagConstraints gbc_lblApplicationEmailPassword = new GridBagConstraints();
		gbc_lblApplicationEmailPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationEmailPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationEmailPassword.gridx = 0;
		gbc_lblApplicationEmailPassword.gridy = 3;
		add(lblApplicationEmailPassword, gbc_lblApplicationEmailPassword);

		passwordField = new CustomJPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		add(passwordField, gbc_passwordField);

		chckbxValidateEmailServerConnection = new CustomJCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.chckbxValidateEmailServerConnection"));
		GridBagConstraints gbc_chckbxValidateEmailServerConnection = new GridBagConstraints();
		gbc_chckbxValidateEmailServerConnection.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxValidateEmailServerConnection.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxValidateEmailServerConnection.gridx = 1;
		gbc_chckbxValidateEmailServerConnection.gridy = 4;
		add(chckbxValidateEmailServerConnection, gbc_chckbxValidateEmailServerConnection);

		JLabel lblApplicationGeneratedPasswordLength = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationGeneratedPasswordLength"));
		GridBagConstraints gbc_lblApplicationGeneratedPasswordLength = new GridBagConstraints();
		gbc_lblApplicationGeneratedPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationGeneratedPasswordLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationGeneratedPasswordLength.gridx = 0;
		gbc_lblApplicationGeneratedPasswordLength.gridy = 5;
		add(lblApplicationGeneratedPasswordLength, gbc_lblApplicationGeneratedPasswordLength);

		spinnerApplicationGeneratedPasswordLength = new CustomJSpinner(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()));
		GridBagConstraints gbc_spinnerApplicationGeneratedPasswordLength = new GridBagConstraints();
		gbc_spinnerApplicationGeneratedPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationGeneratedPasswordLength.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationGeneratedPasswordLength.gridx = 1;
		gbc_spinnerApplicationGeneratedPasswordLength.gridy = 5;
		add(spinnerApplicationGeneratedPasswordLength, gbc_spinnerApplicationGeneratedPasswordLength);

		JLabel lblApplicationMinPasswordLength = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationMinPasswordLength"));
		GridBagConstraints gbc_lblApplicationMinPasswordLength = new GridBagConstraints();
		gbc_lblApplicationMinPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationMinPasswordLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationMinPasswordLength.gridx = 0;
		gbc_lblApplicationMinPasswordLength.gridy = 6;
		add(lblApplicationMinPasswordLength, gbc_lblApplicationMinPasswordLength);

		spinnerApplicationMinPasswordLength = new CustomJSpinner(
				Constants.SPINNER_MINIMUM_VALUE_PASSWORD_LENGTH,
				Constants.SPINNER_MINIMUM_VALUE_PASSWORD_LENGTH,
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()));
		GridBagConstraints gbc_spinnerApplicationMinPasswordLength = new GridBagConstraints();
		gbc_spinnerApplicationMinPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationMinPasswordLength.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationMinPasswordLength.gridx = 1;
		gbc_spinnerApplicationMinPasswordLength.gridy = 6;
		add(spinnerApplicationMinPasswordLength, gbc_spinnerApplicationMinPasswordLength);

		JLabel lblApplicationMaxPasswordLength = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationMaxPasswordLength"));
		GridBagConstraints gbc_lblApplicationMaxPasswordLength = new GridBagConstraints();
		gbc_lblApplicationMaxPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationMaxPasswordLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationMaxPasswordLength.gridx = 0;
		gbc_lblApplicationMaxPasswordLength.gridy = 7;
		add(lblApplicationMaxPasswordLength, gbc_lblApplicationMaxPasswordLength);

		spinnerApplicationMaxPasswordLength = new CustomJSpinner(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()),
				Constants.SPINNER_MAXIMUM_VALUE_PASSWORD_LENGTH);
		GridBagConstraints gbc_spinnerApplicationMaxPasswordLength = new GridBagConstraints();
		gbc_spinnerApplicationMaxPasswordLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationMaxPasswordLength.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationMaxPasswordLength.gridx = 1;
		gbc_spinnerApplicationMaxPasswordLength.gridy = 7;
		add(spinnerApplicationMaxPasswordLength, gbc_spinnerApplicationMaxPasswordLength);

		lblApplicationNumberOfAttempts = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationNumberOfAttempts"));
		GridBagConstraints gbc_lblApplicationNumberOfAttempts = new GridBagConstraints();
		gbc_lblApplicationNumberOfAttempts.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationNumberOfAttempts.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationNumberOfAttempts.gridx = 0;
		gbc_lblApplicationNumberOfAttempts.gridy = 8;
		add(lblApplicationNumberOfAttempts, gbc_lblApplicationNumberOfAttempts);

		spinnerApplicationNumberOfAttemptsLimit = new CustomJSpinner(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT).getValue()),
				Constants.SPINNER_MINIMUM_VALUE_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT,
				Constants.SPINNER_MAXIMUM_VALUE_APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT);
		GridBagConstraints gbc_spinnerApplicationNumberOfAttemptsLimit = new GridBagConstraints();
		gbc_spinnerApplicationNumberOfAttemptsLimit.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationNumberOfAttemptsLimit.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationNumberOfAttemptsLimit.gridx = 1;
		gbc_spinnerApplicationNumberOfAttemptsLimit.gridy = 8;
		add(spinnerApplicationNumberOfAttemptsLimit, gbc_spinnerApplicationNumberOfAttemptsLimit);

		chckbxWrongAttemptsAccountBlocking = new CustomJCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.chckbxWrongAttemptsAccountBlocking"));
		GridBagConstraints gbc_chckbxWrongAttemptsAccountBlocking = new GridBagConstraints();
		gbc_chckbxWrongAttemptsAccountBlocking.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxWrongAttemptsAccountBlocking.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxWrongAttemptsAccountBlocking.gridx = 1;
		gbc_chckbxWrongAttemptsAccountBlocking.gridy = 9;
		add(chckbxWrongAttemptsAccountBlocking, gbc_chckbxWrongAttemptsAccountBlocking);

		JLabel lblApplicationTemplatesDirectoryName = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationTemplatesDirectoryName"));
		GridBagConstraints gbc_lblApplicationTemplatesDirectoryName = new GridBagConstraints();
		gbc_lblApplicationTemplatesDirectoryName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationTemplatesDirectoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationTemplatesDirectoryName.gridx = 0;
		gbc_lblApplicationTemplatesDirectoryName.gridy = 10;
		add(lblApplicationTemplatesDirectoryName, gbc_lblApplicationTemplatesDirectoryName);

		textFieldApplicationTemplatesDirectoryName = new CustomJTextField();
		GridBagConstraints gbc_textFieldApplicationTemplatesDirectoryName = new GridBagConstraints();
		gbc_textFieldApplicationTemplatesDirectoryName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldApplicationTemplatesDirectoryName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApplicationTemplatesDirectoryName.gridx = 1;
		gbc_textFieldApplicationTemplatesDirectoryName.gridy = 10;
		add(textFieldApplicationTemplatesDirectoryName, gbc_textFieldApplicationTemplatesDirectoryName);
		textFieldApplicationTemplatesDirectoryName.setColumns(10);

		lblApplicationComboBoxDataPackageSize = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.lblApplicationComboBoxDataPackageSize"));
		GridBagConstraints gbc_lblApplicationComboBoxDataPackageSize = new GridBagConstraints();
		gbc_lblApplicationComboBoxDataPackageSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApplicationComboBoxDataPackageSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblApplicationComboBoxDataPackageSize.gridx = 0;
		gbc_lblApplicationComboBoxDataPackageSize.gridy = 11;
		add(lblApplicationComboBoxDataPackageSize, gbc_lblApplicationComboBoxDataPackageSize);

		spinnerApplicationComboBoxDataPackageSize = new CustomJSpinner(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE).getValue()),
				Constants.SPINNER_MINIMUM_VALUE_COMBO_BOX_DATA_PACKAGE_SIZE,
				Constants.SPINNER_MAXIMUM_VALUE_COMBO_BOX_DATA_PACKAGE_SIZE);
		GridBagConstraints gbc_spinnerApplicationComboBoxDataPackageSize = new GridBagConstraints();
		gbc_spinnerApplicationComboBoxDataPackageSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerApplicationComboBoxDataPackageSize.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerApplicationComboBoxDataPackageSize.gridx = 1;
		gbc_spinnerApplicationComboBoxDataPackageSize.gridy = 11;
		add(spinnerApplicationComboBoxDataPackageSize, gbc_spinnerApplicationComboBoxDataPackageSize);

		btnAccept = CustomJButtonFactory.createCustomJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.insets = new Insets(0, 0, 5, 0);
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 12;
		add(btnAccept, gbc_btnAccept);

		btnRestoreDefaultValues = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSendAgain-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("systemParametersPanel.btnRestoreDefaultValues"));
		GridBagConstraints gbc_btnRestoreDefaultValues = new GridBagConstraints();
		gbc_btnRestoreDefaultValues.gridx = 1;
		gbc_btnRestoreDefaultValues.gridy = 13;
		add(btnRestoreDefaultValues, gbc_btnRestoreDefaultValues);
	}

	public void reloadMinMaxDefaultInSpinners() {
		spinnerApplicationGeneratedPasswordLength.setModel(new SpinnerNumberModel(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_GENERATED_PASSWORD_LENGTH).getValue()).intValue(),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()).intValue(),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()).intValue(),
				1));
		spinnerApplicationMinPasswordLength.setModel(new SpinnerNumberModel(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()).intValue(),
				Constants.SPINNER_MINIMUM_VALUE_PASSWORD_LENGTH.intValue(),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MIN_PASSWORD_LENGTH).getValue()).intValue(),
				1));
		spinnerApplicationMaxPasswordLength.setModel(new SpinnerNumberModel(
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()).intValue(),
				Integer.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_MAX_PASSWORD_LENGTH).getValue()).intValue(),
				Constants.SPINNER_MAXIMUM_VALUE_PASSWORD_LENGTH.intValue(),
				1));
	}
}

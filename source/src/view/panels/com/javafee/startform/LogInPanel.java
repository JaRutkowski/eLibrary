package com.javafee.startform;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class LogInPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblLogin;
	private JLabel lblPassword;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;

	public LogInPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("logInPanel.logInPanelBorderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 124, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblLogin = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("logInPanel.lblLogin"));
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		add(lblLogin, gbc_lblLogin);

		textFieldLogin = new JTextField();
		textFieldLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
		gbc_textFieldLogin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 0;
		add(textFieldLogin, gbc_textFieldLogin);
		textFieldLogin.setColumns(10);

		lblPassword = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.lblPassword"));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		add(lblPassword, gbc_lblPassword);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		add(passwordField, gbc_passwordField);
	}

	public JLabel getLblLogin() {
		return lblLogin;
	}

	public void setLblLogin(JLabel lblLogin) {
		this.lblLogin = lblLogin;
	}

	public JLabel getLblPassword() {
		return lblPassword;
	}

	public void setLblPassword(JLabel lblPassword) {
		this.lblPassword = lblPassword;
	}

	public JTextField getTextFieldLogin() {
		return textFieldLogin;
	}

	public void setTextFieldLogin(JTextField textFieldLogin) {
		this.textFieldLogin = textFieldLogin;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
}

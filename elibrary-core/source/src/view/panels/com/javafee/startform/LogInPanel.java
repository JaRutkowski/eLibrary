package com.javafee.startform;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LogInPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblLogin;
	private JLabel lblPassword;
	@Getter
	private JTextField textFieldLogin;
	@Getter
	private JPasswordField passwordField;
	@Getter
	private JButton btnForgotPassword;

	public LogInPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.logInPanelBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		add(lblPassword, gbc_lblPassword);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		add(passwordField, gbc_passwordField);

		btnForgotPassword = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.btnForgotPassword"));
		btnForgotPassword.setContentAreaFilled(false);
		btnForgotPassword.setOpaque(false);
		btnForgotPassword.setBorderPainted(false);
		btnForgotPassword.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnForgotPassword.setBorder(null);
		GridBagConstraints gbc_btnForgotPassword = new GridBagConstraints();
		gbc_btnForgotPassword.anchor = GridBagConstraints.WEST;
		gbc_btnForgotPassword.gridx = 1;
		gbc_btnForgotPassword.gridy = 2;
		add(btnForgotPassword, gbc_btnForgotPassword);
	}
}

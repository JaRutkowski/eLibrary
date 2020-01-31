package com.javafee.elibrary.core.startform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jpasswordfield.CustomJPasswordField;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;

import lombok.Getter;

public class LogInPanel extends BasePanel {
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
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.logInPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblLogin = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("logInPanel.lblLogin"));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		add(lblLogin, gbc_lblLogin);

		textFieldLogin = new CustomJTextField();
		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
		gbc_textFieldLogin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 0;
		add(textFieldLogin, gbc_textFieldLogin);
		textFieldLogin.setColumns(10);

		lblPassword = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.lblPassword"));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		add(lblPassword, gbc_lblPassword);

		passwordField = new CustomJPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		add(passwordField, gbc_passwordField);

		btnForgotPassword = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("logInPanel.btnForgotPassword"));
		btnForgotPassword.setContentAreaFilled(false);
		btnForgotPassword.setOpaque(false);
		btnForgotPassword.setBorderPainted(false);
		btnForgotPassword.setBorder(null);
		GridBagConstraints gbc_btnForgotPassword = new GridBagConstraints();
		gbc_btnForgotPassword.anchor = GridBagConstraints.WEST;
		gbc_btnForgotPassword.gridx = 1;
		gbc_btnForgotPassword.gridy = 2;
		add(btnForgotPassword, gbc_btnForgotPassword);
	}
}

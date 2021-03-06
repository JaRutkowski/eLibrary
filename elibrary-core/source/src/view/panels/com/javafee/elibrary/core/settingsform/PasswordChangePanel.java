package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants.Button_Type;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButtonFactory;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jpasswordfield.CustomJPasswordField;

import lombok.Getter;

public class PasswordChangePanel extends BasePanel {
	@Getter
	private JPasswordField passwordFieldOld;
	@Getter
	private JPasswordField passwordFieldNew;
	@Getter
	private JPasswordField passwordFieldConfirmNew;
	@Getter
	private JButton btnAccept;

	public PasswordChangePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 200, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblOldPassword = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("passwordChangePanel.lblOldPassword"));
		GridBagConstraints gbc_lblOldPassword = new GridBagConstraints();
		gbc_lblOldPassword.anchor = GridBagConstraints.EAST;
		gbc_lblOldPassword.insets = new Insets(5, 0, 5, 5);
		gbc_lblOldPassword.gridx = 0;
		gbc_lblOldPassword.gridy = 0;
		add(lblOldPassword, gbc_lblOldPassword);

		passwordFieldOld = new CustomJPasswordField();
		GridBagConstraints gbc_oldPasswordField = new GridBagConstraints();
		gbc_oldPasswordField.insets = new Insets(5, 0, 5, 0);
		gbc_oldPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_oldPasswordField.gridx = 1;
		gbc_oldPasswordField.gridy = 0;
		add(passwordFieldOld, gbc_oldPasswordField);

		JLabel lblNewPassword = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("passwordChangePanel.lblNewPassword"));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.anchor = GridBagConstraints.EAST;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 0;
		gbc_lblNewPassword.gridy = 2;
		add(lblNewPassword, gbc_lblNewPassword);

		passwordFieldNew = new CustomJPasswordField();
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.insets = new Insets(0, 0, 5, 0);
		gbc_newPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_newPasswordField.gridx = 1;
		gbc_newPasswordField.gridy = 2;
		add(passwordFieldNew, gbc_newPasswordField);

		JLabel lblConfirmNewPassword = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("passwordChangePanel.lblConfirmNewPassword"));
		GridBagConstraints gbc_lblConfirmNewPassword = new GridBagConstraints();
		gbc_lblConfirmNewPassword.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmNewPassword.insets = new Insets(0, 5, 5, 5);
		gbc_lblConfirmNewPassword.gridx = 0;
		gbc_lblConfirmNewPassword.gridy = 3;
		add(lblConfirmNewPassword, gbc_lblConfirmNewPassword);

		passwordFieldConfirmNew = new CustomJPasswordField();
		GridBagConstraints gbc_confirmNewPasswordField = new GridBagConstraints();
		gbc_confirmNewPasswordField.insets = new Insets(0, 0, 5, 0);
		gbc_confirmNewPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmNewPasswordField.gridx = 1;
		gbc_confirmNewPasswordField.gridy = 3;
		add(passwordFieldConfirmNew, gbc_confirmNewPasswordField);

		btnAccept = CustomJButtonFactory.createCustomJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		add(btnAccept, gbc_btnNewButton);
	}
}

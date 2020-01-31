package com.javafee.elibrary.core.startform;

import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.elibrary.core.unicomponent.jdatechooser.CustomJDateChooser;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jpasswordfield.CustomJPasswordField;
import com.javafee.elibrary.core.unicomponent.jradiobutton.CustomJRadioButton;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.toedter.calendar.JDateChooser;

import lombok.Getter;

public class RegistrationPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTextField textFieldName;
	@Getter
	private JTextField textFieldSurname;
	@Getter
	private JDateChooser dateChooserBirthDate;
	@Getter
	private JTextField textFieldAddress;
	@Getter
	private JTextField textFieldPeselNumber;
	@Getter
	private JTextField textFieldDocumentNumber;
	@Getter
	private JTextField textFieldLogin;
	@Getter
	private JPasswordField passwordField;
	@Getter
	private JButton btnRegisterNow;
	@Getter
	private JComboBox<City> comboBoxCity;
	@Getter
	private ButtonGroup groupRadioButtonSex;
	@Getter
	private JRadioButton radioButtonFemale;
	@Getter
	private JRadioButton radioButtonMale;
	@Getter
	private JTextField textFieldEMail;
	@Getter
	private JLabel lblPassword;
	private JLabel lblEMail;

	public RegistrationPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("registrationPanel.registrationPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 130, 198, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblPesel = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblPesel"));
		GridBagConstraints gbc_lblPesel = new GridBagConstraints();
		gbc_lblPesel.anchor = GridBagConstraints.WEST;
		gbc_lblPesel.insets = new Insets(0, 0, 5, 5);
		gbc_lblPesel.gridx = 0;
		gbc_lblPesel.gridy = 0;
		add(lblPesel, gbc_lblPesel);

		textFieldPeselNumber = new CustomJTextField();
		GridBagConstraints gbc_textFieldPeselNumber = new GridBagConstraints();
		gbc_textFieldPeselNumber.gridwidth = 2;
		gbc_textFieldPeselNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPeselNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPeselNumber.gridx = 1;
		gbc_textFieldPeselNumber.gridy = 0;
		add(textFieldPeselNumber, gbc_textFieldPeselNumber);
		textFieldPeselNumber.setColumns(10);

		JLabel lblDocumentNumber = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblDocumentNumber"));
		GridBagConstraints gbc_lblDocumentNumber = new GridBagConstraints();
		gbc_lblDocumentNumber.anchor = GridBagConstraints.WEST;
		gbc_lblDocumentNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblDocumentNumber.gridx = 0;
		gbc_lblDocumentNumber.gridy = 1;
		add(lblDocumentNumber, gbc_lblDocumentNumber);

		textFieldDocumentNumber = new CustomJTextField();
		GridBagConstraints gbc_textFieldDocumentNumber = new GridBagConstraints();
		gbc_textFieldDocumentNumber.gridwidth = 2;
		gbc_textFieldDocumentNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDocumentNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDocumentNumber.gridx = 1;
		gbc_textFieldDocumentNumber.gridy = 1;
		add(textFieldDocumentNumber, gbc_textFieldDocumentNumber);
		textFieldDocumentNumber.setColumns(10);

		JLabel lblName = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblName"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		add(lblName, gbc_lblName);

		textFieldName = new CustomJTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.gridwidth = 2;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 2;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);

		JLabel lblSurname = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblSurname"));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 0;
		gbc_lblSurname.gridy = 3;
		add(lblSurname, gbc_lblSurname);

		textFieldSurname = new CustomJTextField();
		GridBagConstraints gbc_textFieldSurname = new GridBagConstraints();
		gbc_textFieldSurname.gridwidth = 2;
		gbc_textFieldSurname.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSurname.gridx = 1;
		gbc_textFieldSurname.gridy = 3;
		add(textFieldSurname, gbc_textFieldSurname);
		textFieldSurname.setColumns(10);

		JLabel lblAddress = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblAddress"));
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 4;
		add(lblAddress, gbc_lblAddress);

		textFieldAddress = new CustomJTextField();
		GridBagConstraints gbc_textFieldAddress = new GridBagConstraints();
		gbc_textFieldAddress.gridwidth = 2;
		gbc_textFieldAddress.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAddress.gridx = 1;
		gbc_textFieldAddress.gridy = 4;
		add(textFieldAddress, gbc_textFieldAddress);
		textFieldAddress.setColumns(10);

		JLabel lblCity = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblCity"));
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 5;
		add(lblCity, gbc_lblCity);

		comboBoxCity = new CustomJComboBox<City>();
		GridBagConstraints gbc_comboBoxCity = new GridBagConstraints();
		gbc_comboBoxCity.gridwidth = 2;
		gbc_comboBoxCity.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCity.gridx = 1;
		gbc_comboBoxCity.gridy = 5;
		add(comboBoxCity, gbc_comboBoxCity);

		JLabel lblSex = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblSex"));
		GridBagConstraints gbc_lblSex = new GridBagConstraints();
		gbc_lblSex.insets = new Insets(0, 0, 5, 5);
		gbc_lblSex.anchor = GridBagConstraints.WEST;
		gbc_lblSex.gridx = 0;
		gbc_lblSex.gridy = 6;
		add(lblSex, gbc_lblSex);

		radioButtonFemale = new CustomJRadioButton(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.radioButtonFemale"));
		radioButtonFemale.setBackground(Utils.getApplicationUserDefinedColor());
		radioButtonFemale.setActionCommand(Constants.DATA_BASE_FEMALE_SIGN.toString());
		GridBagConstraints gbc_radioButtonFemale = new GridBagConstraints();
		gbc_radioButtonFemale.anchor = GridBagConstraints.WEST;
		gbc_radioButtonFemale.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonFemale.gridx = 1;
		gbc_radioButtonFemale.gridy = 6;
		add(radioButtonFemale, gbc_radioButtonFemale);

		radioButtonMale = new CustomJRadioButton(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.radioButtonMale"));
		radioButtonMale.setBackground(Utils.getApplicationUserDefinedColor());
		radioButtonMale.setActionCommand(Constants.DATA_BASE_MALE_SIGN.toString());
		GridBagConstraints gbc_radioButtonMale = new GridBagConstraints();
		gbc_radioButtonMale.anchor = GridBagConstraints.WEST;
		gbc_radioButtonMale.insets = new Insets(0, 0, 5, 0);
		gbc_radioButtonMale.gridx = 2;
		gbc_radioButtonMale.gridy = 6;
		add(radioButtonMale, gbc_radioButtonMale);

		groupRadioButtonSex = new ButtonGroup();
		groupRadioButtonSex.add(radioButtonFemale);
		groupRadioButtonSex.add(radioButtonMale);

		JLabel lblBirthDate = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblBirthDate"));
		GridBagConstraints gbc_lblBirthDate = new GridBagConstraints();
		gbc_lblBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthDate.anchor = GridBagConstraints.WEST;
		gbc_lblBirthDate.gridx = 0;
		gbc_lblBirthDate.gridy = 7;
		add(lblBirthDate, gbc_lblBirthDate);

		dateChooserBirthDate = new CustomJDateChooser();
		dateChooserBirthDate.setDateFormatString(Constants.APPLICATION_DATE_FORMAT.toPattern());
		GridBagConstraints gbc_dateChooserBirthDate = new GridBagConstraints();
		gbc_dateChooserBirthDate.gridwidth = 2;
		gbc_dateChooserBirthDate.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooserBirthDate.fill = GridBagConstraints.BOTH;
		gbc_dateChooserBirthDate.gridx = 1;
		gbc_dateChooserBirthDate.gridy = 7;
		add(dateChooserBirthDate, gbc_dateChooserBirthDate);

		lblEMail = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblEMail"));
		GridBagConstraints gbc_lblEMail = new GridBagConstraints();
		gbc_lblEMail.anchor = GridBagConstraints.WEST;
		gbc_lblEMail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEMail.gridx = 0;
		gbc_lblEMail.gridy = 8;
		add(lblEMail, gbc_lblEMail);

		textFieldEMail = new CustomJTextField();
		GridBagConstraints gbc_textFieldEMail = new GridBagConstraints();
		gbc_textFieldEMail.gridwidth = 2;
		gbc_textFieldEMail.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEMail.gridx = 1;
		gbc_textFieldEMail.gridy = 8;
		add(textFieldEMail, gbc_textFieldEMail);
		textFieldEMail.setColumns(10);

		JLabel lblLogin = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblLogin"));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.anchor = GridBagConstraints.WEST;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 9;
		add(lblLogin, gbc_lblLogin);

		textFieldLogin = new CustomJTextField();
		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
		gbc_textFieldLogin.gridwidth = 2;
		gbc_textFieldLogin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 9;
		add(textFieldLogin, gbc_textFieldLogin);
		textFieldLogin.setColumns(10);

		lblPassword = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.lblPassword"));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 10;
		add(lblPassword, gbc_lblPassword);

		passwordField = new CustomJPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 10;
		add(passwordField, gbc_passwordField);

		btnRegisterNow = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRegisterNow-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("registrationPanel.btnRegisterNow"));
		GridBagConstraints gbc_btnRegisterNow = new GridBagConstraints();
		gbc_btnRegisterNow.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegisterNow.gridwidth = 3;
		gbc_btnRegisterNow.gridx = 0;
		gbc_btnRegisterNow.gridy = 11;
		add(btnRegisterNow, gbc_btnRegisterNow);
	}
}

package com.javafee.elibrary.core.tabbedform.admdictionaries;

import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.hibernate.dto.library.Author;
import com.javafee.elibrary.hibernate.dto.library.Category;
import com.javafee.elibrary.hibernate.dto.library.PublishingHouse;
import com.javafee.elibrary.core.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.javafee.elibrary.core.unicomponent.jdatechooser.CustomJDateChooser;
import com.javafee.elibrary.core.unicomponent.jradiobutton.CustomJRadioButton;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;
import com.toedter.calendar.JDateChooser;

import lombok.Getter;

public class AdmDictionaryPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	@Getter
	private CockpitEditionPanel cockpitEditionPanel;

	@Getter
	private JTextField textFieldAuthorName;
	@Getter
	private JTextField textFieldAuthorNickname;
	@Getter
	private JTextField textFieldAuthorSurname;
	@Getter
	private JTextField textFieldCategoryName;
	@Getter
	private JTextField textFieldPublishingHouseName;

	@Getter
	private JDateChooser dateChooserBirthDate;

	@Getter
	private JRadioButton radioButtonAuthor;
	@Getter
	private JRadioButton radioButtonCategory;
	@Getter
	private JRadioButton radioButtonPublishingHouse;
	@Getter
	private ButtonGroup groupRadioButtonChoice;

	@Getter
	private JComboBox<Author> comboBoxAuthor;
	@Getter
	private JComboBox<Category> comboBoxCategory;
	@Getter
	private JComboBox<PublishingHouse> comboBoxPublishingHouse;

	public AdmDictionaryPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 128, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 26, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblAuthor = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblAuthor"));
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.gridwidth = 2;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 0;
		add(lblAuthor, gbc_lblAuthor);

		JLabel lblCategory = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblCategory"));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.gridwidth = 2;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 2;
		gbc_lblCategory.gridy = 0;
		add(lblCategory, gbc_lblCategory);

		JLabel lblPublishingHouse = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.lblPublishingHouse"));
		GridBagConstraints gbc_lblPublishingHouse = new GridBagConstraints();
		gbc_lblPublishingHouse.gridwidth = 2;
		gbc_lblPublishingHouse.insets = new Insets(0, 0, 5, 0);
		gbc_lblPublishingHouse.gridx = 4;
		gbc_lblPublishingHouse.gridy = 0;
		add(lblPublishingHouse, gbc_lblPublishingHouse);

		comboBoxAuthor = new CustomJComboBox<Author>();
		GridBagConstraints gbc_comboBoxAuthor = new GridBagConstraints();
		gbc_comboBoxAuthor.gridwidth = 2;
		gbc_comboBoxAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxAuthor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxAuthor.gridx = 0;
		gbc_comboBoxAuthor.gridy = 1;
		add(comboBoxAuthor, gbc_comboBoxAuthor);

		comboBoxCategory = new CustomJComboBox<Category>();
		GridBagConstraints gbc_comboBoxCategory = new GridBagConstraints();
		gbc_comboBoxCategory.gridwidth = 2;
		gbc_comboBoxCategory.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategory.gridx = 2;
		gbc_comboBoxCategory.gridy = 1;
		add(comboBoxCategory, gbc_comboBoxCategory);

		comboBoxPublishingHouse = new CustomJComboBox<PublishingHouse>();
		GridBagConstraints gbc_comboBoxPublishingHouse = new GridBagConstraints();
		gbc_comboBoxPublishingHouse.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxPublishingHouse.gridwidth = 2;
		gbc_comboBoxPublishingHouse.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPublishingHouse.gridx = 4;
		gbc_comboBoxPublishingHouse.gridy = 1;
		add(comboBoxPublishingHouse, gbc_comboBoxPublishingHouse);

		JLabel lblAuthorName = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("admDictionaryPanel.lblAuthorName"));
		GridBagConstraints gbc_lblAuthorName = new GridBagConstraints();
		gbc_lblAuthorName.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorName.gridx = 0;
		gbc_lblAuthorName.gridy = 2;
		add(lblAuthorName, gbc_lblAuthorName);

		textFieldAuthorName = new CustomJTextField();
		GridBagConstraints gbc_textFieldAuthorName = new GridBagConstraints();
		gbc_textFieldAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorName.gridx = 1;
		gbc_textFieldAuthorName.gridy = 2;
		add(textFieldAuthorName, gbc_textFieldAuthorName);
		textFieldAuthorName.setColumns(10);

		JLabel lblCategoryName = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("admDictionaryPanel.lblCategoryName"));
		GridBagConstraints gbc_lblCategoryName = new GridBagConstraints();
		gbc_lblCategoryName.anchor = GridBagConstraints.WEST;
		gbc_lblCategoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategoryName.gridx = 2;
		gbc_lblCategoryName.gridy = 2;
		add(lblCategoryName, gbc_lblCategoryName);

		textFieldCategoryName = new CustomJTextField();
		textFieldCategoryName.setEnabled(false);
		GridBagConstraints gbc_textFieldCategoryName = new GridBagConstraints();
		gbc_textFieldCategoryName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCategoryName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCategoryName.gridx = 3;
		gbc_textFieldCategoryName.gridy = 2;
		add(textFieldCategoryName, gbc_textFieldCategoryName);
		textFieldCategoryName.setColumns(10);

		JLabel lblPublishingHouseName = new CustomJLabel(SystemProperties.getInstance().getResourceBundle()
				.getString("admDictionaryPanel.lblPublishingHouseName"));
		GridBagConstraints gbc_lblPublishingHouseName = new GridBagConstraints();
		gbc_lblPublishingHouseName.anchor = GridBagConstraints.WEST;
		gbc_lblPublishingHouseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublishingHouseName.gridx = 4;
		gbc_lblPublishingHouseName.gridy = 2;
		add(lblPublishingHouseName, gbc_lblPublishingHouseName);

		textFieldPublishingHouseName = new CustomJTextField();
		textFieldPublishingHouseName.setEnabled(false);
		GridBagConstraints gbc_textFieldPublishingHouseName = new GridBagConstraints();
		gbc_textFieldPublishingHouseName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPublishingHouseName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPublishingHouseName.gridx = 5;
		gbc_textFieldPublishingHouseName.gridy = 2;
		add(textFieldPublishingHouseName, gbc_textFieldPublishingHouseName);
		textFieldPublishingHouseName.setColumns(10);

		JLabel lblAuthorNickname = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("admDictionaryPanel.lblAuthorNickname"));
		GridBagConstraints gbc_lblAuthorNickname = new GridBagConstraints();
		gbc_lblAuthorNickname.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorNickname.gridx = 0;
		gbc_lblAuthorNickname.gridy = 3;
		add(lblAuthorNickname, gbc_lblAuthorNickname);

		textFieldAuthorNickname = new CustomJTextField();
		GridBagConstraints gbc_textFieldAuthorNickname = new GridBagConstraints();
		gbc_textFieldAuthorNickname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorNickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorNickname.gridx = 1;
		gbc_textFieldAuthorNickname.gridy = 3;
		add(textFieldAuthorNickname, gbc_textFieldAuthorNickname);
		textFieldAuthorNickname.setColumns(10);

		JLabel lblAuthorSurname = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("admDictionaryPanel.lblAuthorSurname"));
		GridBagConstraints gbc_lblAuthorSurname = new GridBagConstraints();
		gbc_lblAuthorSurname.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorSurname.gridx = 0;
		gbc_lblAuthorSurname.gridy = 4;
		add(lblAuthorSurname, gbc_lblAuthorSurname);

		textFieldAuthorSurname = new CustomJTextField();
		GridBagConstraints gbc_textFieldAuthorSurname = new GridBagConstraints();
		gbc_textFieldAuthorSurname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorSurname.gridx = 1;
		gbc_textFieldAuthorSurname.gridy = 4;
		add(textFieldAuthorSurname, gbc_textFieldAuthorSurname);
		textFieldAuthorSurname.setColumns(10);

		JLabel lblAuthorBirthDate = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("admDictionaryPanel.lblAuthorBirthDate"));
		GridBagConstraints gbc_lblAuthorBirthDate = new GridBagConstraints();
		gbc_lblAuthorBirthDate.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorBirthDate.gridx = 0;
		gbc_lblAuthorBirthDate.gridy = 5;
		add(lblAuthorBirthDate, gbc_lblAuthorBirthDate);

		dateChooserBirthDate = new CustomJDateChooser();
		dateChooserBirthDate.setDateFormatString(Constants.APPLICATION_DATE_FORMAT.toPattern());
		GridBagConstraints gbc_dateChooserBirthDate = new GridBagConstraints();
		gbc_dateChooserBirthDate.anchor = GridBagConstraints.NORTH;
		gbc_dateChooserBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserBirthDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooserBirthDate.gridx = 1;
		gbc_dateChooserBirthDate.gridy = 5;
		add(dateChooserBirthDate, gbc_dateChooserBirthDate);

		radioButtonAuthor = new CustomJRadioButton();
		radioButtonAuthor.setBackground(Utils.getApplicationUserDefinedColor());
		radioButtonAuthor.setSelected(true);
		radioButtonAuthor.setActionCommand(Constants.RADIO_BUTTON_AUTHOR);
		GridBagConstraints gbc_radioButtonAuthor = new GridBagConstraints();
		gbc_radioButtonAuthor.gridwidth = 2;
		gbc_radioButtonAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonAuthor.gridx = 0;
		gbc_radioButtonAuthor.gridy = 6;
		add(radioButtonAuthor, gbc_radioButtonAuthor);

		radioButtonCategory = new CustomJRadioButton();
		radioButtonCategory.setBackground(Utils.getApplicationUserDefinedColor());
		radioButtonCategory.setActionCommand(Constants.RADIO_BUTTON_CATEGORY);
		GridBagConstraints gbc_radioButtonCategory = new GridBagConstraints();
		gbc_radioButtonCategory.gridwidth = 2;
		gbc_radioButtonCategory.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonCategory.gridx = 2;
		gbc_radioButtonCategory.gridy = 6;
		add(radioButtonCategory, gbc_radioButtonCategory);

		radioButtonPublishingHouse = new CustomJRadioButton();
		radioButtonPublishingHouse.setBackground(Utils.getApplicationUserDefinedColor());
		radioButtonPublishingHouse.setActionCommand(Constants.RADIO_BUTTON_PUBLISHING_HOUSE);
		GridBagConstraints gbc_radioButtonPublishingHouse = new GridBagConstraints();
		gbc_radioButtonPublishingHouse.gridwidth = 2;
		gbc_radioButtonPublishingHouse.insets = new Insets(0, 0, 5, 0);
		gbc_radioButtonPublishingHouse.gridx = 4;
		gbc_radioButtonPublishingHouse.gridy = 6;
		add(radioButtonPublishingHouse, gbc_radioButtonPublishingHouse);

		groupRadioButtonChoice = new ButtonGroup();
		groupRadioButtonChoice.add(radioButtonAuthor);
		groupRadioButtonChoice.add(radioButtonCategory);
		groupRadioButtonChoice.add(radioButtonPublishingHouse);

		cockpitEditionPanel = new CockpitEditionPanel();
		GridBagConstraints gbc_cockpitEditionPanel = new GridBagConstraints();
		gbc_cockpitEditionPanel.gridwidth = 2;
		gbc_cockpitEditionPanel.insets = new Insets(0, 0, 0, 5);
		gbc_cockpitEditionPanel.fill = GridBagConstraints.BOTH;
		gbc_cockpitEditionPanel.gridx = 2;
		gbc_cockpitEditionPanel.gridy = 7;
		add(cockpitEditionPanel, gbc_cockpitEditionPanel);
	}
}

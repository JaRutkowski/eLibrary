package com.javafee.elibrary.core.tabbedform.books.frames;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;

import lombok.Getter;

public class BookDataPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTextField textFieldTitle;
	@Getter
	private JTextField textFieldIsbnNumber;
	@Getter
	private JTextField textFieldNumberOfPage;
	@Getter
	private JTextField textFieldNumberOfTomes;
	@Getter
	private JTextField textFieldBookImagePath;
	@Getter
	private JButton btnChooseBookImageFile;

	public BookDataPanel() {
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.bookDataPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblTitle = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblTitle"));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		textFieldTitle = new CustomJTextField();
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.gridwidth = 2;
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTitle.gridx = 1;
		gbc_textFieldTitle.gridy = 0;
		add(textFieldTitle, gbc_textFieldTitle);
		textFieldTitle.setColumns(10);

		JLabel lblIsbnNumber = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblIsbnNumber"));
		GridBagConstraints gbc_lblIsbnNumber = new GridBagConstraints();
		gbc_lblIsbnNumber.anchor = GridBagConstraints.WEST;
		gbc_lblIsbnNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblIsbnNumber.gridx = 0;
		gbc_lblIsbnNumber.gridy = 1;
		add(lblIsbnNumber, gbc_lblIsbnNumber);

		textFieldIsbnNumber = new CustomJTextField();
		GridBagConstraints gbc_textFieldIsbnNumber = new GridBagConstraints();
		gbc_textFieldIsbnNumber.gridwidth = 2;
		gbc_textFieldIsbnNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIsbnNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIsbnNumber.gridx = 1;
		gbc_textFieldIsbnNumber.gridy = 1;
		add(textFieldIsbnNumber, gbc_textFieldIsbnNumber);
		textFieldIsbnNumber.setColumns(10);

		JLabel lblNumberOfPage = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblNumberOfPage"));
		GridBagConstraints gbc_lblNumberOfPage = new GridBagConstraints();
		gbc_lblNumberOfPage.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfPage.gridx = 0;
		gbc_lblNumberOfPage.gridy = 2;
		add(lblNumberOfPage, gbc_lblNumberOfPage);

		textFieldNumberOfPage = new CustomJTextField();
		GridBagConstraints gbc_textFieldNumberOfPage = new GridBagConstraints();
		gbc_textFieldNumberOfPage.gridwidth = 2;
		gbc_textFieldNumberOfPage.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNumberOfPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumberOfPage.gridx = 1;
		gbc_textFieldNumberOfPage.gridy = 2;
		add(textFieldNumberOfPage, gbc_textFieldNumberOfPage);
		textFieldNumberOfPage.setColumns(10);

		JLabel lblNumberOfTomes = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblNumberOfTomes"));
		GridBagConstraints gbc_lblNumberOfTomes = new GridBagConstraints();
		gbc_lblNumberOfTomes.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfTomes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfTomes.gridx = 0;
		gbc_lblNumberOfTomes.gridy = 3;
		add(lblNumberOfTomes, gbc_lblNumberOfTomes);

		textFieldNumberOfTomes = new CustomJTextField();
		GridBagConstraints gbc_textFieldNumberOfTomes = new GridBagConstraints();
		gbc_textFieldNumberOfTomes.gridwidth = 2;
		gbc_textFieldNumberOfTomes.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNumberOfTomes.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumberOfTomes.gridx = 1;
		gbc_textFieldNumberOfTomes.gridy = 3;
		add(textFieldNumberOfTomes, gbc_textFieldNumberOfTomes);
		textFieldNumberOfTomes.setColumns(10);

		JLabel lblBookImage = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblBookImage"));
		GridBagConstraints gbc_lblBookImage = new GridBagConstraints();
		gbc_lblBookImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblBookImage.anchor = GridBagConstraints.WEST;
		gbc_lblBookImage.gridx = 0;
		gbc_lblBookImage.gridy = 4;
		add(lblBookImage, gbc_lblBookImage);

		textFieldBookImagePath = new CustomJTextField();
		textFieldBookImagePath.setEditable(false);
		GridBagConstraints gbc_textFieldBookImagePath = new GridBagConstraints();
		gbc_textFieldBookImagePath.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldBookImagePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldBookImagePath.gridx = 1;
		gbc_textFieldBookImagePath.gridy = 4;
		add(textFieldBookImagePath, gbc_textFieldBookImagePath);
		textFieldBookImagePath.setColumns(10);

		btnChooseBookImageFile = new CustomJButton();
		btnChooseBookImageFile.setIcon(
				new ImageIcon(new ImageIcon(BookDataPanel.class.getResource("/images/btnChooseFile-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnChooseBookImageFile = new GridBagConstraints();
		gbc_btnChooseBookImageFile.gridx = 2;
		gbc_btnChooseBookImageFile.gridy = 4;
		add(btnChooseBookImageFile, gbc_btnChooseBookImageFile);
	}
}

package com.javafee.tabbedform.books.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;

import lombok.Getter;

public class BookDataPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTextField textFieldTitle;
	@Getter
	private JTextField textFieldIsbnNumber;
	@Getter
	private JTextField textFieldNumberOfPage;
	@Getter
	private JTextField textFieldNumberOfTomes;

	public BookDataPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.bookDataPanelBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblTitle"));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		textFieldTitle = new JTextField();
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTitle.gridx = 1;
		gbc_textFieldTitle.gridy = 0;
		add(textFieldTitle, gbc_textFieldTitle);
		textFieldTitle.setColumns(10);

		JLabel lblIsbnNumber = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblIsbnNumber"));
		GridBagConstraints gbc_lblIsbnNumber = new GridBagConstraints();
		gbc_lblIsbnNumber.anchor = GridBagConstraints.WEST;
		gbc_lblIsbnNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblIsbnNumber.gridx = 0;
		gbc_lblIsbnNumber.gridy = 1;
		add(lblIsbnNumber, gbc_lblIsbnNumber);

		textFieldIsbnNumber = new JTextField();
		GridBagConstraints gbc_textFieldIsbnNumber = new GridBagConstraints();
		gbc_textFieldIsbnNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIsbnNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIsbnNumber.gridx = 1;
		gbc_textFieldIsbnNumber.gridy = 1;
		add(textFieldIsbnNumber, gbc_textFieldIsbnNumber);
		textFieldIsbnNumber.setColumns(10);

		JLabel lblNumberOfPage = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblNumberOfPage"));
		GridBagConstraints gbc_lblNumberOfPage = new GridBagConstraints();
		gbc_lblNumberOfPage.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfPage.gridx = 0;
		gbc_lblNumberOfPage.gridy = 2;
		add(lblNumberOfPage, gbc_lblNumberOfPage);

		textFieldNumberOfPage = new JTextField();
		GridBagConstraints gbc_textFieldNumberOfPage = new GridBagConstraints();
		gbc_textFieldNumberOfPage.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNumberOfPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumberOfPage.gridx = 1;
		gbc_textFieldNumberOfPage.gridy = 2;
		add(textFieldNumberOfPage, gbc_textFieldNumberOfPage);
		textFieldNumberOfPage.setColumns(10);

		JLabel lblNumberOfTomes = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("bookDataPanel.lblNumberOfTomes"));
		GridBagConstraints gbc_lblNumberOfTomes = new GridBagConstraints();
		gbc_lblNumberOfTomes.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfTomes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumberOfTomes.gridx = 0;
		gbc_lblNumberOfTomes.gridy = 3;
		add(lblNumberOfTomes, gbc_lblNumberOfTomes);

		textFieldNumberOfTomes = new JTextField();
		GridBagConstraints gbc_textFieldNumberOfTomes = new GridBagConstraints();
		gbc_textFieldNumberOfTomes.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumberOfTomes.gridx = 1;
		gbc_textFieldNumberOfTomes.gridy = 3;
		add(textFieldNumberOfTomes, gbc_textFieldNumberOfTomes);
		textFieldNumberOfTomes.setColumns(10);
	}
}

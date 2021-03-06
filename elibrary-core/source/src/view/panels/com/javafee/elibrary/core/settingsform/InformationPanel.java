package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JLabel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;

public class InformationPanel extends BasePanel {
	private JLabel lblTitle;
	private JLabel lblDescription;
	private JLabel lblMenuItems;

	public InformationPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblTitle = new CustomJLabel();
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(5, 5, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		lblDescription = new CustomJLabel();
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 1;
		add(lblDescription, gbc_lblDescription);

		lblMenuItems = new CustomJLabel();
		GridBagConstraints gbc_lblMenuItems = new GridBagConstraints();
		gbc_lblMenuItems.anchor = GridBagConstraints.WEST;
		gbc_lblMenuItems.insets = new Insets(0, 5, 5, 5);
		gbc_lblMenuItems.gridx = 0;
		gbc_lblMenuItems.gridy = 2;
		add(lblMenuItems, gbc_lblMenuItems);
	}

	public void reloadLblTextContent(String lblTitleText, String lblDescriptionText, String lblMenuItemsText) {
		lblTitle.setText(lblTitleText);
		lblDescription.setText(lblDescriptionText);
		lblMenuItems.setText(lblMenuItemsText);
	}
}

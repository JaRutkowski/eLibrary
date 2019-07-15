package com.javafee.settingsform;

import com.javafee.common.BasePanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InformationPanel extends BasePanel {
	private JLabel lblTitle;
	private JLabel lblDescription;
	private JLabel lblMenuItems;

	public InformationPanel(String lblTitleText, String lblDescriptionText, String lblMenuItemsText) {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblTitle = new JLabel(lblTitleText);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);
		
		lblDescription = new JLabel(lblDescriptionText);
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 1;
		add(lblDescription, gbc_lblDescription);
		
		lblMenuItems = new JLabel(lblMenuItemsText);
		GridBagConstraints gbc_lblMenuItems = new GridBagConstraints();
		gbc_lblMenuItems.gridx = 0;
		gbc_lblMenuItems.gridy = 2;
		add(lblMenuItems, gbc_lblMenuItems);
	}
}

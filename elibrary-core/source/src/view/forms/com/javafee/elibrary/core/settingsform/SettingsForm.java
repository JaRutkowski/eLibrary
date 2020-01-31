package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JFrame;

import com.javafee.elibrary.core.common.BaseForm;

import lombok.Getter;

public class SettingsForm extends BaseForm {

	@Getter
	private SettingsPanel settingsPanel;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		settingsPanel = new SettingsPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) settingsPanel.getLayout();
		gridBagLayout_1.columnWidths = new int[]{181, 181};
		GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
		gbc_settingsPanel.insets = new Insets(0, 0, 0, 5);
		gbc_settingsPanel.fill = GridBagConstraints.BOTH;
		gbc_settingsPanel.gridx = 0;
		gbc_settingsPanel.gridy = 0;
		frame.getContentPane().add(settingsPanel, gbc_settingsPanel);
	}
}

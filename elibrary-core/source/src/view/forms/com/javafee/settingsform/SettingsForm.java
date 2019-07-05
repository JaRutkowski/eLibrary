package com.javafee.settingsform;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.javafee.form.BaseForm;

import lombok.Getter;

public class SettingsForm extends BaseForm {
	@Getter
	private JPanel settingsPanel;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 162, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		settingsPanel = new SettingsPanel();
		GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
		gbc_settingsPanel.insets = new Insets(0, 0, 0, 5);
		gbc_settingsPanel.fill = GridBagConstraints.BOTH;
		gbc_settingsPanel.gridx = 0;
		gbc_settingsPanel.gridy = 0;
		frame.getContentPane().add(settingsPanel, gbc_settingsPanel);
	}
}

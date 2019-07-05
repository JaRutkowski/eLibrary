package com.javafee.settingsform;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.javafee.common.Constants;
import com.javafee.common.Utils;
import com.javafee.startform.StartForm;

import lombok.Getter;

public class SettingsForm {
	@Getter
	private JFrame frame;

	@Getter
	private JPanel settingsPanel;

	public SettingsForm() {
		initialize();
	}

	private void initialize() {
		Utils.setLookAndFeel();
		frame = new JFrame();
		frame.getContentPane().setBackground(Utils.getApplicationColor());
		frame.setTitle(Constants.APPLICATION_NAME);
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
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

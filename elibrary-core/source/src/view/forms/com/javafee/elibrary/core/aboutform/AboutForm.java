package com.javafee.elibrary.core.aboutform;

import java.awt.*;

import javax.swing.JFrame;

import com.javafee.elibrary.core.common.BaseForm;

import lombok.Getter;

public class AboutForm extends BaseForm {

	@Getter
	private AboutPanel aboutPanel;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();

		frame.setBounds(100, 100, 350, 420);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		aboutPanel = new AboutPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(aboutPanel, gbc_panel);
	}
}

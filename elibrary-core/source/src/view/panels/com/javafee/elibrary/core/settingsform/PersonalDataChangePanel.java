package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.uniform.CockpitConfirmationPanel;

import lombok.Getter;

public class PersonalDataChangePanel extends BasePanel {
	@Getter
	private PersonalDataPanel personalDataPanel;
	@Getter
	private CockpitConfirmationPanel cockpitConfirmationPanel;

	public PersonalDataChangePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		personalDataPanel = new PersonalDataPanel();
		GridBagConstraints gbc_dataPanel = new GridBagConstraints();
		gbc_dataPanel.insets = new Insets(0, 0, 5, 0);
		gbc_dataPanel.fill = GridBagConstraints.BOTH;
		gbc_dataPanel.gridx = 0;
		gbc_dataPanel.gridy = 0;
		add(personalDataPanel, gbc_dataPanel);

		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(cockpitConfirmationPanel, gbc_panel);
	}
}

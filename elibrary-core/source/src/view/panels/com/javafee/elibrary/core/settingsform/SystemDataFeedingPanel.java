package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;

import lombok.Getter;

@Getter
public class SystemDataFeedingPanel extends BasePanel {
	private SystemDataFeedingTablePanel systemDataFeedingTablePanel;
	private JButton btnCheckAllDataValues;
	private JButton btnRunAll;

	public SystemDataFeedingPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		systemDataFeedingTablePanel = new SystemDataFeedingTablePanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(systemDataFeedingTablePanel, gbc_panel);

		btnCheckAllDataValues = new JButton(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanel.btnCheckAllDataValues"));
		GridBagConstraints gbc_btnCheckAllDataValues = new GridBagConstraints();
		gbc_btnCheckAllDataValues.insets = new Insets(0, 0, 5, 0);
		gbc_btnCheckAllDataValues.gridx = 0;
		gbc_btnCheckAllDataValues.gridy = 1;
		add(btnCheckAllDataValues, gbc_btnCheckAllDataValues);

		btnRunAll = new JButton(SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanel.btnRunAll"));
		GridBagConstraints gbc_btnRunAll = new GridBagConstraints();
		gbc_btnRunAll.gridx = 0;
		gbc_btnRunAll.gridy = 2;
		add(btnRunAll, gbc_btnRunAll);
	}

}

package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;

import lombok.Getter;

@Getter
public class SystemInstallationPanel extends BasePanel {
	private JLabel lblVersionValue;
	private JLabel lblBuildNumberValue;
	private JLabel lblLastBuildDateValue;
	private JLabel lblLastBuildStatusValue;

	public SystemInstallationPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel buildDataPanel = new BasePanel();
		buildDataPanel.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("systemInstallationPanel.systemInstallationPanelTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_buildDataPanel = new GridBagConstraints();
		gbc_buildDataPanel.fill = GridBagConstraints.BOTH;
		gbc_buildDataPanel.gridx = 0;
		gbc_buildDataPanel.gridy = 0;
		add(buildDataPanel, gbc_buildDataPanel);
		GridBagLayout gbl_buildDataPanel = new GridBagLayout();
		gbl_buildDataPanel.columnWidths = new int[]{0, 0, 0};
		gbl_buildDataPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_buildDataPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_buildDataPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		buildDataPanel.setLayout(gbl_buildDataPanel);

		JLabel lblVersion = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("systemInstallationPanel.lblVersion"));
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.WEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 0;
		gbc_lblVersion.gridy = 0;
		buildDataPanel.add(lblVersion, gbc_lblVersion);

		lblVersionValue = new CustomJLabel();
		GridBagConstraints gbc_lblVersionValue = new GridBagConstraints();
		gbc_lblVersionValue.anchor = GridBagConstraints.WEST;
		gbc_lblVersionValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblVersionValue.gridx = 1;
		gbc_lblVersionValue.gridy = 0;
		buildDataPanel.add(lblVersionValue, gbc_lblVersionValue);

		JLabel lblBuildNumber = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("systemInstallationPanel.lblBuildNumber"));
		GridBagConstraints gbc_lblBuildNumber = new GridBagConstraints();
		gbc_lblBuildNumber.anchor = GridBagConstraints.WEST;
		gbc_lblBuildNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuildNumber.gridx = 0;
		gbc_lblBuildNumber.gridy = 1;
		buildDataPanel.add(lblBuildNumber, gbc_lblBuildNumber);

		lblBuildNumberValue = new CustomJLabel();
		GridBagConstraints gbc_lblBuildNumberValue = new GridBagConstraints();
		gbc_lblBuildNumberValue.anchor = GridBagConstraints.WEST;
		gbc_lblBuildNumberValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblBuildNumberValue.gridx = 1;
		gbc_lblBuildNumberValue.gridy = 1;
		buildDataPanel.add(lblBuildNumberValue, gbc_lblBuildNumberValue);

		JLabel lblLastBuildDate = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("systemInstallationPanel.lblLastBuildDate"));
		GridBagConstraints gbc_lblLastBuildDate = new GridBagConstraints();
		gbc_lblLastBuildDate.anchor = GridBagConstraints.WEST;
		gbc_lblLastBuildDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastBuildDate.gridx = 0;
		gbc_lblLastBuildDate.gridy = 2;
		buildDataPanel.add(lblLastBuildDate, gbc_lblLastBuildDate);

		lblLastBuildDateValue = new CustomJLabel();
		GridBagConstraints gbc_lblLastBuildDateValue = new GridBagConstraints();
		gbc_lblLastBuildDateValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblLastBuildDateValue.anchor = GridBagConstraints.WEST;
		gbc_lblLastBuildDateValue.gridx = 1;
		gbc_lblLastBuildDateValue.gridy = 2;
		buildDataPanel.add(lblLastBuildDateValue, gbc_lblLastBuildDateValue);

		JLabel lblLastBuildStatus = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("systemInstallationPanel.lblLastBuildStatus"));
		GridBagConstraints gbc_lblLastBuildStatus = new GridBagConstraints();
		gbc_lblLastBuildStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblLastBuildStatus.gridx = 0;
		gbc_lblLastBuildStatus.gridy = 3;
		buildDataPanel.add(lblLastBuildStatus, gbc_lblLastBuildStatus);

		lblLastBuildStatusValue = new CustomJLabel();
		GridBagConstraints gbc_lblLastBuildStatusValue = new GridBagConstraints();
		gbc_lblLastBuildStatusValue.anchor = GridBagConstraints.WEST;
		gbc_lblLastBuildStatusValue.gridx = 1;
		gbc_lblLastBuildStatusValue.gridy = 3;
		buildDataPanel.add(lblLastBuildStatusValue, gbc_lblLastBuildStatusValue);
	}
}

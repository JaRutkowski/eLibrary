package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;

import lombok.Getter;

@Getter
public class SystemMonitorPanel extends BasePanel {
	private JCheckBox chckbxApiServices;
	private JLabel lblApiServicesHealth;
	private JLabel lblApiServicesStatus;
	private JLabel lblApiServicesHostValue;
	private JLabel lblApiServicesPortValue;
	private JButton btnCheckHealth;
	private JLabel lblApiServicesVersionValue;
	private JCheckBox chckbxDbConnection;
	private JLabel lblDbConnectionHostValue;
	private JLabel lblDbConnectionNameValue;
	private JLabel lblDbConnectionHealth;
	private JLabel lblDbConnectionStatus;

	public SystemMonitorPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{91, 68, 420, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		chckbxApiServices = new CustomJCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.chckbxApiServices"));
		GridBagConstraints gbc_chckbxApiServices = new GridBagConstraints();
		gbc_chckbxApiServices.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxApiServices.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxApiServices.gridx = 0;
		gbc_chckbxApiServices.gridy = 0;
		add(chckbxApiServices, gbc_chckbxApiServices);

		lblApiServicesHealth = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesHealth = new GridBagConstraints();
		gbc_lblApiServicesHealth.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApiServicesHealth.insets = new Insets(0, 0, 5, 5);
		gbc_lblApiServicesHealth.gridx = 1;
		gbc_lblApiServicesHealth.gridy = 0;
		add(lblApiServicesHealth, gbc_lblApiServicesHealth);

		lblApiServicesStatus = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesStatus = new GridBagConstraints();
		gbc_lblApiServicesStatus.anchor = GridBagConstraints.WEST;
		gbc_lblApiServicesStatus.insets = new Insets(0, 0, 5, 0);
		gbc_lblApiServicesStatus.gridx = 2;
		gbc_lblApiServicesStatus.gridy = 0;
		add(lblApiServicesStatus, gbc_lblApiServicesStatus);

		JPanel apiServicesDetailsPanel = new BasePanel();
		apiServicesDetailsPanel.setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.apiServicesDetailsPanelTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_apiServicesDetailsPanel = new GridBagConstraints();
		gbc_apiServicesDetailsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_apiServicesDetailsPanel.fill = GridBagConstraints.BOTH;
		gbc_apiServicesDetailsPanel.gridwidth = 3;
		gbc_apiServicesDetailsPanel.gridx = 0;
		gbc_apiServicesDetailsPanel.gridy = 1;
		add(apiServicesDetailsPanel, gbc_apiServicesDetailsPanel);
		GridBagLayout gbl_apiServicesDetailsPanel = new GridBagLayout();
		gbl_apiServicesDetailsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_apiServicesDetailsPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_apiServicesDetailsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_apiServicesDetailsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		apiServicesDetailsPanel.setLayout(gbl_apiServicesDetailsPanel);

		JLabel lblApiServicesHost = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblApiServicesHost"));
		GridBagConstraints gbc_lblApiServicesHost = new GridBagConstraints();
		gbc_lblApiServicesHost.anchor = GridBagConstraints.WEST;
		gbc_lblApiServicesHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblApiServicesHost.gridx = 0;
		gbc_lblApiServicesHost.gridy = 0;
		apiServicesDetailsPanel.add(lblApiServicesHost, gbc_lblApiServicesHost);

		lblApiServicesHostValue = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesHostValue = new GridBagConstraints();
		gbc_lblApiServicesHostValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApiServicesHostValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblApiServicesHostValue.gridx = 1;
		gbc_lblApiServicesHostValue.gridy = 0;
		apiServicesDetailsPanel.add(lblApiServicesHostValue, gbc_lblApiServicesHostValue);

		JLabel lblApiServicesPort = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblApiServicesPort"));
		GridBagConstraints gbc_lblApiServicesPort = new GridBagConstraints();
		gbc_lblApiServicesPort.anchor = GridBagConstraints.WEST;
		gbc_lblApiServicesPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblApiServicesPort.gridx = 0;
		gbc_lblApiServicesPort.gridy = 1;
		apiServicesDetailsPanel.add(lblApiServicesPort, gbc_lblApiServicesPort);

		lblApiServicesPortValue = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesPortValue = new GridBagConstraints();
		gbc_lblApiServicesPortValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblApiServicesPortValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApiServicesPortValue.gridx = 1;
		gbc_lblApiServicesPortValue.gridy = 1;
		apiServicesDetailsPanel.add(lblApiServicesPortValue, gbc_lblApiServicesPortValue);

		JLabel lblApiServicesVersion = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblApiServicesVersion"));
		GridBagConstraints gbc_lblApiServicesVersion = new GridBagConstraints();
		gbc_lblApiServicesVersion.insets = new Insets(0, 0, 0, 5);
		gbc_lblApiServicesVersion.gridx = 0;
		gbc_lblApiServicesVersion.gridy = 2;
		apiServicesDetailsPanel.add(lblApiServicesVersion, gbc_lblApiServicesVersion);

		lblApiServicesVersionValue = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesVersionValue = new GridBagConstraints();
		gbc_lblApiServicesVersionValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApiServicesVersionValue.gridx = 1;
		gbc_lblApiServicesVersionValue.gridy = 2;
		apiServicesDetailsPanel.add(lblApiServicesVersionValue, gbc_lblApiServicesVersionValue);

		chckbxDbConnection = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.chckbxDbConnection"));
		GridBagConstraints gbc_chckbxDbConnection = new GridBagConstraints();
		gbc_chckbxDbConnection.anchor = GridBagConstraints.WEST;
		gbc_chckbxDbConnection.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxDbConnection.gridx = 0;
		gbc_chckbxDbConnection.gridy = 2;
		add(chckbxDbConnection, gbc_chckbxDbConnection);

		lblDbConnectionHealth = new CustomJLabel();
		GridBagConstraints gbc_lblDbConnectionHealth = new GridBagConstraints();
		gbc_lblDbConnectionHealth.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDbConnectionHealth.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbConnectionHealth.gridx = 1;
		gbc_lblDbConnectionHealth.gridy = 2;
		add(lblDbConnectionHealth, gbc_lblDbConnectionHealth);

		lblDbConnectionStatus = new CustomJLabel();
		GridBagConstraints gbc_lblDbConnectionStatus = new GridBagConstraints();
		gbc_lblDbConnectionStatus.anchor = GridBagConstraints.WEST;
		gbc_lblDbConnectionStatus.insets = new Insets(0, 0, 5, 0);
		gbc_lblDbConnectionStatus.gridx = 2;
		gbc_lblDbConnectionStatus.gridy = 2;
		add(lblDbConnectionStatus, gbc_lblDbConnectionStatus);

		JPanel dbConnectionDetailsPanel = new BasePanel();
		dbConnectionDetailsPanel.setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.dbConnectionDetailsPanelTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_dbConnectionDetailsPanel = new GridBagConstraints();
		gbc_dbConnectionDetailsPanel.fill = GridBagConstraints.BOTH;
		gbc_dbConnectionDetailsPanel.gridwidth = 3;
		gbc_dbConnectionDetailsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_dbConnectionDetailsPanel.gridx = 0;
		gbc_dbConnectionDetailsPanel.gridy = 3;
		add(dbConnectionDetailsPanel, gbc_dbConnectionDetailsPanel);
		GridBagLayout gbl_dbConnectionDetailsPanel = new GridBagLayout();
		gbl_dbConnectionDetailsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_dbConnectionDetailsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_dbConnectionDetailsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_dbConnectionDetailsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		dbConnectionDetailsPanel.setLayout(gbl_dbConnectionDetailsPanel);

		JLabel lblDbConnectionHost = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblDbConnectionHost"));
		GridBagConstraints gbc_lblDbConnectionHost = new GridBagConstraints();
		gbc_lblDbConnectionHost.anchor = GridBagConstraints.WEST;
		gbc_lblDbConnectionHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbConnectionHost.gridx = 0;
		gbc_lblDbConnectionHost.gridy = 0;
		dbConnectionDetailsPanel.add(lblDbConnectionHost, gbc_lblDbConnectionHost);

		lblDbConnectionHostValue = new CustomJLabel();
		GridBagConstraints gbc_lblDbConnectionHostValue = new GridBagConstraints();
		gbc_lblDbConnectionHostValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDbConnectionHostValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblDbConnectionHostValue.gridx = 1;
		gbc_lblDbConnectionHostValue.gridy = 0;
		dbConnectionDetailsPanel.add(lblDbConnectionHostValue, gbc_lblDbConnectionHostValue);

		JLabel lblDbConnectionName = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblDbConnectionName"));
		GridBagConstraints gbc_lblDbConnectionName = new GridBagConstraints();
		gbc_lblDbConnectionName.anchor = GridBagConstraints.WEST;
		gbc_lblDbConnectionName.insets = new Insets(0, 0, 0, 5);
		gbc_lblDbConnectionName.gridx = 0;
		gbc_lblDbConnectionName.gridy = 1;
		dbConnectionDetailsPanel.add(lblDbConnectionName, gbc_lblDbConnectionName);

		lblDbConnectionNameValue = new CustomJLabel();
		GridBagConstraints gbc_lblDbConnectionNameValue = new GridBagConstraints();
		gbc_lblDbConnectionNameValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDbConnectionNameValue.gridx = 1;
		gbc_lblDbConnectionNameValue.gridy = 1;
		dbConnectionDetailsPanel.add(lblDbConnectionNameValue, gbc_lblDbConnectionNameValue);

		btnCheckHealth = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnCheckHealth-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.btnCheckHealth"));
		GridBagConstraints gbc_btnCheckHealth = new GridBagConstraints();
		gbc_btnCheckHealth.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCheckHealth.insets = new Insets(0, 0, 0, 5);
		gbc_btnCheckHealth.gridx = 1;
		gbc_btnCheckHealth.gridy = 4;
		add(btnCheckHealth, gbc_btnCheckHealth);
	}
}

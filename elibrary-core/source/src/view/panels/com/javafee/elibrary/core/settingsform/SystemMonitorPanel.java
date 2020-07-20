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
	private JLabel lblApiServicesVersion;
	private JLabel lblApiServicesVersionValue;

	public SystemMonitorPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{260, 72, 400, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_lblApiServicesStatus.anchor = GridBagConstraints.EAST;
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
		gbl_apiServicesDetailsPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_apiServicesDetailsPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_apiServicesDetailsPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		gbc_lblApiServicesHostValue.gridwidth = 2;
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
		gbc_lblApiServicesPortValue.gridwidth = 2;
		gbc_lblApiServicesPortValue.gridx = 1;
		gbc_lblApiServicesPortValue.gridy = 1;
		apiServicesDetailsPanel.add(lblApiServicesPortValue, gbc_lblApiServicesPortValue);

		lblApiServicesVersion = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.lblApiServicesVersion"));
		GridBagConstraints gbc_lblApiServicesVersion = new GridBagConstraints();
		gbc_lblApiServicesVersion.insets = new Insets(0, 0, 0, 5);
		gbc_lblApiServicesVersion.gridx = 0;
		gbc_lblApiServicesVersion.gridy = 2;
		apiServicesDetailsPanel.add(lblApiServicesVersion, gbc_lblApiServicesVersion);

		lblApiServicesVersionValue = new CustomJLabel();
		GridBagConstraints gbc_lblApiServicesVersionValue = new GridBagConstraints();
		gbc_lblApiServicesVersionValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApiServicesVersionValue.gridwidth = 2;
		gbc_lblApiServicesVersionValue.gridx = 1;
		gbc_lblApiServicesVersionValue.gridy = 2;
		apiServicesDetailsPanel.add(lblApiServicesVersionValue, gbc_lblApiServicesVersionValue);

		btnCheckHealth = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnCheckHealth-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("systemMonitorPanel.btnCheckHealth"));
		GridBagConstraints gbc_btnCheckHealth = new GridBagConstraints();
		gbc_btnCheckHealth.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCheckHealth.insets = new Insets(0, 0, 0, 5);
		gbc_btnCheckHealth.gridx = 1;
		gbc_btnCheckHealth.gridy = 2;
		add(btnCheckHealth, gbc_btnCheckHealth);
	}
}

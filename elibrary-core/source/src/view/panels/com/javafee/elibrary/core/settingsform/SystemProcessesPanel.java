package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;

import lombok.Getter;

@Getter
public class SystemProcessesPanel extends BasePanel {
	private JCheckBox chckbxNetworkService;
	private JLabel lblNetworkServiceHealth;
	private JCheckBox chckbxDirectoryWatchService;
	private JLabel lblDirectoryWatchServiceHealth;
	private JButton btnStop;
	private JButton btnCheckHealth;

	public SystemProcessesPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		chckbxNetworkService = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle().getString("systemProcessesPanel.chckbxNetworkService"));
		GridBagConstraints gbc_chckbxNetoworkService = new GridBagConstraints();
		gbc_chckbxNetoworkService.anchor = GridBagConstraints.WEST;
		gbc_chckbxNetoworkService.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNetoworkService.gridx = 0;
		gbc_chckbxNetoworkService.gridy = 0;
		add(chckbxNetworkService, gbc_chckbxNetoworkService);

		lblNetworkServiceHealth = new CustomJLabel();
		GridBagConstraints gbc_lblNetworkServiceHealth = new GridBagConstraints();
		gbc_lblNetworkServiceHealth.insets = new Insets(0, 0, 5, 0);
		gbc_lblNetworkServiceHealth.gridx = 1;
		gbc_lblNetworkServiceHealth.gridy = 0;
		add(lblNetworkServiceHealth, gbc_lblNetworkServiceHealth);

		chckbxDirectoryWatchService = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle().getString("systemProcessesPanel.chckbxDirectoryWatchService"));
		GridBagConstraints gbc_chckbxDirectoryWatchService = new GridBagConstraints();
		gbc_chckbxDirectoryWatchService.anchor = GridBagConstraints.WEST;
		gbc_chckbxDirectoryWatchService.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxDirectoryWatchService.gridx = 0;
		gbc_chckbxDirectoryWatchService.gridy = 1;
		add(chckbxDirectoryWatchService, gbc_chckbxDirectoryWatchService);

		lblDirectoryWatchServiceHealth = new CustomJLabel();
		GridBagConstraints gbc_lblDirectoryWatchServiceHealth = new GridBagConstraints();
		gbc_lblDirectoryWatchServiceHealth.insets = new Insets(0, 0, 5, 0);
		gbc_lblDirectoryWatchServiceHealth.gridx = 1;
		gbc_lblDirectoryWatchServiceHealth.gridy = 1;
		add(lblDirectoryWatchServiceHealth, gbc_lblDirectoryWatchServiceHealth);

		btnStop = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRoundParse-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("systemProcessesPanel.btnStop"));
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.insets = new Insets(0, 0, 0, 5);
		gbc_btnStop.gridx = 0;
		gbc_btnStop.gridy = 2;
		add(btnStop, gbc_btnStop);

		btnCheckHealth = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnCheckHealth-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("systemProcessesPanel.btnCheckHealth"));
		GridBagConstraints gbc_btnCheckHealth = new GridBagConstraints();
		gbc_btnCheckHealth.gridx = 1;
		gbc_btnCheckHealth.gridy = 2;
		add(btnCheckHealth, gbc_btnCheckHealth);
	}
}

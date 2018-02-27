package com.javafee.uniform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;

public class ReloadPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton btnReload;

	public ReloadPanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnReload = new JButton(SystemProperties.getInstance().getResourceBundle().getString("reloadPanel.btnReload"));
		btnReload.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnReload-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnReload = new GridBagConstraints();
		gbc_btnReload.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnReload.gridx = 0;
		gbc_btnReload.gridy = 0;
		add(btnReload, gbc_btnReload);
	}

	public JButton getBtnReload() {
		return btnReload;
	}
}

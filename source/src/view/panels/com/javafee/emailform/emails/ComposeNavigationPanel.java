package com.javafee.emailform.emails;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.startform.RegistrationPanel;

import lombok.Getter;

public class ComposeNavigationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnSaveAsDraft;
	@Getter
	private JButton btnSend;
	@Getter
	private JButton btnAddAttachment;
	@Getter
	private JButton btnClear;

	public ComposeNavigationPanel() {
		setLayout(new GridLayout(0, 2, 0, 0));

		btnAddAttachment = new JButton(SystemProperties.getInstance().getResourceBundle()
				.getString("composeNavigationPanel.btnAddAttachment"));
		btnAddAttachment.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddAttachment-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		btnAddAttachment.setEnabled(false);
		add(btnAddAttachment);

		btnSaveAsDraft = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSaveAsDraft"));
		btnSaveAsDraft.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSaveAsDraft-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		add(btnSaveAsDraft);

		btnSend = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSend"));
		btnSend.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSend-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		add(btnSend);

		btnClear = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnClear"));
		btnClear.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnClear-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		add(btnClear);
	}
}

package com.javafee.elibrary.core.emailform.emails;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class ComposeNavigationPanel extends BasePanel {
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
		super();
		setLayout(new GridLayout(0, 2, 0, 0));

		btnAddAttachment = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddAttachment-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnAddAttachment"));
		btnAddAttachment.setEnabled(false);
		add(btnAddAttachment);

		btnSaveAsDraft = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSaveAsDraft-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSaveAsDraft"));
		add(btnSaveAsDraft);

		btnSend = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSend-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSend"));
		add(btnSend);

		btnClear = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnClear-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnClear"));
		add(btnClear);
	}
}

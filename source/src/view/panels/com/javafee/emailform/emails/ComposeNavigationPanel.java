package com.javafee.emailform.emails;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;

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
	private JButton btnDelete;

	public ComposeNavigationPanel() {
		setLayout(new GridLayout(0, 2, 0, 0));
		
		btnAddAttachment = new JButton(SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnAddAttachment"));
		add(btnAddAttachment);
		
		btnSaveAsDraft = new JButton(SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSaveAsDraft"));
		add(btnSaveAsDraft);
		
		btnSend = new JButton(SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnSend"));
		add(btnSend);
		
		btnDelete = new JButton(SystemProperties.getInstance().getResourceBundle().getString("composeNavigationPanel.btnDelete"));
		add(btnDelete);
	}
}

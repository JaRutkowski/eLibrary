package com.javafee.emailform.emails;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.startform.RegistrationPanel;

import lombok.Getter;

public class DraftNavigationPanel extends JPanel {

	private static final long serialVersionUID = -79852007511436215L;

	@Getter
	private JButton btnModify;
	@Getter
	private JButton btnSend;
	@Getter
	private JButton btnDelete;

	public DraftNavigationPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnModify = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnModify"));
		btnModify.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnModify-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnPreview = new GridBagConstraints();
		gbc_btnPreview.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPreview.insets = new Insets(0, 0, 0, 5);
		gbc_btnPreview.gridx = 0;
		gbc_btnPreview.gridy = 0;
		add(btnModify, gbc_btnPreview);

		btnSend = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnSend"));
		btnSend.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSend-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnSendAgain = new GridBagConstraints();
		gbc_btnSendAgain.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSendAgain.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendAgain.gridx = 1;
		gbc_btnSendAgain.gridy = 0;
		add(btnSend, gbc_btnSendAgain);

		btnDelete = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnDelete"));
		btnDelete.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnDelete-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.gridx = 2;
		gbc_btnRemove.gridy = 0;
		add(btnDelete, gbc_btnRemove);
	}
}

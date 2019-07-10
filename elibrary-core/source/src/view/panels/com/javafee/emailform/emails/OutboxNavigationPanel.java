package com.javafee.emailform.emails;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;

import lombok.Getter;

public class OutboxNavigationPanel extends JPanel {

	private static final long serialVersionUID = 170626561363626778L;

	@Getter
	private JButton btnPreview;
	@Getter
	private JButton btnSendAgain;
	@Getter
	private JButton btnDelete;

	public OutboxNavigationPanel() {
		setBackground(Utils.getApplicationUserDefineColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnPreview = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("outboxNavigationPanel.btnPreview"));
		btnPreview.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnOpenMessage-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnPreview = new GridBagConstraints();
		gbc_btnPreview.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPreview.insets = new Insets(0, 0, 0, 5);
		gbc_btnPreview.gridx = 0;
		gbc_btnPreview.gridy = 0;
		add(btnPreview, gbc_btnPreview);

		btnSendAgain = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("outboxNavigationPanel.btnSendAgain"));
		btnSendAgain.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSendAgain-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnSendAgain = new GridBagConstraints();
		gbc_btnSendAgain.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSendAgain.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendAgain.gridx = 1;
		gbc_btnSendAgain.gridy = 0;
		add(btnSendAgain, gbc_btnSendAgain);

		btnDelete = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("outboxNavigationPanel.btnDelete"));
		btnDelete.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRemove-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.gridx = 2;
		gbc_btnRemove.gridy = 0;
		add(btnDelete, gbc_btnRemove);

	}

}
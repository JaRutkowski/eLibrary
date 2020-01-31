package com.javafee.elibrary.core.emailform.emails;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class DraftNavigationPanel extends BasePanel {

	private static final long serialVersionUID = -79852007511436215L;

	@Getter
	private JButton btnModify;
	@Getter
	private JButton btnSend;
	@Getter
	private JButton btnDelete;

	public DraftNavigationPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnModify = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnModify-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnModify"));
		GridBagConstraints gbc_btnPreview = new GridBagConstraints();
		gbc_btnPreview.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPreview.insets = new Insets(0, 0, 0, 5);
		gbc_btnPreview.gridx = 0;
		gbc_btnPreview.gridy = 0;
		add(btnModify, gbc_btnPreview);

		btnSend = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSend-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnSend"));
		GridBagConstraints gbc_btnSendAgain = new GridBagConstraints();
		gbc_btnSendAgain.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSendAgain.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendAgain.gridx = 1;
		gbc_btnSendAgain.gridy = 0;
		add(btnSend, gbc_btnSendAgain);

		btnDelete = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnDelete-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("draftNavigationPanel.btnDelete"));
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.gridx = 2;
		gbc_btnRemove.gridy = 0;
		add(btnDelete, gbc_btnRemove);
	}
}

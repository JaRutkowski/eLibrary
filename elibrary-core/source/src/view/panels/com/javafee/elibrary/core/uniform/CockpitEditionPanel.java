package com.javafee.elibrary.core.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class CockpitEditionPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAdd;
	@Getter
	private JButton btnModify;
	@Getter
	private JButton btnDelete;

	public CockpitEditionPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnAdd = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAdd-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnAdd"));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.NORTH;
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		add(btnAdd, gbc_btnAdd);

		btnModify = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnModify-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnModify"));
		GridBagConstraints gbc_btnModify = new GridBagConstraints();
		gbc_btnModify.anchor = GridBagConstraints.NORTH;
		gbc_btnModify.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModify.insets = new Insets(0, 0, 0, 5);
		gbc_btnModify.gridx = 1;
		gbc_btnModify.gridy = 0;
		add(btnModify, gbc_btnModify);

		btnDelete = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnDelete-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnDelete"));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.NORTH;
		gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);
	}
}

package com.javafee.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javafee.common.BasePanel;
import com.javafee.startform.RegistrationPanel;
import com.javafee.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class AddMultiPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAddToList;
	@Getter
	private JButton btnRemoveFromList;
	@Getter
	private JButton btnCheckList;

	public AddMultiPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnAddToList = new CustomJButton("");
		btnAddToList.setOpaque(false);
		btnAddToList.setContentAreaFilled(false);
		btnAddToList.setBorderPainted(false);
		btnAddToList.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddToList-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnAddToList = new GridBagConstraints();
		gbc_btnAddToList.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddToList.gridx = 0;
		gbc_btnAddToList.gridy = 0;
		add(btnAddToList, gbc_btnAddToList);

		btnRemoveFromList = new CustomJButton("");
		btnRemoveFromList.setOpaque(false);
		btnRemoveFromList.setContentAreaFilled(false);
		btnRemoveFromList.setBorderPainted(false);
		btnRemoveFromList.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRemoveFromList-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRemoveFromList = new GridBagConstraints();
		gbc_btnRemoveFromList.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveFromList.gridx = 1;
		gbc_btnRemoveFromList.gridy = 0;
		add(btnRemoveFromList, gbc_btnRemoveFromList);

		btnCheckList = new CustomJButton("");
		btnCheckList.setOpaque(false);
		btnCheckList.setContentAreaFilled(false);
		btnCheckList.setBorderPainted(false);
		btnCheckList.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnCheckList-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnCheckList = new GridBagConstraints();
		gbc_btnCheckList.gridx = 2;
		gbc_btnCheckList.gridy = 0;
		add(btnCheckList, gbc_btnCheckList);
	}
}

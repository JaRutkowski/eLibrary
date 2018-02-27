package com.javafee.uniform;

import javax.swing.JPanel;

import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AddMultiPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton btnAddToList;
	private JButton btnRemoveFromList;
	private JButton btnCheckList;
	
	public AddMultiPanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnAddToList = new JButton("");
		btnAddToList.setOpaque(false);
		btnAddToList.setContentAreaFilled(false);
		btnAddToList.setBorderPainted(false);
		btnAddToList.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddToList-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnAddToList = new GridBagConstraints();
		gbc_btnAddToList.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddToList.gridx = 0;
		gbc_btnAddToList.gridy = 0;
		add(btnAddToList, gbc_btnAddToList);
		
		btnRemoveFromList = new JButton("");
		btnRemoveFromList.setOpaque(false);
		btnRemoveFromList.setContentAreaFilled(false);
		btnRemoveFromList.setBorderPainted(false);
		btnRemoveFromList.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRemoveFromList-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRemoveFromList = new GridBagConstraints();
		gbc_btnRemoveFromList.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveFromList.gridx = 1;
		gbc_btnRemoveFromList.gridy = 0;
		add(btnRemoveFromList, gbc_btnRemoveFromList);
		
		btnCheckList = new JButton("");
		btnCheckList.setOpaque(false);
		btnCheckList.setContentAreaFilled(false);
		btnCheckList.setBorderPainted(false);
		btnCheckList.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnCheckList-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnCheckList = new GridBagConstraints();
		gbc_btnCheckList.gridx = 2;
		gbc_btnCheckList.gridy = 0;
		add(btnCheckList, gbc_btnCheckList);
	}
	
	public JButton getBtnAddToList() {
		return btnAddToList;
	}
	
	public JButton getBtnRemoveFromList() {
		return btnRemoveFromList;
	}
	
	public JButton getBtnCheckList() {
		return btnCheckList;
	}
}

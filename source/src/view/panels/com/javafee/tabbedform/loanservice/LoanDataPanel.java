package com.javafee.tabbedform.loanservice;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.uniform.CockpitEditionPanel;

import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

public class LoanDataPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private CockpitEditionPanel cockpitEditionPanel;

	private JTextField textFieldInventoryNumber;
	private JComboBox<Client> comboBoxClient;
	private JComboBox<Book> comboBoxBook;
	private JCheckBox chckbxIsReadingRoom;

	private JLabel lblClients;
	private JLabel lblBooks;
	private JLabel lblInventoryNumber;

	public LoanDataPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("loanDataPanel.loanDataPanelBorderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 200, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblClients = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("loanDataPanel.lblClient"));
		GridBagConstraints gbc_lblClients = new GridBagConstraints();
		gbc_lblClients.insets = new Insets(0, 0, 5, 5);
		gbc_lblClients.anchor = GridBagConstraints.WEST;
		gbc_lblClients.gridx = 0;
		gbc_lblClients.gridy = 0;
		add(lblClients, gbc_lblClients);

		comboBoxClient = new JComboBox<Client>();
		GridBagConstraints gbc_comboBoxClient = new GridBagConstraints();
		gbc_comboBoxClient.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxClient.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxClient.gridx = 1;
		gbc_comboBoxClient.gridy = 0;
		add(comboBoxClient, gbc_comboBoxClient);

		lblBooks = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("loanDataPanel.lblBook"));
		GridBagConstraints gbc_lblBooks = new GridBagConstraints();
		gbc_lblBooks.insets = new Insets(0, 0, 5, 5);
		gbc_lblBooks.anchor = GridBagConstraints.WEST;
		gbc_lblBooks.gridx = 0;
		gbc_lblBooks.gridy = 1;
		add(lblBooks, gbc_lblBooks);

		comboBoxBook = new JComboBox<Book>();
		GridBagConstraints gbc_comboBoxBook = new GridBagConstraints();
		gbc_comboBoxBook.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxBook.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBook.gridx = 1;
		gbc_comboBoxBook.gridy = 1;
		add(comboBoxBook, gbc_comboBoxBook);

		lblInventoryNumber = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("loanDataPanel.lblInventoryNumber"));
		GridBagConstraints gbc_lblInventoryNumber = new GridBagConstraints();
		gbc_lblInventoryNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblInventoryNumber.anchor = GridBagConstraints.WEST;
		gbc_lblInventoryNumber.gridx = 0;
		gbc_lblInventoryNumber.gridy = 2;
		add(lblInventoryNumber, gbc_lblInventoryNumber);

		textFieldInventoryNumber = new JTextField();
		GridBagConstraints gbc_textFieldInventoryNumber = new GridBagConstraints();
		gbc_textFieldInventoryNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInventoryNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInventoryNumber.gridx = 1;
		gbc_textFieldInventoryNumber.gridy = 2;
		add(textFieldInventoryNumber, gbc_textFieldInventoryNumber);
		textFieldInventoryNumber.setColumns(10);

		chckbxIsReadingRoom = new JCheckBox(SystemProperties.getInstance().getResourceBundle().getString("loanDataPanel.chckbxIsReadingRoom"));
		GridBagConstraints gbc_chckbxIsReadingRoom = new GridBagConstraints();
		gbc_chckbxIsReadingRoom.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxIsReadingRoom.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxIsReadingRoom.gridx = 1;
		gbc_chckbxIsReadingRoom.gridy = 3;
		add(chckbxIsReadingRoom, gbc_chckbxIsReadingRoom);

		cockpitEditionPanel = new CockpitEditionPanel();
		GridBagConstraints gbc_cockpitEditionPanel = new GridBagConstraints();
		gbc_cockpitEditionPanel.gridwidth = 2;
		gbc_cockpitEditionPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cockpitEditionPanel.fill = GridBagConstraints.BOTH;
		gbc_cockpitEditionPanel.gridx = 0;
		gbc_cockpitEditionPanel.gridy = 4;
		add(cockpitEditionPanel, gbc_cockpitEditionPanel);
	}

	public CockpitEditionPanel getCockpitEditionPanel() {
		return cockpitEditionPanel;
	}

	public JTextField getTextFieldInventoryNumber() {
		return textFieldInventoryNumber;
	}

	public JComboBox<Client> getComboBoxClient() {
		return comboBoxClient;
	}

	public JComboBox<Book> getComboBoxBook() {
		return comboBoxBook;
	}

	public JCheckBox getChckbxIsReadingRoom() {
		return chckbxIsReadingRoom;
	}

	public JLabel getLblClients() {
		return lblClients;
	}

	public JLabel getLblBooks() {
		return lblBooks;
	}

	public JLabel getLblInventoryNumber() {
		return lblInventoryNumber;
	}
}

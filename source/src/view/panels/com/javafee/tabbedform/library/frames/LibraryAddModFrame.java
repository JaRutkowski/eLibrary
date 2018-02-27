package com.javafee.tabbedform.library.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.model.BookTableModel;
import com.javafee.uniform.CockpitConfirmationPanel;

import net.coderazzi.filters.gui.TableFilterHeader;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LibraryAddModFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTable bookTable;
	private JScrollPane scrollPane;
	private CockpitConfirmationPanel cockpitConfirmationPanel;
	private JLabel lblInventoryNumber;
	private JTextField textFieldInventoryNumber;

	public LibraryAddModFrame() {
		setBackground(Utils.getApplicationColor());
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(LibraryAddModFrame.class.getResource("/images/splashScreen.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 517, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 97, 345, 0 };
		gbl_contentPane.rowHeights = new int[] { 317, 0, 22, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		bookTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(bookTable);
		bookTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setModel(new BookTableModel());
		bookTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(bookTable);
		
		lblInventoryNumber = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("libraryAddModFrame.lblInventoryNumber"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblInventoryNumber, gbc_lblNewLabel);
		
		textFieldInventoryNumber = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		contentPane.add(textFieldInventoryNumber, gbc_textField);
		textFieldInventoryNumber.setColumns(10);
		
		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_cockpitConfirmationPanel = new GridBagConstraints();
		gbc_cockpitConfirmationPanel.anchor = GridBagConstraints.SOUTH;
		gbc_cockpitConfirmationPanel.gridwidth = 2;
		gbc_cockpitConfirmationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_cockpitConfirmationPanel.gridx = 0;
		gbc_cockpitConfirmationPanel.gridy = 2;
		contentPane.add(cockpitConfirmationPanel, gbc_cockpitConfirmationPanel);
	}
	
	public JTable getBookTable() {
		return bookTable;
	}
	
	public JTextField getTextFieldInventoryNumber() {
		return textFieldInventoryNumber;
	}
	
	public CockpitConfirmationPanel getCockpitConfirmationPanel() {
		return cockpitConfirmationPanel;
	}
}

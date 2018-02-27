package com.javafee.tabbedform.loanservice;

import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.model.BookTableModel;
import com.javafee.model.ClientTableModel;
import com.javafee.model.LoanReservationTableModel;
import com.javafee.model.LoanTableModel;
import com.javafee.model.RegisteredClientTableModel;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.startform.RegistrationPanel;
import com.javafee.tabbedform.library.BookFilterPanel;

import net.coderazzi.filters.gui.TableFilterHeader;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.javafee.uniform.CockpitEditionPanel;

public class LoanServicePanel_new extends JPanel {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneClient;
	private JTable clientTable;
	private JTable volumeLoanTable;
	private JScrollPane scrollPane_loanTable;
	private JTable loanTable;
	private JPanel panel;
	private JScrollPane scrollPane_reservationTable;
	private JTable reservationTable;
	private JButton btnLoan;
	private JButton btnProlongation;
	private JButton btnReturn;
	private JButton btnReservation;
	private JButton btnPenalty;
	private JButton btnA;
	private JPanel loanPanel;
	private JScrollPane scrollPane_loanTable_1;
	private JPanel reservationPanel;
	private JPanel volumePanel;
	private JButton btnCancelReservation;

	public LoanServicePanel_new() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 751, 64, 0, 165, 0 };
		gridBagLayout.rowHeights = new int[] { 189, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panel = new JPanel();
		panel.setBackground(Utils.getApplicationColor());
		panel.setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("loanTablePanel.tableClientTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 425, 0 };
		gbl_panel.rowHeights = new int[] { 189, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		scrollPaneClient = new JScrollPane();
		GridBagConstraints gbc_scrollPaneClient = new GridBagConstraints();
		gbc_scrollPaneClient.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneClient.gridx = 0;
		gbc_scrollPaneClient.gridy = 0;
		panel.add(scrollPaneClient, gbc_scrollPaneClient);

		clientTable = new JTable();
		scrollPaneClient.setViewportView(clientTable);
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(clientTable);
		clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clientTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		clientTable.setModel(new RegisteredClientTableModel());
		clientTable.setAutoCreateRowSorter(true);
		scrollPaneClient.setViewportView(clientTable);
		
		volumePanel = new JPanel();
		volumePanel.setBackground(Utils.getApplicationColor());
		volumePanel.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.volumePanelTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_volumePanel = new GridBagConstraints();
		gbc_volumePanel.fill = GridBagConstraints.BOTH;
		gbc_volumePanel.gridwidth = 3;
		gbc_volumePanel.insets = new Insets(0, 0, 5, 0);
		gbc_volumePanel.gridx = 1;
		gbc_volumePanel.gridy = 0;
		add(volumePanel, gbc_volumePanel);
		GridBagLayout gbl_volumePanel = new GridBagLayout();
		gbl_volumePanel.columnWidths = new int[]{64, 0, 0};
		gbl_volumePanel.rowHeights = new int[]{189, 0};
		gbl_volumePanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_volumePanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		volumePanel.setLayout(gbl_volumePanel);

		JScrollPane scrollPane_loanTable_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_loanTable_2 = new GridBagConstraints();
		gbc_scrollPane_loanTable_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_loanTable_2.gridwidth = 2;
		gbc_scrollPane_loanTable_2.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_loanTable_2.gridx = 0;
		gbc_scrollPane_loanTable_2.gridy = 0;
		volumePanel.add(scrollPane_loanTable_2, gbc_scrollPane_loanTable_2);

		volumeLoanTable = new JTable();
		scrollPane_loanTable_2.setViewportView(volumeLoanTable);
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader_volumeLoanTable = new TableFilterHeader(volumeLoanTable);
		volumeLoanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		volumeLoanTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		volumeLoanTable.setModel(new VolumeTableModel());
		volumeLoanTable.setAutoCreateRowSorter(true);
		scrollPane_loanTable_2.setViewportView(volumeLoanTable);
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader_loanTable = new TableFilterHeader(volumeLoanTable);
		
		btnLoan = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnLoan"));
		btnLoan.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddToList-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnLoan = new GridBagConstraints();
		gbc_btnLoan.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLoan.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoan.gridx = 1;
		gbc_btnLoan.gridy = 1;
		add(btnLoan, gbc_btnLoan);
		
		loanPanel = new JPanel();
		loanPanel.setBackground(Utils.getApplicationColor());
		loanPanel.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanPanelTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_loanPanel = new GridBagConstraints();
		gbc_loanPanel.fill = GridBagConstraints.BOTH;
		gbc_loanPanel.gridwidth = 4;
		gbc_loanPanel.insets = new Insets(0, 0, 5, 0);
		gbc_loanPanel.gridx = 0;
		gbc_loanPanel.gridy = 2;
		add(loanPanel, gbc_loanPanel);
		GridBagLayout gbl_loanPanel = new GridBagLayout();
		gbl_loanPanel.columnWidths = new int[]{751, 64, 0, 165, 95, 0};
		gbl_loanPanel.rowHeights = new int[]{0, 0};
		gbl_loanPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_loanPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		loanPanel.setLayout(gbl_loanPanel);

		scrollPane_loanTable_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_loanTable_1 = new GridBagConstraints();
		gbc_scrollPane_loanTable_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_loanTable_1.gridwidth = 5;
		gbc_scrollPane_loanTable_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_loanTable_1.gridx = 0;
		gbc_scrollPane_loanTable_1.gridy = 0;
		loanPanel.add(scrollPane_loanTable_1, gbc_scrollPane_loanTable_1);

		loanTable = new JTable();
		scrollPane_loanTable_1.setViewportView(loanTable);
		loanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loanTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		loanTable.setModel(new LoanTableModel());
		loanTable.setAutoCreateRowSorter(true);
		scrollPane_loanTable_1.setViewportView(loanTable);
		scrollPane_loanTable_1.setViewportView(loanTable);
		
		btnReservation = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnReservation"));
		btnReservation.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAdd-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnReservation = new GridBagConstraints();
		gbc_btnReservation.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnReservation.insets = new Insets(0, 0, 5, 5);
		gbc_btnReservation.gridx = 0;
		gbc_btnReservation.gridy = 3;
		add(btnReservation, gbc_btnReservation);
		
		btnProlongation = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnProlongation"));
		btnProlongation.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnReload-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnProlongation = new GridBagConstraints();
		gbc_btnProlongation.anchor = GridBagConstraints.NORTH;
		gbc_btnProlongation.insets = new Insets(0, 0, 5, 5);
		gbc_btnProlongation.gridx = 1;
		gbc_btnProlongation.gridy = 3;
		add(btnProlongation, gbc_btnProlongation);
		
		btnReturn = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnReturn"));
		btnReturn.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnClear-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.anchor = GridBagConstraints.NORTH;
		gbc_btnReturn.insets = new Insets(0, 0, 5, 5);
		gbc_btnReturn.gridx = 2;
		gbc_btnReturn.gridy = 3;
		add(btnReturn, gbc_btnReturn);
		
		btnPenalty = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnPenalty"));
		btnPenalty.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAdd-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnPenalty = new GridBagConstraints();
		gbc_btnPenalty.anchor = GridBagConstraints.NORTH;
		gbc_btnPenalty.insets = new Insets(0, 0, 5, 0);
		gbc_btnPenalty.gridx = 3;
		gbc_btnPenalty.gridy = 3;
		add(btnPenalty, gbc_btnPenalty);
		
		reservationPanel = new JPanel();
		reservationPanel.setBackground(Utils.getApplicationColor());
		reservationPanel.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.reservationPanelTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reservationPanel = new GridBagConstraints();
		gbc_reservationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_reservationPanel.fill = GridBagConstraints.BOTH;
		gbc_reservationPanel.gridwidth = 4;
		gbc_reservationPanel.gridx = 0;
		gbc_reservationPanel.gridy = 4;
		add(reservationPanel, gbc_reservationPanel);
		GridBagLayout gbl_reservationPanel = new GridBagLayout();
		gbl_reservationPanel.columnWidths = new int[]{751, 64, 0, 165, 0};
		gbl_reservationPanel.rowHeights = new int[]{0, 0};
		gbl_reservationPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_reservationPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		reservationPanel.setLayout(gbl_reservationPanel);

		scrollPane_reservationTable = new JScrollPane();
		GridBagConstraints gbc_scrollPane_reservationTable = new GridBagConstraints();
		gbc_scrollPane_reservationTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_reservationTable.gridwidth = 4;
		gbc_scrollPane_reservationTable.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_reservationTable.gridx = 0;
		gbc_scrollPane_reservationTable.gridy = 0;
		reservationPanel.add(scrollPane_reservationTable, gbc_scrollPane_reservationTable);

		reservationTable = new JTable();
		scrollPane_reservationTable.setViewportView(reservationTable);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reservationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		reservationTable.setModel(new LoanReservationTableModel()); //TODO Change to ReservationTableModel()
		reservationTable.setAutoCreateRowSorter(true);
		
		btnCancelReservation = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnCancelReservation"));
		btnCancelReservation.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnDelete-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnCancelReservation = new GridBagConstraints();
		gbc_btnCancelReservation.gridx = 3;
		gbc_btnCancelReservation.gridy = 5;
		add(btnCancelReservation, gbc_btnCancelReservation);
	}

	public JScrollPane getScrollPaneClient() {
		return scrollPaneClient;
	}

	public void setScrollPaneClient(JScrollPane scrollPaneClient) {
		this.scrollPaneClient = scrollPaneClient;
	}

	public JTable getClientTable() {
		return clientTable;
	}

	public void setClientTable(JTable clientTable) {
		this.clientTable = clientTable;
	}

	public JTable getVolumeLoanTable() {
		return volumeLoanTable;
	}

	public void setVolumeLoanTable(JTable volumeLoanTable) {
		this.volumeLoanTable = volumeLoanTable;
	}

	public JScrollPane getScrollPane_loanTable() {
		return scrollPane_loanTable;
	}

	public void setScrollPane_loanTable(JScrollPane scrollPane_loanTable) {
		this.scrollPane_loanTable = scrollPane_loanTable;
	}

	public JTable getLoanTable() {
		return loanTable;
	}

	public void setLoanTable(JTable loanTable) {
		this.loanTable = loanTable;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JScrollPane getScrollPane_reservationTable() {
		return scrollPane_reservationTable;
	}

	public void setScrollPane_reservationTable(JScrollPane scrollPane_reservationTable) {
		this.scrollPane_reservationTable = scrollPane_reservationTable;
	}

	public JTable getReservationTable() {
		return reservationTable;
	}

	public void setReservationTable(JTable reservationTable) {
		this.reservationTable = reservationTable;
	}

	public JButton getBtnLoan() {
		return btnLoan;
	}

	public void setBtnLoan(JButton btnLoan) {
		this.btnLoan = btnLoan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getBtnProlongation() {
		return btnProlongation;
	}

	public void setBtnProlongation(JButton btnProlongation) {
		this.btnProlongation = btnProlongation;
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public JButton getBtnReservation() {
		return btnReservation;
	}

	public void setBtnReservation(JButton btnReservation) {
		this.btnReservation = btnReservation;
	}

	public JButton getBtnPenalty() {
		return btnPenalty;
	}

	public void setBtnPenalty(JButton btnPenalty) {
		this.btnPenalty = btnPenalty;
	}
	
	public JButton getBtnCancelReservation() {
		return btnCancelReservation;
	}
}

package com.javafee.tabbedform.library;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.model.VolumeTableReadingRoomModel;
import com.javafee.uniform.CockpitEditionPanel;

import net.coderazzi.filters.gui.TableFilterHeader;

public class LibraryTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable loanVolumeTable;
	private JTable readingRoomVolumeTable;
	private JScrollPane scrollPane_readingRoom;
	private CockpitEditionPanel cockpitEditionPanelLoan;
	private CockpitEditionPanel cockpitEditionPanelReadingRoom;
	private JPanel panelLoan;
	private JPanel panelReadingRoom;

	public LibraryTablePanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 477, 0 };
		gridBagLayout.rowHeights = new int[] { 284, 0, 312, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		panelLoan = new JPanel();
		panelLoan.setBackground(Utils.getApplicationColor());
		panelLoan.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelLoanTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelLoan = new GridBagConstraints();
		gbc_panelLoan.fill = GridBagConstraints.BOTH;
		gbc_panelLoan.insets = new Insets(0, 0, 5, 0);
		gbc_panelLoan.gridx = 0;
		gbc_panelLoan.gridy = 0;
		add(panelLoan, gbc_panelLoan);
		GridBagLayout gbl_panelLoan = new GridBagLayout();
		gbl_panelLoan.columnWidths = new int[]{477, 0};
		gbl_panelLoan.rowHeights = new int[]{284, 0};
		gbl_panelLoan.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelLoan.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLoan.setLayout(gbl_panelLoan);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelLoan.add(scrollPane, gbc_scrollPane);
		
		loanVolumeTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(loanVolumeTable);
		loanVolumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loanVolumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		loanVolumeTable.setModel(new VolumeTableLoanModel());
		loanVolumeTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(loanVolumeTable);
		
		cockpitEditionPanelLoan = new CockpitEditionPanel();
		GridBagConstraints gbc_ce_panelLoan = new GridBagConstraints();
		gbc_ce_panelLoan.anchor = GridBagConstraints.WEST;
		gbc_ce_panelLoan.insets = new Insets(0, 0, 5, 0);
		gbc_ce_panelLoan.fill = GridBagConstraints.VERTICAL;
		gbc_ce_panelLoan.gridx = 0;
		gbc_ce_panelLoan.gridy = 1;
		add(cockpitEditionPanelLoan, gbc_ce_panelLoan);
		
		panelReadingRoom = new JPanel();
		panelReadingRoom.setBackground(Utils.getApplicationColor());
		panelReadingRoom.setBorder(new TitledBorder(null, SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelReadingRoomTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelReadingRoom = new GridBagConstraints();
		gbc_panelReadingRoom.fill = GridBagConstraints.BOTH;
		gbc_panelReadingRoom.insets = new Insets(0, 0, 5, 0);
		gbc_panelReadingRoom.gridx = 0;
		gbc_panelReadingRoom.gridy = 2;
		add(panelReadingRoom, gbc_panelReadingRoom);
		GridBagLayout gbl_panelReadingRoom = new GridBagLayout();
		gbl_panelReadingRoom.columnWidths = new int[]{477, 0};
		gbl_panelReadingRoom.rowHeights = new int[]{312, 0};
		gbl_panelReadingRoom.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelReadingRoom.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelReadingRoom.setLayout(gbl_panelReadingRoom);
		
		scrollPane_readingRoom = new JScrollPane();
		GridBagConstraints gbc_scrollPane_readingRoom = new GridBagConstraints();
		gbc_scrollPane_readingRoom.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_readingRoom.gridx = 0;
		gbc_scrollPane_readingRoom.gridy = 0;
		panelReadingRoom.add(scrollPane_readingRoom, gbc_scrollPane_readingRoom);
		
		readingRoomVolumeTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader_readingRoom = new TableFilterHeader(readingRoomVolumeTable);
		readingRoomVolumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		readingRoomVolumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		readingRoomVolumeTable.setModel(new VolumeTableReadingRoomModel());
		readingRoomVolumeTable.setAutoCreateRowSorter(true);
		scrollPane_readingRoom.setViewportView(readingRoomVolumeTable);
		
		cockpitEditionPanelReadingRoom = new CockpitEditionPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		add(cockpitEditionPanelReadingRoom, gbc_panel_1);
	}
	
	public JTable getLoanVolumeTable() {
		return loanVolumeTable;
	}
	
	public void setLoanVolumeTable(JTable loanVolumeTable) {
		this.loanVolumeTable = loanVolumeTable;
	}
	
	public JTable getReadingRoomBookTable() {
		return readingRoomVolumeTable;
	}
	
	public void setReadingRoomVolumeTable(JTable readingRoomVolumeTable) {
		this.readingRoomVolumeTable = readingRoomVolumeTable;
	}
	
	public CockpitEditionPanel getCockpitEditionPanelLoan() {
		return cockpitEditionPanelLoan;
	}
	
	public CockpitEditionPanel getCockpitEditionPanelReadingRoom() {
		return cockpitEditionPanelReadingRoom;
	}
}

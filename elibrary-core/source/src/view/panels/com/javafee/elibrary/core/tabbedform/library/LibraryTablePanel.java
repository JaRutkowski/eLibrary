package com.javafee.elibrary.core.tabbedform.library;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.model.VolumeLoanTableModel;
import com.javafee.elibrary.core.model.VolumeReadingRoomTableModel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class LibraryTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable loanVolumeTable;
	@Getter
	private JTable readingRoomVolumeTable;
	private JScrollPane scrollPane_readingRoom;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelLoan;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelReadingRoom;
	@Getter
	private JPanel panelLoan;
	@Getter
	private JPanel panelReadingRoom;

	public LibraryTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{477, 0};
		gridBagLayout.rowHeights = new int[]{284, 0, 312, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		panelLoan = new JPanel();
		panelLoan.setBackground(Utils.getApplicationUserDefinedColor());
		panelLoan.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelLoanTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
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

		loanVolumeTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(loanVolumeTable);
		loanVolumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loanVolumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		loanVolumeTable.setModel(new VolumeLoanTableModel());
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
		panelReadingRoom.setBackground(Utils.getApplicationUserDefinedColor());
		panelReadingRoom.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelReadingRoomTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
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

		readingRoomVolumeTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader_readingRoom = new CustomTableFilterHeader(readingRoomVolumeTable);
		readingRoomVolumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		readingRoomVolumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		readingRoomVolumeTable.setModel(new VolumeReadingRoomTableModel());
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
}

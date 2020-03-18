package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.model.SystemDataFeedingTableModel;
import com.javafee.elibrary.core.settingsform.utils.SystemDataFeedingTableCellRenderer;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.ActionJTable;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class SystemDataFeedingTablePanel extends BasePanel {
	@Getter
	private ActionJTable systemDataFeedingTable;

	public SystemDataFeedingTablePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{538, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		systemDataFeedingTable = new ActionJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(systemDataFeedingTable);
		systemDataFeedingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		systemDataFeedingTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		systemDataFeedingTable.setModel(new SystemDataFeedingTableModel());
		systemDataFeedingTable.setAutoCreateRowSorter(true);
		TableColumn column = systemDataFeedingTable.getColumnModel().getColumn(Constants.SystemDataFeedingTableColumn.COL_DATA.getValue());
		column.setCellRenderer(new SystemDataFeedingTableCellRenderer());
		scrollPane.setViewportView(systemDataFeedingTable);
	}
}

package com.javafee.elibrary.core.tabbedform.books;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class BookTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable bookTable;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelBook;

	public BookTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{477, 0};
		gridBagLayout.rowHeights = new int[]{284, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		bookTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(bookTable);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setModel(new BookTableModel());
		bookTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(bookTable);

		cockpitEditionPanelBook = new CockpitEditionPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(cockpitEditionPanelBook, gbc_panel);
	}
}

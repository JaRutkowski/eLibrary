package com.javafee.elibrary.core.tabbedform.books;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;
import com.javafee.elibrary.core.uniform.ImagePanel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class BookTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable bookTable;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelBook;
	@Getter
	private ImagePanel bookImagePreviewPanel;
	private JSplitPane splitPane;

	public BookTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{477, 0};
		gridBagLayout.rowHeights = new int[]{284, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		add(splitPane, gbc_splitPane);

		bookImagePreviewPanel = new ImagePanel(SystemProperties.getInstance().getResourceBundle()
				.getString("imagePanel.title"));
		splitPane.setRightComponent(bookImagePreviewPanel);
		bookImagePreviewPanel.setPreferredSize(new Dimension(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		bookTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(bookTable);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setModel(new BookTableModel());
		bookTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(bookTable);

		cockpitEditionPanelBook = new CockpitEditionPanel();
		GridBagConstraints gbc_cockpitEditionPanelBook = new GridBagConstraints();
		gbc_cockpitEditionPanelBook.anchor = GridBagConstraints.WEST;
		gbc_cockpitEditionPanelBook.fill = GridBagConstraints.VERTICAL;
		gbc_cockpitEditionPanelBook.gridx = 0;
		gbc_cockpitEditionPanelBook.gridy = 1;
		add(cockpitEditionPanelBook, gbc_cockpitEditionPanelBook);
	}
}

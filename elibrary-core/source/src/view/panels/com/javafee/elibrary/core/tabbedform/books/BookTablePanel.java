package com.javafee.elibrary.core.tabbedform.books;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;
import com.javafee.elibrary.core.uniform.ImagePanel;

import lombok.Getter;

public class BookTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private ImportExportableJTable bookTable;
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

		bookTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new BookTableModel(),
				true);
		bookTable.setActions(new BtnImportAction(bookTable), new BtnExportAction(bookTable));
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

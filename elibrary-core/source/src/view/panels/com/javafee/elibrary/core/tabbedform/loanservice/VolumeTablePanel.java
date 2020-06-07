package com.javafee.elibrary.core.tabbedform.loanservice;

import java.awt.*;

import javax.swing.JScrollPane;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.model.VolumeTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;

import lombok.Getter;

public class VolumeTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private ImportExportableJTable volumeTable;

	public VolumeTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 0};
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

		volumeTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new VolumeTableModel(),
				true);
		volumeTable.setActions(new BtnImportAction(volumeTable), new BtnExportAction(volumeTable));
		scrollPane.setViewportView(volumeTable);
	}
}

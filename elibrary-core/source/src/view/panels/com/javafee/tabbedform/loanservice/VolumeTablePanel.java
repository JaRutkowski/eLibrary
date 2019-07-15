package com.javafee.tabbedform.loanservice;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.javafee.common.BasePanel;
import com.javafee.common.Utils;
import com.javafee.model.VolumeTableModel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class VolumeTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable volumeTable;

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

		volumeTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(volumeTable);
		volumeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		volumeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		volumeTable.setModel(new VolumeTableModel());
		volumeTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(volumeTable);
	}
}

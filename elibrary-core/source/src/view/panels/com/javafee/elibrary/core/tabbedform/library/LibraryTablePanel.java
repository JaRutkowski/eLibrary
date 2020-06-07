package com.javafee.elibrary.core.tabbedform.library;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.model.VolumeLoanTableModel;
import com.javafee.elibrary.core.model.VolumeReadingRoomTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;
import com.javafee.elibrary.core.uniform.ImagePanel;

import lombok.Getter;

public class LibraryTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private ImportExportableJTable loanVolumeTable;
	@Getter
	private ImportExportableJTable readingRoomVolumeTable;
	private JScrollPane scrollPane_readingRoom;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelLoan;
	@Getter
	private CockpitEditionPanel cockpitEditionPanelReadingRoom;
	@Getter
	private JPanel panelLoan;
	@Getter
	private JPanel panelReadingRoom;
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	@Getter
	private ImagePanel imagePreviewPanelLoan;
	@Getter
	private ImagePanel imagePreviewPanelReadingRoom;

	public LibraryTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{477, 0};
		gridBagLayout.rowHeights = new int[]{284, 0, 312, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
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

		imagePreviewPanelLoan = new ImagePanel(SystemProperties.getInstance().getResourceBundle()
				.getString("imagePanel.title"));
		splitPane.setRightComponent(imagePreviewPanelLoan);

		panelLoan = new JPanel();
		splitPane.setLeftComponent(panelLoan);
		panelLoan.setBackground(Utils.getApplicationUserDefinedColor());
		panelLoan.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelLoanTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
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

		loanVolumeTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new VolumeLoanTableModel(),
				true);
		loanVolumeTable.setActions(new BtnImportAction(loanVolumeTable), new BtnExportAction(loanVolumeTable));
		scrollPane.setViewportView(loanVolumeTable);

		cockpitEditionPanelLoan = new CockpitEditionPanel();
		GridBagConstraints gbc_ce_panelLoan = new GridBagConstraints();
		gbc_ce_panelLoan.anchor = GridBagConstraints.WEST;
		gbc_ce_panelLoan.insets = new Insets(0, 0, 5, 0);
		gbc_ce_panelLoan.fill = GridBagConstraints.VERTICAL;
		gbc_ce_panelLoan.gridx = 0;
		gbc_ce_panelLoan.gridy = 1;
		add(cockpitEditionPanelLoan, gbc_ce_panelLoan);

		splitPane_1 = new JSplitPane();
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setResizeWeight(0.5);
		GridBagConstraints gbc_splitPane_1 = new GridBagConstraints();
		gbc_splitPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane_1.fill = GridBagConstraints.BOTH;
		gbc_splitPane_1.gridx = 0;
		gbc_splitPane_1.gridy = 2;
		add(splitPane_1, gbc_splitPane_1);

		imagePreviewPanelReadingRoom = new ImagePanel(SystemProperties.getInstance().getResourceBundle()
				.getString("imagePanel.title"));
		splitPane_1.setRightComponent(imagePreviewPanelReadingRoom);

		panelReadingRoom = new JPanel();
		splitPane_1.setLeftComponent(panelReadingRoom);
		panelReadingRoom.setBackground(Utils.getApplicationUserDefinedColor());
		panelReadingRoom.setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("libraryTablePanel.panelReadingRoomTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
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

		readingRoomVolumeTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new VolumeReadingRoomTableModel(),
				true);
		readingRoomVolumeTable.setActions(new BtnImportAction(readingRoomVolumeTable), new BtnExportAction(readingRoomVolumeTable));
		scrollPane_readingRoom.setViewportView(readingRoomVolumeTable);

		cockpitEditionPanelReadingRoom = new CockpitEditionPanel();
		GridBagConstraints gbc_cockpitEditionPanelReadingRoom = new GridBagConstraints();
		gbc_cockpitEditionPanelReadingRoom.anchor = GridBagConstraints.WEST;
		gbc_cockpitEditionPanelReadingRoom.fill = GridBagConstraints.VERTICAL;
		gbc_cockpitEditionPanelReadingRoom.gridx = 0;
		gbc_cockpitEditionPanelReadingRoom.gridy = 3;
		add(cockpitEditionPanelReadingRoom, gbc_cockpitEditionPanelReadingRoom);
	}
}

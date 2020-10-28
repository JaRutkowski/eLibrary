package com.javafee.elibrary.core.tabbedform.admworkers;

import java.awt.*;

import javax.swing.JScrollPane;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.model.WorkerTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import com.javafee.elibrary.core.uniform.AdmBlockPanel;
import com.javafee.elibrary.core.uniform.AdmIsAccountantPanel;
import com.javafee.elibrary.core.uniform.AdmIsRegisteredPanel;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;

import lombok.Getter;

public class WorkerTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private ImportExportableJTable workerTable;

	@Getter
	private WorkerDataPanel workerDataPanel;
	@Getter
	private CockpitEditionPanel cockpitEditionPanel;
	@Getter
	private AdmIsRegisteredPanel admIsRegisteredPanel;
	@Getter
	private AdmIsAccountantPanel admIsAccountantPanel;
	@Getter
	private AdmBlockPanel admBlockPanel;

	public WorkerTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{394, 413, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 275, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		workerTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new WorkerTableModel(),
				true);
		workerTable.setActions(new BtnImportAction(workerTable), new BtnExportAction(workerTable));
		scrollPane.setViewportView(workerTable);

		admIsAccountantPanel = new AdmIsAccountantPanel();
		GridBagConstraints gbc_admIsAccountantPanel = new GridBagConstraints();
		gbc_admIsAccountantPanel.insets = new Insets(0, 0, 5, 5);
		gbc_admIsAccountantPanel.fill = GridBagConstraints.BOTH;
		gbc_admIsAccountantPanel.gridx = 0;
		gbc_admIsAccountantPanel.gridy = 2;
		add(admIsAccountantPanel, gbc_admIsAccountantPanel);

		admIsRegisteredPanel = new AdmIsRegisteredPanel();
		GridBagConstraints gbc_admIsRegisteredPanel = new GridBagConstraints();
		gbc_admIsRegisteredPanel.insets = new Insets(0, 0, 5, 5);
		gbc_admIsRegisteredPanel.fill = GridBagConstraints.BOTH;
		gbc_admIsRegisteredPanel.gridx = 1;
		gbc_admIsRegisteredPanel.gridy = 2;
		add(admIsRegisteredPanel, gbc_admIsRegisteredPanel);

		admBlockPanel = new AdmBlockPanel();
		GridBagConstraints gbc_admBlockedPanel = new GridBagConstraints();
		gbc_admBlockedPanel.insets = new Insets(0, 0, 5, 0);
		gbc_admBlockedPanel.fill = GridBagConstraints.BOTH;
		gbc_admBlockedPanel.gridx = 2;
		gbc_admBlockedPanel.gridy = 2;
		add(admBlockPanel, gbc_admBlockedPanel);

		cockpitEditionPanel = new CockpitEditionPanel();
		GridBagConstraints gbc_cockpitEditionPanel = new GridBagConstraints();
		gbc_cockpitEditionPanel.gridwidth = 3;
		gbc_cockpitEditionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_cockpitEditionPanel.anchor = GridBagConstraints.NORTH;
		gbc_cockpitEditionPanel.gridx = 0;
		gbc_cockpitEditionPanel.gridy = 3;
		add(cockpitEditionPanel, gbc_cockpitEditionPanel);
	}
}

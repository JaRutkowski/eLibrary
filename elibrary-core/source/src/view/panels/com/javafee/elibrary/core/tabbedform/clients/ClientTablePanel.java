package com.javafee.elibrary.core.tabbedform.clients;

import java.awt.*;

import javax.swing.JScrollPane;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.model.ClientTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import com.javafee.elibrary.core.uniform.AdmIsRegisteredPanel;
import com.javafee.elibrary.core.uniform.CockpitEditionPanel;
import com.javafee.elibrary.core.uniform.MessageAndAlertPanel;

import lombok.Getter;

public class ClientTablePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private ImportExportableJTable clientTable;
	@Getter
	private CockpitEditionPanel cockpitEditionPanel;
	@Getter
	private AdmIsRegisteredPanel admIsRegisteredPanel;
	@Getter
	private MessageAndAlertPanel messageAndAlertPanel;

	public ClientTablePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{417, 16, 0};
		gridBagLayout.rowHeights = new int[]{0, 76, 38, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		clientTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
				new ClientTableModel(),
				true);
		clientTable.setActions(new BtnImportAction(clientTable), new BtnExportAction(clientTable));
		scrollPane.setViewportView(clientTable);

		admIsRegisteredPanel = new AdmIsRegisteredPanel();
		admIsRegisteredPanel.setEnabled(false);
		admIsRegisteredPanel.setVisible(false);
		GridBagConstraints gbc_admIsRegisteredPanel = new GridBagConstraints();
		gbc_admIsRegisteredPanel.gridwidth = 2;
		gbc_admIsRegisteredPanel.insets = new Insets(0, 0, 5, 0);
		gbc_admIsRegisteredPanel.fill = GridBagConstraints.BOTH;
		gbc_admIsRegisteredPanel.gridx = 0;
		gbc_admIsRegisteredPanel.gridy = 1;
		add(admIsRegisteredPanel, gbc_admIsRegisteredPanel);

		cockpitEditionPanel = new CockpitEditionPanel();
		GridBagConstraints gbc_alertAndMessagePanel = new GridBagConstraints();
		gbc_alertAndMessagePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_alertAndMessagePanel.insets = new Insets(10, 0, 0, 5);
		gbc_alertAndMessagePanel.gridx = 0;
		gbc_alertAndMessagePanel.gridy = 2;
		add(cockpitEditionPanel, gbc_alertAndMessagePanel);

		messageAndAlertPanel = new MessageAndAlertPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(messageAndAlertPanel, gbc_panel);

	}
}

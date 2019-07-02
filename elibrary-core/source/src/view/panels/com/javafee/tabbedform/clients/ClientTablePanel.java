package com.javafee.tabbedform.clients;

import com.javafee.common.Utils;
import com.javafee.model.ClientTableModel;
import com.javafee.uniform.AdmIsRegisteredPanel;
import com.javafee.uniform.CockpitEditionPanel;
import com.javafee.uniform.MessageAndAlertPanel;
import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import java.awt.*;

public class ClientTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable clientTable;
	@Getter
	private CockpitEditionPanel cockpitEditionPanel;
	@Getter
	private AdmIsRegisteredPanel admIsRegisteredPanel;
	@Getter
	private MessageAndAlertPanel messageAndAlertPanel;

	public ClientTablePanel() {
		setBackground(Utils.getApplicationColor());
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

		clientTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(clientTable);
		clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clientTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		clientTable.setModel(new ClientTableModel());
		clientTable.setAutoCreateRowSorter(true);
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

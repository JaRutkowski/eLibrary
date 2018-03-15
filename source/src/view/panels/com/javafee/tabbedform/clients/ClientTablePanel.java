package com.javafee.tabbedform.clients;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.javafee.common.Utils;
import com.javafee.model.ClientTableModel;
import com.javafee.uniform.AdmIsRegisteredPanel;
import com.javafee.uniform.CockpitEditionPanel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;
import javax.swing.JButton;

public class ClientTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JTable clientTable;
	@Getter
	private CockpitEditionPanel cockpitEditionPanel;
	@Getter
	private AdmIsRegisteredPanel admIsRegisteredPanel;
	@Getter
	private JButton btnContact;

	public ClientTablePanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 843, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 76, 38, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(cockpitEditionPanel, gbc_panel);
		
		btnContact = new JButton("Contact");
		GridBagConstraints gbc_btnContact = new GridBagConstraints();
		gbc_btnContact.fill = GridBagConstraints.VERTICAL;
		gbc_btnContact.insets = new Insets(0, 0, 5, 0);
		gbc_btnContact.gridx = 1;
		gbc_btnContact.gridy = 2;
		add(btnContact, gbc_btnContact);
	}
}

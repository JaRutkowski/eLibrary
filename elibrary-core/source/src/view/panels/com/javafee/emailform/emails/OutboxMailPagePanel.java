package com.javafee.emailform.emails;

import com.javafee.common.SystemProperties;

import com.javafee.model.OutboxTableModel;
import com.javafee.hibernate.dto.common.UserData;
import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class OutboxMailPagePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JCheckBox chckbxShowOnlyMyCorrespondence;
	@Getter
	private OutboxNavigationPanel outboxNavigationPanel;
	@Getter
	private JTable outboxTable;
	@Getter
	private JCheckBox checkShowOnlySystemCorrespondence;
	private JLabel lblRecipient;
	@Getter
	private JComboBox<UserData> comboBoxRecipient;

	public OutboxMailPagePanel() {
		setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("outboxMailPanel.outboxMailPanelBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		checkShowOnlySystemCorrespondence = new JCheckBox(SystemProperties.getInstance().getResourceBundle()
				.getString("outboxMailPanel.chckbxShowOnlySystemCorrespondence"));
		GridBagConstraints gbc_checkShowOnlySystemCorrespondence = new GridBagConstraints();
		gbc_checkShowOnlySystemCorrespondence.anchor = GridBagConstraints.WEST;
		gbc_checkShowOnlySystemCorrespondence.insets = new Insets(0, 0, 5, 5);
		gbc_checkShowOnlySystemCorrespondence.gridx = 0;
		gbc_checkShowOnlySystemCorrespondence.gridy = 0;
		add(checkShowOnlySystemCorrespondence, gbc_checkShowOnlySystemCorrespondence);

		lblRecipient = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("outboxMailPanel.lblRecipient"));
		GridBagConstraints gbc_lblRecipient = new GridBagConstraints();
		gbc_lblRecipient.anchor = GridBagConstraints.EAST;
		gbc_lblRecipient.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecipient.gridx = 1;
		gbc_lblRecipient.gridy = 0;
		add(lblRecipient, gbc_lblRecipient);

		comboBoxRecipient = new JComboBox<UserData>();
		GridBagConstraints gbc_comboBoxRecipient = new GridBagConstraints();
		gbc_comboBoxRecipient.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxRecipient.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRecipient.gridx = 2;
		gbc_comboBoxRecipient.gridy = 0;
		add(comboBoxRecipient, gbc_comboBoxRecipient);

		chckbxShowOnlyMyCorrespondence = new JCheckBox(SystemProperties.getInstance().getResourceBundle()
				.getString("outboxMailPanel.chckbxShowOnlyMyCorrespondence"));
		chckbxShowOnlyMyCorrespondence.setVisible(false);
		GridBagConstraints gbc_chckbxShowOnlyMyCorrespondence = new GridBagConstraints();
		gbc_chckbxShowOnlyMyCorrespondence.anchor = GridBagConstraints.WEST;
		gbc_chckbxShowOnlyMyCorrespondence.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowOnlyMyCorrespondence.gridx = 0;
		gbc_chckbxShowOnlyMyCorrespondence.gridy = 1;
		add(chckbxShowOnlyMyCorrespondence, gbc_chckbxShowOnlyMyCorrespondence);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);

		outboxTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(outboxTable);
		outboxTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outboxTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		outboxTable.setModel(new OutboxTableModel());
		outboxTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(outboxTable);

		outboxNavigationPanel = new OutboxNavigationPanel();
		GridBagConstraints gbc_outboxNavigationPanel = new GridBagConstraints();
		gbc_outboxNavigationPanel.anchor = GridBagConstraints.SOUTH;
		gbc_outboxNavigationPanel.gridwidth = 3;
		gbc_outboxNavigationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_outboxNavigationPanel.gridx = 0;
		gbc_outboxNavigationPanel.gridy = 3;
		add(outboxNavigationPanel, gbc_outboxNavigationPanel);
	}
}

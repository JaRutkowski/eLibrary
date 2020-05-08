package com.javafee.elibrary.core.tabbedform.clientreservations;

import java.awt.*;

import javax.swing.JTabbedPane;

import com.javafee.elibrary.core.common.BasePanel;

import lombok.Getter;

public class ClientReservationPanel extends BasePanel {
	@Getter
	private JTabbedPane tabbedPane;

	@Getter
	private CreateReservationPanel createReservationPanel;
	@Getter
	private BrowseReservationPanel browseReservationPanel;

	public ClientReservationPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);

		createReservationPanel = new CreateReservationPanel();
		browseReservationPanel = new BrowseReservationPanel();
	}
}

package com.javafee.elibrary.core.tabbedform.clientreservations;

import java.awt.*;

import com.javafee.elibrary.core.common.BasePanel;

import lombok.Getter;

public class BrowseReservationPanel extends BasePanel {
	@Getter
	private ActiveClientReservationPanel activeClientReservationPanel;
	@Getter
	private HistoryClientReservationPanel historyClientReservationPanel;

	public BrowseReservationPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		activeClientReservationPanel = new ActiveClientReservationPanel();
		GridBagConstraints gbc_activeClientReservationPanel = new GridBagConstraints();
		gbc_activeClientReservationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_activeClientReservationPanel.fill = GridBagConstraints.BOTH;
		gbc_activeClientReservationPanel.gridx = 0;
		gbc_activeClientReservationPanel.gridy = 0;
		add(activeClientReservationPanel, gbc_activeClientReservationPanel);

		historyClientReservationPanel = new HistoryClientReservationPanel();
		GridBagConstraints gbc_historyClientReservationPanel = new GridBagConstraints();
		gbc_historyClientReservationPanel.fill = GridBagConstraints.BOTH;
		gbc_historyClientReservationPanel.gridx = 0;
		gbc_historyClientReservationPanel.gridy = 1;
		add(historyClientReservationPanel, gbc_historyClientReservationPanel);
	}
}

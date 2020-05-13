package com.javafee.elibrary.core.tabbedform.clientreservations;

import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.LoanHistoryClientReservationTableModel;
import com.javafee.elibrary.core.tabbedform.loanservice.LoanTablePanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;

public class HistoryClientReservationPanel extends LoanTablePanel {
	public HistoryClientReservationPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("historyClientReservationPanel.historyClientReservationPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		getLoanTable().setModel(new LoanHistoryClientReservationTableModel());
	}
}

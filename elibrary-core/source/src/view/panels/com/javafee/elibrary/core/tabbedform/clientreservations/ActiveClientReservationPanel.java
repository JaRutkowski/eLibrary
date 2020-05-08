package com.javafee.elibrary.core.tabbedform.clientreservations;

import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.LoanActiveClientReservationTableModel;
import com.javafee.elibrary.core.tabbedform.loanservice.LoanTablePanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;

public class ActiveClientReservationPanel extends LoanTablePanel {
	public ActiveClientReservationPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("activeClientReservationPanel.activeClientReservationPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		getLoanTable().setModel(new LoanActiveClientReservationTableModel());
	}

}

package com.javafee.elibrary.core.tabbedform.clientreservations;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.LoanActiveClientReservationTableModel;
import com.javafee.elibrary.core.tabbedform.loanservice.LoanTablePanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class ActiveClientReservationPanel extends LoanTablePanel {
	@Getter
	private JButton btnCancelReservation;

	public ActiveClientReservationPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("activeClientReservationPanel.activeClientReservationPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		getLoanTable().setModel(new LoanActiveClientReservationTableModel());

		btnCancelReservation = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnCancelReservation"));
		btnCancelReservation
				.setIcon(new ImageIcon(new ImageIcon(ActiveClientReservationPanel.class.getResource("/images/btnDelete-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnCancelReservation = new GridBagConstraints();
		gbc_btnCancelReservation.gridx = 0;
		gbc_btnCancelReservation.gridy = 1;
		add(btnCancelReservation, gbc_btnCancelReservation);
	}
}

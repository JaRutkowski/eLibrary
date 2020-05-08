package com.javafee.elibrary.core.tabbedform.clientreservations;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.LoanClientReservationTableModel;
import com.javafee.elibrary.core.tabbedform.loanservice.LoanTablePanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class CreateReservationPanel extends LoanTablePanel {
	@Getter
	private JButton btnReservation;

	public CreateReservationPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("createReservationPanel.createReservationPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		getLoanTable().setModel(new LoanClientReservationTableModel());

		btnReservation = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnReservation"));
		btnReservation
				.setIcon(new ImageIcon(new ImageIcon(CreateReservationPanel.class.getResource("/images/btnAdd-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnReservation = new GridBagConstraints();
		gbc_btnReservation.gridx = 0;
		gbc_btnReservation.gridy = 1;
		add(btnReservation, gbc_btnReservation);
	}
}

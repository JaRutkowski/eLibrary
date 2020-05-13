package com.javafee.elibrary.core.tabbedform;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.LoanActiveClientReservationTableModel;
import com.javafee.elibrary.core.model.LoanTableModel;
import com.javafee.elibrary.core.tabbedform.clientreservations.ClientReservationPanel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Lend;
import com.javafee.elibrary.hibernate.dto.library.Reservation;

import lombok.Setter;

public class TabBrowseReservationEvent implements IActionForm {
	@Setter
	private ClientReservationPanel clientReservationPanel;

	protected static TabBrowseReservationEvent tabBrowseReservationEvent = null;

	public TabBrowseReservationEvent(ClientReservationPanel clientReservationPanel) {
		this.control(clientReservationPanel);
	}

	public static TabBrowseReservationEvent getInstance(ClientReservationPanel clientReservationPanel) {
		if (tabBrowseReservationEvent == null) {
			tabBrowseReservationEvent = new TabBrowseReservationEvent(clientReservationPanel);
		}
		return tabBrowseReservationEvent;
	}

	private void control(ClientReservationPanel clientReservationPanel) {
		setClientReservationPanel(clientReservationPanel);
		initializeForm();

		clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getBtnCancelReservation().addActionListener(e -> onClickBtnCancelReservation());
	}

	@Override
	public void initializeForm() {
	}

	private void onClickBtnCancelReservation() {
		if (validateLoanActiveClientReservationPanel()) {
			int selectedRowIndex = clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable()
					.convertRowIndexToModel(clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanActiveClientReservationTableModel) clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable()
						.getModel()).getLend(selectedRowIndex);
				Reservation reservation = selectedLend.getReservation();
				reservation.setIsCancelled(true);
				reservation.setIsActive(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Reservation.class.getName(), reservation);
				HibernateUtil.commitTransaction();

				((LoanTableModel) clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable().getModel()).reloadData();
				((LoanTableModel) clientReservationPanel.getCreateReservationPanel().getLoanTable().getModel()).reloadData();

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.reservationCancelSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabLoanServiceEvent.reservationCancelSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
	}

	private boolean validateLoanActiveClientReservationPanel() {
		return clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable().getSelectedRow() != -1;
	}
}

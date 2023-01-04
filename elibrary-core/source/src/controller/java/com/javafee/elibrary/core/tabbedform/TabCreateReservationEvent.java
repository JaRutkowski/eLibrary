package com.javafee.elibrary.core.tabbedform;

import java.text.MessageFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.LoanTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.core.tabbedform.clientreservations.ClientReservationPanel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Lend;
import com.javafee.elibrary.hibernate.dto.library.Reservation;

import lombok.Setter;

public class TabCreateReservationEvent implements IActionForm {
	@Setter
	private ClientReservationPanel clientReservationPanel;

	protected static TabCreateReservationEvent tabCreateReservationEvent = null;

	public TabCreateReservationEvent(ClientReservationPanel clientReservationPanel) {
		this.control(clientReservationPanel);
	}

	public static TabCreateReservationEvent getInstance(ClientReservationPanel clientReservationPanel) {
		if (tabCreateReservationEvent == null) {
			tabCreateReservationEvent = new TabCreateReservationEvent(clientReservationPanel);
		}
		return tabCreateReservationEvent;
	}

	public void control(ClientReservationPanel clientReservationPanel) {
		setClientReservationPanel(clientReservationPanel);
		initializeForm();

		clientReservationPanel.getCreateReservationPanel().getBtnReservation().addActionListener(e -> onClickBtnReservation());
		clientReservationPanel.getRootPane().setDefaultButton(clientReservationPanel.getCreateReservationPanel().getBtnReservation());
	}

	@Override
	public void initializeForm() {
	}

	private void onClickBtnReservation() {
		if (validateLoanClientReservationTableSelection()) {
			int selectedLoanRowIndex = clientReservationPanel.getCreateReservationPanel().getLoanTable()
					.convertRowIndexToModel(clientReservationPanel.getCreateReservationPanel().getLoanTable().getSelectedRow());
			if (selectedLoanRowIndex != -1) {
				Client loggedUser = LogInEvent.getClient();
				Lend selectedLoan = ((LoanTableModel) clientReservationPanel.getCreateReservationPanel().getLoanTable().getModel())
						.getLend(selectedLoanRowIndex);

				if (!loggedUser.equals(selectedLoan.getClient())) {
					if (!Validator.validateIfReservationsLimitExceeded(loggedUser.getIdUserData(), 1L)) {
						if (!Validator.validateIfVolumeActiveReservationExists(selectedLoan.getVolume().getIdVolume())) {
							Reservation reservation = new Reservation();
							reservation.setClient(loggedUser);
							reservation.setVolume(selectedLoan.getVolume());
							reservation.setReservationDate(new Date());

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().save(reservation);
							HibernateUtil.commitTransaction();

							selectedLoan.setReservation(reservation);

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().update(Lend.class.getName(), selectedLoan);
							HibernateUtil.commitTransaction();

							((LoanTableModel) clientReservationPanel.getCreateReservationPanel().getLoanTable().getModel()).reloadData();
							((LoanTableModel) clientReservationPanel.getBrowseReservationPanel().getActiveClientReservationPanel().getLoanTable().getModel()).reloadData();

							Utils.displayOptionPane(
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabLoanServiceEvent.reservationSuccess"),
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabLoanServiceEvent.reservationSuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);
						} else
							JOptionPane.showMessageDialog(clientReservationPanel,
									SystemProperties.getInstance().getResourceBundle()
											.getString("tabLoanServiceEvent.reservationError"),
									SystemProperties.getInstance().getResourceBundle().getString(
											"tabLoanServiceEvent.reservationErrorTitle"),
									JOptionPane.ERROR_MESSAGE);
					} else
						JOptionPane.showMessageDialog(clientReservationPanel,
								MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
												.getString("tabLoanServiceEvent.reservationsLimitExceededError"),
										SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_RESERVATIONS_LIMIT).getValue()),
								SystemProperties.getInstance().getResourceBundle().getString(
										"tabLoanServiceEvent.reservationErrorTitle"),
								JOptionPane.ERROR_MESSAGE);
				} else
					JOptionPane.showMessageDialog(clientReservationPanel,
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationError1"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationErrorTitle"),
							JOptionPane.ERROR_MESSAGE);
			}
		} else
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
	}

	private boolean validateLoanClientReservationTableSelection() {
		return clientReservationPanel.getCreateReservationPanel().getLoanTable().getSelectedRow() != -1;
	}
}

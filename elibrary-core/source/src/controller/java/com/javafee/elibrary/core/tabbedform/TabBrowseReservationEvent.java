package com.javafee.elibrary.core.tabbedform;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.LoanActiveClientReservationTableModel;
import com.javafee.elibrary.core.tabbedform.clientreservations.BrowseReservationPanel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Lend;
import com.javafee.elibrary.hibernate.dto.library.Reservation;

import lombok.Setter;

public class TabBrowseReservationEvent implements IActionForm {
	@Setter
	private BrowseReservationPanel browseReservationPanel;

	protected static TabBrowseReservationEvent tabBrowseReservationEvent = null;

	public TabBrowseReservationEvent(BrowseReservationPanel browseReservationPanel) {
		this.control(browseReservationPanel);
	}

	public static TabBrowseReservationEvent getInstance(BrowseReservationPanel browseReservationPanel) {
		if (tabBrowseReservationEvent == null) {
			tabBrowseReservationEvent = new TabBrowseReservationEvent(browseReservationPanel);
		}
		return tabBrowseReservationEvent;
	}

	private void control(BrowseReservationPanel browseReservationPanel) {
		setBrowseReservationPanel(browseReservationPanel);
		initializeForm();

		browseReservationPanel.getActiveClientReservationPanel().getBtnCancelReservation().addActionListener(e -> onClickBtnCancelReservation());
	}

	@Override
	public void initializeForm() {
	}

	private void onClickBtnCancelReservation() {
		if (validateLoanActiveClientReservationPanel()) {
			int selectedRowIndex = browseReservationPanel.getActiveClientReservationPanel().getLoanTable()
					.convertRowIndexToModel(browseReservationPanel.getActiveClientReservationPanel().getLoanTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanActiveClientReservationTableModel) browseReservationPanel.getActiveClientReservationPanel().getLoanTable()
						.getModel()).getLend(selectedRowIndex);
				Reservation reservation = selectedLend.getReservation();
				reservation.setIsCancelled(true);
				reservation.setIsActive(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Reservation.class.getName(), reservation);
				HibernateUtil.commitTransaction();

				//((LoanActiveClientReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel()).reloadData();
				//((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();

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
		return browseReservationPanel.getActiveClientReservationPanel().getLoanTable().getSelectedRow() != -1;
	}
}

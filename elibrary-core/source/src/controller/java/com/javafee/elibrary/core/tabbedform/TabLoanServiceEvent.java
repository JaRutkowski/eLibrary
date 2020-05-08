package com.javafee.elibrary.core.tabbedform;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedLoanServiceEventLoadingException;
import com.javafee.elibrary.core.model.ClientTableModel;
import com.javafee.elibrary.core.model.LoanReservationTableModel;
import com.javafee.elibrary.core.model.LoanTableModel;
import com.javafee.elibrary.core.model.VolumeTableModel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Lend;
import com.javafee.elibrary.hibernate.dto.library.Reservation;
import com.javafee.elibrary.hibernate.dto.library.Volume;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class TabLoanServiceEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabLoanServiceEvent loadServiceEvent = null;

	public TabLoanServiceEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabLoanServiceEvent getInstance(TabbedForm tabbedForm) {
		((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();
		((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable().getModel()).reloadData();
		if (loadServiceEvent == null) {
			loadServiceEvent = new TabLoanServiceEvent(tabbedForm);
		} else
			new RefusedLoanServiceEventLoadingException("Cannot loan service event loading");
		return loadServiceEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getPanelLoanService().getBtnLoan().addActionListener(e -> onClickBtnLoan());
		tabbedForm.getPanelLoanService().getBtnReservation().addActionListener(e -> onClickBtnReservation());
		tabbedForm.getPanelLoanService().getBtnProlongation().addActionListener(e -> onClickBtnProlongation());
		tabbedForm.getPanelLoanService().getBtnReturn().addActionListener(e -> onClickBtnReturn());
		tabbedForm.getPanelLoanService().getBtnPenalty().addActionListener(e -> onClickBtnPenalty());
		tabbedForm.getPanelLoanService().getLoanTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onSelectionChangeLoanTable();
		});
		tabbedForm.getPanelLoanService().getLoanTable().getModel().addTableModelListener(e -> onChangeLoanTable());
		tabbedForm.getPanelLoanService().getBtnCancelReservation().addActionListener(e -> onClickBtnCancelReservation());
	}

	@Override
	public void initializeForm() {
		onChangeLoanTable();
	}

	private void onClickBtnLoan() {
		int selectedClientRow = tabbedForm.getPanelLoanService().getClientTable().getSelectedRow();
		int selectedVolumeRow = tabbedForm.getPanelLoanService().getVolumeLoanTable().getSelectedRow();

		if (selectedClientRow != -1 && selectedVolumeRow != -1) {
			final Client selectedClient = ((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable()
					.getModel()).getClient(selectedClientRow);
			final Volume selectedVolume = ((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable()
					.getModel()).getVolume(selectedVolumeRow);

			final Date lendDate;
			Lend loan = null;
			try {
				lendDate = Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT.format(new Date()));
				final Calendar cal = Calendar.getInstance();
				cal.add(Constants.APPLICATION_PROLONGATION_PERIOD.getFirst(),
						Constants.APPLICATION_PROLONGATION_PERIOD.getSecond().intValue());

				loan = new Lend();
				loan.setClient(selectedClient);
				loan.setVolume(selectedVolume);
				loan.setLendDate(lendDate);
				loan.setReturnedDate(cal.getTime());
				selectedVolume.getLend().add(loan);
			} catch (ParseException e) {
				log.severe(e.getMessage());
			}

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(loan);
			HibernateUtil.getSession().update(selectedVolume);
			HibernateUtil.commitTransaction();

			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();
			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("tabLoanServiceEvent.loanSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolume"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolumeTitle"),
					JOptionPane.ERROR_MESSAGE);
	}

	private void onClickBtnReservation() {
		if (tabbedForm.getPanelLoanService().getClientTable().getSelectedRow() != -1
				&& validateLoanTableSelection()) {
			int selectedClientRowIndex = tabbedForm.getPanelLoanService().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelLoanService().getClientTable().getSelectedRow());
			int selectedLoanRowIndex = tabbedForm.getPanelLoanService().getLoanTable()
					.convertRowIndexToModel(tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow());
			if (selectedClientRowIndex != -1 && selectedLoanRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable()
						.getModel()).getClient(selectedClientRowIndex);
				Client clientShallowClone = (Client) selectedClient.clone();
				Lend selectedLoan = ((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
						.getLend(selectedLoanRowIndex);
				Lend lendShallowClone = (Lend) selectedLoan.clone();

				if (!selectedClient.equals(selectedLoan.getClient())) {
					if (!validateIfActiveReservationForLendExists(lendShallowClone)) {
						Reservation reservation = new Reservation();
						reservation.setClient(clientShallowClone);
						reservation.setVolume(lendShallowClone.getVolume());
						reservation.setReservationDate(new Date());

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().save(reservation);
						HibernateUtil.commitTransaction();

						lendShallowClone.setReservation(reservation);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession()
								.evict(((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable().getModel())
										.getClient(selectedClientRowIndex));
						HibernateUtil.getSession()
								.evict(((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
										.getLend(selectedLoanRowIndex));
						HibernateUtil.getSession().update(Lend.class.getName(), lendShallowClone);
						HibernateUtil.commitTransaction();

						((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
						((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel()).reloadData();

						Utils.displayOptionPane(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationSuccess"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} else
						JOptionPane.showMessageDialog(tabbedForm.getFrame(),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationError"),
								SystemProperties.getInstance().getResourceBundle().getString(
										"tabLoanServiceEvent.reservationErrorTitle"),
								JOptionPane.ERROR_MESSAGE);
				} else
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
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

	@SuppressWarnings("deprecation")
	private void onClickBtnProlongation() {
		if (validateLoanTableSelection()) {
			final JTable jTable = tabbedForm.getPanelLoanService().getLoanTable();
			Lend lend = ((LoanTableModel) jTable.getModel())
					.getLend(jTable.convertRowIndexToModel(jTable.getSelectedRow()));

			Date lendData = lend.getLendDate();
			Date returnData = lend.getReturnedDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(lendData);

			Date deadLine = new Date();
			deadLine.setMonth(lendData.getMonth());
			deadLine.setYear(lendData.getYear());
			deadLine.setDate(lendData.getDate());
			if (lendData.getMonth() == 9) {
				deadLine.setMonth(0);
				deadLine.setYear(deadLine.getYear() + 1);
			} else if (lendData.getMonth() == 10) {
				deadLine.setMonth(1);
				deadLine.setYear(deadLine.getYear() + 1);
			} else if (lendData.getMonth() == 11) {
				deadLine.setMonth(2);
				deadLine.setYear(deadLine.getYear() + 1);
			} else
				deadLine.setMonth(deadLine.getMonth() + 3);

			Boolean error = false;
			if (returnData.getMonth() == 11) {
				returnData.setMonth(0);
				returnData.setYear(returnData.getYear() + 1);
				if (returnData.after(deadLine))
					error = true;
			} else {
				returnData.setMonth(returnData.getMonth() + 1);
				if (returnData.after(deadLine))
					error = true;
			}

			if (!error) {
				lend.setReturnedDate(returnData);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Lend.class.getName(), lend);
				HibernateUtil.commitTransaction();

				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
						.setLend(tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow(), lend);
				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("loanServicePanel.loanLimitExpiredError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("loanServicePanel.loanLimitExpiredErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		} else
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
	}

	private void onClickBtnReturn() {
		if (!validateLoanTableSelection()) {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		} else {
			final Lend lend = getSelectedLend();
			if (calculatePenalty() != new BigDecimal(0).doubleValue())
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyError") + " "
								+ calculatePenalty() + Constants.APPLICATION_CURRENCY,
						SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			else if (!validateIfActiveReservationForLendExists(lend)) {
				lend.setIsReturned(true);
				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(lend);
				HibernateUtil.commitTransaction();

				((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();
				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanReturnSuccess"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanReturnSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				Reservation reservation = lend.getReservation();
				lend.setClient(reservation.getClient());
				reservation.setIsActive(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Lend.class.getName(), lend);
				HibernateUtil.getSession().update(Reservation.class.getName(), reservation);
				HibernateUtil.commitTransaction();

				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
				((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanReservationRealizationSuccess") + " ["
								+ lend.getClient().getName() + " " + lend.getClient().getSurname() + "]",
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanReservationRealizationSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void onClickBtnPenalty() {
		if (!validateLoanTableSelection()) {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		} else {
			final Lend lend = getSelectedLend();
			if (calculatePenalty() != new BigDecimal(0).doubleValue()) {
				//TODO Handle data base penalty approach
				//TODO Bug fix of paying penalty for volume that is reserved
				lend.setIsReturned(true);
				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(lend);
				HibernateUtil.commitTransaction();

				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
				((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.penaltyPaid"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.penaltyPaidTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.loanErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onClickBtnCancelReservation() {
		if (tabbedForm.getPanelLoanService().getReservationTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLoanService().getReservationTable()
					.convertRowIndexToModel(tabbedForm.getPanelLoanService().getReservationTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable()
						.getModel()).getLend(selectedRowIndex);
				Reservation reservation = selectedLend.getReservation();
				reservation.setIsCancelled(true);
				reservation.setIsActive(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Reservation.class.getName(), reservation);
				HibernateUtil.commitTransaction();

				((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel()).reloadData();
				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
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

	private void onChangeLoanTable() {
		boolean isLoanTableNotEmpty = !((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).getLends().isEmpty();
		tabbedForm.getPanelLoanService().getBtnProlongation().setEnabled(isLoanTableNotEmpty);
		tabbedForm.getPanelLoanService().getBtnReturn().setEnabled(isLoanTableNotEmpty);
		tabbedForm.getPanelLoanService().getBtnReservation().setEnabled(isLoanTableNotEmpty);
		tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(isLoanTableNotEmpty);
		tabbedForm.getPanelLoanService().getBtnCancelReservation().setEnabled(isLoanTableNotEmpty);
	}

	private void onSelectionChangeLoanTable() {
		tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow() != -1
				&& tabbedForm.getPanelLoanService().getLoanTable().convertRowIndexToModel(tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow()) != -1
				&& calculatePenalty() != new BigDecimal(0).doubleValue());
	}

	private double calculatePenalty() {
		int diffMonth = 0;
		if (validateLoanTableSelection()) {
			int selectedRowIndex = tabbedForm.getPanelLoanService().getLoanTable()
					.convertRowIndexToModel(tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
						.getLend(selectedRowIndex);
				Date returnDate = selectedLend.getReturnedDate();

				if (new Date().after(returnDate)) {
					Calendar startCalendar = new GregorianCalendar();
					startCalendar.setTime(returnDate);
					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(new Date());

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
				}
			}
		}
		return diffMonth * Double.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_PENALTY_VALUE).getValue());
	}

	private Lend getSelectedLend() {
		final JTable jTable = tabbedForm.getPanelLoanService().getLoanTable();
		final Lend lend = ((LoanTableModel) jTable.getModel())
				.getLend(jTable.convertRowIndexToModel(jTable.getSelectedRow()));
		return lend;
	}

	private boolean validateIfActiveReservationForLendExists(Lend lend) {
		return lend.getReservation() != null && lend.getReservation().getIsActive();
	}

	private boolean validateLoanTableSelection() {
		final JTable jTable = tabbedForm.getPanelLoanService().getLoanTable();
		return jTable.getSelectedRow() != -1;
	}
}

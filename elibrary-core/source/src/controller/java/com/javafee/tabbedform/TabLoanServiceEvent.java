package com.javafee.tabbedform;

import com.javafee.common.Constants;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedLoanServiceEventLoadingException;




import com.javafee.model.ClientTableModel;
import com.javafee.model.LoanReservationTableModel;
import com.javafee.model.LoanTableModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.Lend;
import com.javafee.hibernate.dto.library.Volume;
import lombok.Setter;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

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

		tabbedForm.getPanelLoanService().getBtnLoan().addActionListener(e -> {
			try {
				prepareReservation();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		tabbedForm.getPanelLoanService().getBtnReservation().addActionListener(e -> onClickBtnReservation());
		tabbedForm.getPanelLoanService().getBtnProlongation().addActionListener(e -> onClickBtnProlongation());
		tabbedForm.getPanelLoanService().getBtnReturn().addActionListener(e -> onClickBtnReturn());
		tabbedForm.getPanelLoanService().getBtnPenalty().addActionListener(e -> onClickBtnPenalty());
		tabbedForm.getPanelLoanService().getLoanTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onClientTableListSelectionChange();
		});
		tabbedForm.getPanelLoanService().getLoanTable().getModel().addTableModelListener(e -> onTableLoanChanged());
		tabbedForm.getPanelLoanService().getBtnCancelReservation()
				.addActionListener(e -> onClickBtnCancelReservation());

	}

	private void onClickBtnCancelReservation() {
		if (tabbedForm.getPanelLoanService().getReservationTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLoanService().getReservationTable()
					.convertRowIndexToModel(tabbedForm.getPanelLoanService().getReservationTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Lend selectedLend = ((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable()
						.getModel()).getLend(selectedRowIndex);
				selectedLend.setReservationClient(null);
				selectedLend.getVolume().setIsReserve(false);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(Lend.class.getName(), selectedLend);
				HibernateUtil.commitTransaction();

				((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel())
						.reloadData();
				((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();

				JOptionPane.showMessageDialog(tabbedForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabLoanServiceEvent.reservationCancelSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabLoanServiceEvent.reservationCancelSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		}
	}

	private void onTableLoanChanged() {
		if (((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).getLends().isEmpty()) {
			tabbedForm.getPanelLoanService().getBtnProlongation().setEnabled(false);
			tabbedForm.getPanelLoanService().getBtnReturn().setEnabled(false);
			tabbedForm.getPanelLoanService().getBtnReservation().setEnabled(false);
			tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(false);
		} else {
			tabbedForm.getPanelLoanService().getBtnProlongation().setEnabled(true);
			tabbedForm.getPanelLoanService().getBtnReturn().setEnabled(true);
			tabbedForm.getPanelLoanService().getBtnReservation().setEnabled(true);
			tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(true);
		}
	}

	private void onClientTableListSelectionChange() {
		if (tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow() != -1
				&& tabbedForm.getPanelLoanService().getLoanTable().convertRowIndexToModel(
				tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow()) != -1) {
			if (calculatePenalty() == new BigDecimal(0).doubleValue())
				tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(false);
			else
				tabbedForm.getPanelLoanService().getBtnPenalty().setEnabled(true);
		}
	}

	@Override
	public void initializeForm() {
		onTableLoanChanged();
	}

	@SuppressWarnings("deprecation")
	private void onClickBtnProlongation() {
		final JTable jTable = tabbedForm.getPanelLoanService().getLoanTable();
		if (jTable.convertRowIndexToModel(jTable.getSelectedRow()) != -1) {
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

		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.notSelectedLoanError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.notSelectedLoanErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private double calculatePenalty() {
		int diffMonth = 0;
		if (tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow() != -1) {
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
				System.out.println(diffMonth * Constants.PENALTY_VALUE);
			}
		}
		return diffMonth * Constants.PENALTY_VALUE;
	}

	private void prepareReservation() throws ParseException {
		int selectedClientRow = tabbedForm.getPanelLoanService().getClientTable().getSelectedRow();
		int selectedVolumeRow = tabbedForm.getPanelLoanService().getVolumeLoanTable().getSelectedRow();

		if (selectedClientRow != -1 && selectedVolumeRow != -1) {

			final Client selectedClient = ((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable()
					.getModel()).getClient(selectedClientRow);
			final Volume selectedVolume = ((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable()
					.getModel()).getVolume(selectedVolumeRow);

			HibernateUtil.beginTransaction();
			final Date lendDate = Constants.APPLICATION_DATE_FORMAT
					.parse(Constants.APPLICATION_DATE_FORMAT.format(new Date()));
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);

			final Lend loan = new Lend();
			loan.setClient(selectedClient);
			loan.setVolume(selectedVolume);
			loan.setLendDate(lendDate);
			loan.setReturnedDate(cal.getTime());
			loan.getVolume().setIsLended(true);
			HibernateUtil.getSession().save(loan);
			HibernateUtil.commitTransaction();

			changeStatusVolumeISLended(selectedVolume);

			final LoanTableModel vtm = (LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel();
			vtm.add(loan);

			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel())
					.remove(loan.getVolume());
			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel())
					.fireTableDataChanged();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("tabLoanServiceEvent.loanSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolume"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("loanServicePanel.loanLackClientOrVolumeTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void changeStatusVolumeISLended(final Volume volume) {
		HibernateUtil.beginTransaction();
		volume.setIsLended(true);
		HibernateUtil.getSession().save(volume);
		HibernateUtil.commitTransaction();
	}

	private void changeStatusVolumeISReturned(final Volume volume) {
		HibernateUtil.beginTransaction();
		volume.setIsLended(false);
		HibernateUtil.getSession().save(volume);
		HibernateUtil.commitTransaction();
	}

	private void onClickBtnReturn() {
		final Lend lend = getLendClicked();

		if (calculatePenalty() != new BigDecimal(0).doubleValue()) {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyError") + " "
							+ calculatePenalty() + Constants.APPLICATION_CURRENCY,
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.penaltyErrorTitle"),
					JOptionPane.ERROR_MESSAGE);

		} else if (Objects.isNull(lend.getReservationClient())) {
			lend.setIsReturned(true);
			changeStatusVolumeISReturned(lend.getVolume());
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().delete(lend);
			HibernateUtil.commitTransaction();

			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).delete(lend);
			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).fireTableDataChanged();
			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReturnSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReturnSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			lend.setClient(lend.getReservationClient());
			lend.setReservationClient(null);
			lend.getVolume().setIsReserve(false);
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(lend);
			HibernateUtil.commitTransaction();

			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
			((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel())
					.reloadData();

			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReservationRealizationSuccess") + " ["
							+ lend.getClient().getName() + " " + lend.getClient().getSurname() + "]",
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanServiceEvent.loanReservationRealizationSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void onClickBtnPenalty() {
		final Lend lend = getLendClicked();

		if (calculatePenalty() != new BigDecimal(0).doubleValue()) {
			changeStatusVolumeISReturned(lend.getVolume());
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().delete(lend);
			HibernateUtil.commitTransaction();

			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).add(lend.getVolume());
			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).delete(lend);
			((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel()).reloadData();
			((VolumeTableModel) tabbedForm.getPanelLoanService().getVolumeLoanTable().getModel()).reloadData();
			JOptionPane.showMessageDialog(tabbedForm.getFrame(), "Kara została spłacona", "Spłacona",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(tabbedForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanError"),
					SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.loanErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private Lend getLendClicked() {
		final JTable jTable = tabbedForm.getPanelLoanService().getLoanTable();
		final Lend lend = ((LoanTableModel) jTable.getModel())
				.getLend(jTable.convertRowIndexToModel(jTable.getSelectedRow()));

		return lend;
	}

	private void onClickBtnReservation() {
		if (tabbedForm.getPanelLoanService().getClientTable().getSelectedRow() != -1
				&& tabbedForm.getPanelLoanService().getLoanTable().getSelectedRow() != -1) {
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

					if (!selectedLoan.getVolume().getIsReserve()) {
						lendShallowClone.getVolume().setIsReserve(true);
						lendShallowClone.setReservationClient(clientShallowClone);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession()
								.evict(((ClientTableModel) tabbedForm.getPanelLoanService().getClientTable().getModel())
										.getClient(selectedClientRowIndex));
						HibernateUtil.getSession()
								.evict(((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
										.getLend(selectedLoanRowIndex));
						HibernateUtil.getSession().update(Lend.class.getName(), lendShallowClone);
						HibernateUtil.commitTransaction();

						((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
								.setLend(selectedLoanRowIndex, lendShallowClone);
						((LoanTableModel) tabbedForm.getPanelLoanService().getLoanTable().getModel())
								.fireTableDataChanged();
						((LoanReservationTableModel) tabbedForm.getPanelLoanService().getReservationTable().getModel())
								.reloadData();

						Utils.displayOptionPane(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationSuccess"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationSuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(tabbedForm.getFrame(),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLoanServiceEvent.reservationError"),
								SystemProperties.getInstance().getResourceBundle().getString(
										"tabLoanServiceEvent.reservationErrorTitle"),
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(tabbedForm.getFrame(),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationError1"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLoanServiceEvent.reservationErrorTitle"),
							JOptionPane.ERROR_MESSAGE);
				}

			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabLoanEvent.notSelectedTablesWarning"));
		}
	}
}

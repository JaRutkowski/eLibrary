package com.javafee.elibrary.core.model;

import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Reservation;

import lombok.Getter;
import lombok.Setter;

public class HistoryClientReservationTableModel extends AbstractTableModel {
	@Getter
	@Setter
	private List<Reservation> reservations;
	protected String[] columns;

	public HistoryClientReservationTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookTitleCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookIsbnNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeInventoryNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.reservationDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledCol")};
	}

	protected void prepareHibernateDao() {
		int loggedUserId = LogInEvent.getClient() != null ? LogInEvent.getClient().getIdUserData() : 0;
		setReservations(HibernateUtil.getSession().createQuery("from Reservation as res left join fetch res.volume " +
				"where res.isActive = false and res.client = " + loggedUserId).list());
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return reservations.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Reservation reservation = reservations.get(row);

		switch (Constants.LendClientReservationTableColumn.getByNumber(col)) {
			case COL_VOLUME_BOOK_TITLE:
				return reservation.getVolume().getBook();
			case COL_VOLUME_BOOK_ISBN_NUMBER:
				return reservation.getVolume().getBook().getIsbnNumber();
			case COL_VOLUME_INVENTORY_NUMBER:
				return reservation.getVolume().getInventoryNumber();
			case COL_LEND_DATE_OR_RESERVATION_DATE:
				return Constants.APPLICATION_DATE_FORMAT.format(reservation.getReservationDate());
			case COL_RETURNED_DATE_OR_IS_CANCELLED:
				return reservation.getIsCancelled() ?
						SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledTrueVal")
						: SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledFalseVal");
			default:
				return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	public void reloadData() {
		this.prepareHibernateDao();
		this.fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}

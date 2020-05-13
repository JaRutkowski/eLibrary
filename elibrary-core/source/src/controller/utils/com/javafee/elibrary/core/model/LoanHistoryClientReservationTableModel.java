package com.javafee.elibrary.core.model;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Lend;

public class LoanHistoryClientReservationTableModel extends LoanTableModel {
	public LoanHistoryClientReservationTableModel() {
		super();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookTitleCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookIsbnNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeInventoryNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.reservationDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledCol")};
	}

	@Override
	protected void prepareHibernateDao() {
		int loggedUserId = LogInEvent.getClient() != null ? LogInEvent.getClient().getIdUserData() : 0;
		//TODO Query Reservation instead of Lend
		//		setLends(HibernateUtil.getSession().createQuery("from Reservation as res left join fetch res.reservation " +
		//				"where len.reservation is not null and len.reservation.isActive = false and len.reservation.client = " + loggedUserId).list());
		setLends(HibernateUtil.getSession().createQuery("from Lend as len left join fetch len.reservation " +
				"where len.reservation is not null and len.reservation.isActive = false and len.reservation.client = " + loggedUserId).list());
	}

	@Override
	public Object getValueAt(int row, int col) {
		Lend lend = getLends().get(row);

		switch (Constants.LendClientReservationTableColumn.getByNumber(col)) {
			case COL_VOLUME_BOOK_TITLE:
				return lend.getVolume().getBook();
			case COL_VOLUME_BOOK_ISBN_NUMBER:
				return lend.getVolume().getBook().getIsbnNumber();
			case COL_VOLUME_INVENTORY_NUMBER:
				return lend.getVolume().getInventoryNumber();
			case COL_LEND_DATE_OR_RESERVATION_DATE:
				return Constants.APPLICATION_DATE_FORMAT.format(lend.getReservation() != null ?
						lend.getReservation().getReservationDate() : "");
			case COL_RETURNED_DATE_OR_IS_CANCELLED:
				return lend.getReservation() != null && lend.getReservation().getIsCancelled() ?
						SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledTrueVal")
						: SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.isCancelledFalseVal");
			default:
				return null;
		}
	}
}

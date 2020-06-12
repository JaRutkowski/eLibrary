package com.javafee.elibrary.core.model;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Lend;

public class LoanClientReservationTableModel extends LoanTableModel {
	public LoanClientReservationTableModel() {
		super();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookTitleCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookIsbnNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeInventoryNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.lendDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.returnDateCol")};
	}

	@Override
	protected void prepareHibernateDao() {
		int loggedUserId = LogInEvent.getClient() != null ? LogInEvent.getClient().getIdUserData() : 0;
		setLends(HibernateUtil.getSession().createQuery("from Lend as len left join fetch len.reservation " +
				"where len.isReturned = false and " +
				"(len.reservation is null or " +
				"(len.reservation is not null and (len.reservation.isActive = false or len.reservation.isCancelled = true)))" +
				" and (len.client is not null and len.client != " + loggedUserId + ")").list());
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
				return Constants.APPLICATION_DATE_FORMAT.format(lend.getLendDate());
			case COL_RETURNED_DATE_OR_IS_CANCELLED:
				return Constants.APPLICATION_DATE_FORMAT.format(lend.getReturnedDate());
			default:
				return null;
		}
	}
}

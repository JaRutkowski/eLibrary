package com.javafee.elibrary.core.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Lend;

public class HistoryLoanTableModel extends LoanTableModel {
	public HistoryLoanTableModel() {
		super();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookTitleCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeBookIsbnNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.volumeInventoryNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.lendDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.returnDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("loanTableModel.penaltyCol")};
	}

	@Override
	protected void prepareHibernateDao() {
		int loggedUserId = LogInEvent.getClient() != null ? LogInEvent.getClient().getIdUserData() : 0;
		setLends(HibernateUtil.getSession().createQuery("from Lend as len join fetch len.volume " +
				"where len.isReturned = true and len.client = " + loggedUserId).list());
	}

	@Override
	public Object getValueAt(int row, int col) {
		Lend lend = getLends().get(row);

		switch (Constants.ClientLoanTableColumn.getByNumber(col)) {
			case COL_VOLUME_BOOK_TITLE:
				return lend.getVolume().getBook();
			case COL_VOLUME_BOOK_ISBN_NUMBER:
				return lend.getVolume().getBook().getIsbnNumber();
			case COL_VOLUME_INVENTORY_NUMBER:
				return lend.getVolume().getInventoryNumber();
			case COL_LEND_DATE:
				return Constants.APPLICATION_DATE_FORMAT.format(lend.getLendDate());
			case COL_RETURNED_DATE:
				return Constants.APPLICATION_DATE_FORMAT.format(lend.getReturnedDate());
			case COL_PENALTY:
				return calculatePenalty(lend.getReturnedDate());
			default:
				return null;
		}
	}

	private double calculatePenalty(Date returnDate) {
		int diffMonth = 0;
		if (new Date().after(returnDate)) {
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(returnDate);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(new Date());

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		}
		return diffMonth * Double.valueOf(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_PENALTY_VALUE).getValue());
	}
}
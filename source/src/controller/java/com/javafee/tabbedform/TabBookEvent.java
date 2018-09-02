package com.javafee.tabbedform;

import java.util.List;

import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedBookEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.BookTableModel;

import lombok.Setter;

public class TabBookEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabBookEvent bookEvent = null;
	private BookAddModEvent bookAddModEvent;

	public TabBookEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabBookEvent getInstance(TabbedForm tabbedForm) {
		if (bookEvent == null) {
			bookEvent = new TabBookEvent(tabbedForm);
		} else
			new RefusedBookEventLoadingException("Cannot book event loading");
		return bookEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getPanelBook().getCockpitEditionPanelBook().getBtnAdd().addActionListener(e -> onClickBtnAdd());
		tabbedForm.getPanelBook().getCockpitEditionPanelBook().getBtnModify()
				.addActionListener(e -> onClickBtnModify());
		tabbedForm.getPanelBook().getCockpitEditionPanelBook().getBtnDelete()
				.addActionListener(e -> onClickBtnDelete());
	}

	private void onClickBtnAdd() {
		if (bookAddModEvent == null)
			bookAddModEvent = new BookAddModEvent();
		bookAddModEvent.control(Constans.Context.ADDITION,
				(BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel());
	}

	private void onClickBtnModify() {
		if (tabbedForm.getPanelBook().getBookTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelBook().getBookTable()
					.convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Book selectedBook = ((BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel())
						.getBook(selectedRowIndex);

				Params.getInstance().add("selectedBook", selectedBook);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (bookAddModEvent == null)
					bookAddModEvent = new BookAddModEvent();
				bookAddModEvent.control(Constans.Context.MODIFICATION,
						(BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel());

			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabBookEvent.notSelectedBookWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabBookEvent.notSelectedBookWarning"));
		}
	}

	private void onClickBtnDelete() {
		if (tabbedForm.getPanelBook().getBookTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelBook().getBookTable()
					.convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());

			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Book selectedBook = ((BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel())
							.getBook(selectedRowIndex);
					@SuppressWarnings("unchecked")
					List<Volume> volumes = HibernateUtil.getSession()
							.createQuery("from Volume as vol join fetch vol.book").list();
					boolean bookVolumeExist = false;
					for (Volume v : volumes) {
						if (v.getBook().getIdBook() == selectedBook.getIdBook())
							bookVolumeExist = true;
					}

					if (bookVolumeExist) {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabBookEvent.existingBookVolumeError"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabBookEvent.existingBookVolumeErrorTitle"));
					} else {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedBook);
						HibernateUtil.commitTransaction();
						((BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel()).remove(selectedBook);
					}

				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}
	}

	@Override
	public void initializeForm() {

	}
}

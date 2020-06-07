package com.javafee.elibrary.core.tabbedform;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedBookEventLoadingException;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Book;
import com.javafee.elibrary.hibernate.dto.library.Volume;

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

		tabbedForm.getPanelBook().getBookTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onBookTableListSelectionChange();
		});
	}

	private void onBookTableListSelectionChange() {
		if (tabbedForm.getPanelBook().getBookTable().getSelectedRow() != -1 && tabbedForm.getPanelBook().getBookTable()
				.convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow()) != -1) {

			int selectedRowIndex = tabbedForm.getPanelBook().getBookTable()
					.convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Book selectedBook = ((BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel())
						.getBook(selectedRowIndex);
				reloadImagePreviewPanel(selectedBook);
			}
		}
	}

	private void reloadImagePreviewPanel(Book book) {
		File dir = new File("tmp/test");
		dir.mkdirs();
		File tmp = new File(dir, "tmp.txt");
		try {
			tmp.createNewFile();
			if (book != null && book.getFile() != null
					&& book.getFile().length > 0) {
				FileUtils.writeByteArrayToFile(tmp, book.getFile());
				tabbedForm.getPanelBook().getBookImagePreviewPanel().loadImage(tmp.getAbsolutePath());
				tabbedForm.getPanelBook().getBookImagePreviewPanel()
						.paint(tabbedForm.getPanelBook().getBookImagePreviewPanel().getGraphics());
				tmp.delete();
			} else {
				tabbedForm.getPanelBook().getBookImagePreviewPanel().setImage(null);
				tabbedForm.getPanelBook().getBookImagePreviewPanel().setScaledImage(null);
				tabbedForm.getPanelBook().getBookImagePreviewPanel()
						.paint(tabbedForm.getPanelBook().getBookImagePreviewPanel().getGraphics());
			}
		} catch (IOException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("bookAddModEvent.loadingFileErrorTitle"), e);
			e.printStackTrace();
		}
	}

	private void onClickBtnAdd() {
		if (bookAddModEvent == null)
			bookAddModEvent = new BookAddModEvent();
		bookAddModEvent.control(Constants.Context.ADDITION,
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
				bookAddModEvent.control(Constants.Context.MODIFICATION,
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
							.createQuery("from Volume as vol join fetch vol.book where vol.book.idBook = "
									+ selectedBook.getIdBook()).list();

					if (!volumes.isEmpty())
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabBookEvent.existingBookVolumeError"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabBookEvent.existingBookVolumeErrorTitle"));
					else {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedBook);
						HibernateUtil.commitTransaction();
						((BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel()).remove(selectedBook);
					}

				}
			}
		} else
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
	}

	@Override
	public void initializeForm() {
		onBookTableListSelectionChange();
	}
}

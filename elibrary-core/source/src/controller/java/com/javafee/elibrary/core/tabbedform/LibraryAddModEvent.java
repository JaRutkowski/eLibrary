package com.javafee.elibrary.core.tabbedform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Context;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.common.action.IEvent;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.core.model.VolumeLoanTableModel;
import com.javafee.elibrary.core.model.VolumeReadingRoomTableModel;
import com.javafee.elibrary.core.model.VolumeTableModel;
import com.javafee.elibrary.core.tabbedform.library.frames.LibraryAddModFrame;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Book;
import com.javafee.elibrary.hibernate.dto.library.Volume;

public class LibraryAddModEvent implements IEvent {
	private Context context;
	private Context loanOrReadingRoom;
	private VolumeLoanTableModel volumeLoanTableModel;
	private VolumeReadingRoomTableModel volumeReadingRoomTableModel;

	private LibraryAddModFrame libraryAddModFrame;

	public void control(Context context, Context loanOrReadingRoom, VolumeTableModel volumeTableModel) {
		this.context = context;
		this.loanOrReadingRoom = loanOrReadingRoom;
		if (loanOrReadingRoom == Context.LOAN)
			volumeLoanTableModel = (VolumeLoanTableModel) volumeTableModel;
		else if (loanOrReadingRoom == Context.READING_ROOM)
			volumeReadingRoomTableModel = (VolumeReadingRoomTableModel) volumeTableModel;

		openLibraryAddModFrame(context);
	}

	@Override
	public void initializeEventHandlers() {
		libraryAddModFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedVolume");
			}
		});

		libraryAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept(context, loanOrReadingRoom));
	}

	private void onClickBtnAccept(Context context, Context loanOrReadingRoom) {
		if (context == Context.ADDITION) {
			if (validateTablesSelection()) {
				createVolume(loanOrReadingRoom);
			} else {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.notSelectedTablesWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.notSelectedTablesWarning"));
			}
		} else if (context == Context.MODIFICATION) {
			modificateVolume(loanOrReadingRoom);
		}
	}

	private void modificateVolume(Context loanOrReadingRoom) {
		if (libraryAddModFrame.getBookTable().getSelectedRow() != -1) {
			Volume volumeShallowClone = (Volume) Params.getInstance().get("selectedVolume");

			int selectedRowIndex = libraryAddModFrame.getBookTable()
					.convertRowIndexToModel(libraryAddModFrame.getBookTable().getSelectedRow());
			if (Validator.validateInventoryNumberExist(libraryAddModFrame.getTextFieldInventoryNumber().getText())) {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarning1"));
			} else if (libraryAddModFrame.getTextFieldInventoryNumber().getText()
					.length() == Constants.DATA_BASE_INVENTORY_NUMBER_LENGTH) {

				Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
						.getBook(selectedRowIndex);
				volumeShallowClone.setInventoryNumber(libraryAddModFrame.getTextFieldInventoryNumber().getText());
				volumeShallowClone.setBook(selectedBook);

				HibernateUtil.beginTransaction();
				if (loanOrReadingRoom == Context.LOAN) {
					HibernateUtil.getSession().evict(
							volumeLoanTableModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
					HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
				} else if (loanOrReadingRoom == Context.READING_ROOM) {
					HibernateUtil.getSession().evict(volumeReadingRoomTableModel
							.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
					HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
				}
				HibernateUtil.commitTransaction();

				if (loanOrReadingRoom == Context.LOAN) {
					volumeLoanTableModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
							volumeShallowClone);
					volumeLoanTableModel.fireTableDataChanged();
				} else if (loanOrReadingRoom == Context.READING_ROOM) {
					volumeReadingRoomTableModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
							volumeShallowClone);
					volumeReadingRoomTableModel.fireTableDataChanged();
				}

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.updatingVolumeSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"libraryAddModEvent.updatingVolumeSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);

				libraryAddModFrame.dispose();
			} else {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarning2"));
			}

		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedBookWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedBookWarning"));
		}
	}

	private void createVolume(Context loanOrReadingRoom) {
		if (libraryAddModFrame.getBookTable().getSelectedRow() != -1) {
			int selectedRowIndex = libraryAddModFrame.getBookTable()
					.convertRowIndexToModel(libraryAddModFrame.getBookTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
						.getBook(selectedRowIndex);
				String inventoryNumber = libraryAddModFrame.getTextFieldInventoryNumber().getText();
				if (Validator.validateInventoryNumberExist(inventoryNumber)) {
					LogGuiException.logWarning(
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarning1"));
				} else if (inventoryNumber.length() == Constants.DATA_BASE_INVENTORY_NUMBER_LENGTH) {
					HibernateUtil.beginTransaction();
					Volume volume = new Volume();
					volume.setBook(selectedBook);
					volume.setInventoryNumber(inventoryNumber);
					if (loanOrReadingRoom == Context.READING_ROOM)
						volume.setIsReadingRoom(true);

					HibernateUtil.getSession().save(volume);
					HibernateUtil.commitTransaction();

					if (loanOrReadingRoom == Context.LOAN)
						volumeLoanTableModel.add(volume);
					else if (loanOrReadingRoom == Context.READING_ROOM)
						volumeReadingRoomTableModel.add(volume);

					Utils.displayOptionPane(
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.savingVolumeSuccess"),
							SystemProperties.getInstance().getResourceBundle().getString(
									"libraryAddModEvent.savingVolumeSuccessTitle"),
							JOptionPane.INFORMATION_MESSAGE);

					libraryAddModFrame.dispose();
				} else {
					LogGuiException.logWarning(
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarning2"));
				}
			}
		}
	}

	private void openLibraryAddModFrame(Context context) {
		if (libraryAddModFrame == null || (libraryAddModFrame != null && !libraryAddModFrame.isDisplayable())) {
			libraryAddModFrame = new LibraryAddModFrame();
			if (context == Context.MODIFICATION) {
				fillTextBoxInventoryNumber();
				reloadTable();
			}
			initializeEventHandlers();
			libraryAddModFrame.setVisible(true);
		} else {
			libraryAddModFrame.toFront();
		}
	}

	private void reloadTable() {
		Integer selectedBookIndex = ((BookTableModel) libraryAddModFrame.getBookTable().getModel()).getBooks()
				.indexOf(((Volume) Params.getInstance().get("selectedVolume")).getBook());
		libraryAddModFrame.getBookTable().setRowSelectionInterval(selectedBookIndex, selectedBookIndex);
	}

	private void fillTextBoxInventoryNumber() {
		libraryAddModFrame.getTextFieldInventoryNumber()
				.setText(((Volume) Params.getInstance().get("selectedVolume")).getInventoryNumber() != null
						? ((Volume) Params.getInstance().get("selectedVolume")).getInventoryNumber()
						: "");
	}

	private boolean validateTablesSelection() {
		boolean result = false;

		if (libraryAddModFrame.getBookTable().getSelectedRowCount() != 0) {
			result = true;
		}

		return result;
	}
}
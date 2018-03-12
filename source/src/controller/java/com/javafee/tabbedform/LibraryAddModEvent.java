package com.javafee.tabbedform;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Context;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.BookTableModel;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.model.VolumeTableReadingRoomModel;
import com.javafee.tabbedform.library.frames.LibraryAddModFrame;

public class LibraryAddModEvent {
	private LibraryAddModFrame libraryAddModFrame;

	private VolumeTableLoanModel volumeTableLoanModel;
	private VolumeTableReadingRoomModel volumeTableReadingRoomModel;

	public void control(Context context, Context loanOrReadingRoom, VolumeTableModel volumeTableModel) {
		if (loanOrReadingRoom == Context.LOAN)
			volumeTableLoanModel = (VolumeTableLoanModel) volumeTableModel;
		else if (loanOrReadingRoom == Context.READING_ROOM)
			volumeTableReadingRoomModel = (VolumeTableReadingRoomModel) volumeTableModel;

		openLibraryAddModFrame(context);

		libraryAddModFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedVolume");
			}

			@Override
			public void windowActivated(WindowEvent e) {
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
			if(Validator.validateInventoryNumberExist(libraryAddModFrame.getTextFieldInventoryNumber().getText())) {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarning1"));
			} else if (libraryAddModFrame.getTextFieldInventoryNumber().getText()
					.length() == Constans.DATA_BASE_INVENTORY_NUMBER_LENGTH) {

				Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
						.getBook(selectedRowIndex);
				volumeShallowClone.setInventoryNumber(libraryAddModFrame.getTextFieldInventoryNumber().getText());
				volumeShallowClone.setBook(selectedBook);

				HibernateUtil.beginTransaction();
				if (loanOrReadingRoom == Context.LOAN) {
					HibernateUtil.getSession().evict(
							volumeTableLoanModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
					HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
				} else if (loanOrReadingRoom == Context.READING_ROOM) {
					HibernateUtil.getSession().evict(volumeTableReadingRoomModel
							.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
					HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
				}
				HibernateUtil.commitTransaction();

				if (loanOrReadingRoom == Context.LOAN) {
					volumeTableLoanModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
							volumeShallowClone);
					volumeTableLoanModel.fireTableDataChanged();
				} else if (loanOrReadingRoom == Context.READING_ROOM) {
					volumeTableReadingRoomModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
							volumeShallowClone);
					volumeTableReadingRoomModel.fireTableDataChanged();
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
				if(Validator.validateInventoryNumberExist(inventoryNumber)) {
					LogGuiException.logWarning(
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("libraryAddModEvent.incorrectInventoryNumberWarning1"));
				} else if (inventoryNumber.length() == Constans.DATA_BASE_INVENTORY_NUMBER_LENGTH) {
					HibernateUtil.beginTransaction();
					Volume volume = new Volume();
					volume.setBook(selectedBook);
					volume.setInventoryNumber(inventoryNumber);
					if (loanOrReadingRoom == Context.READING_ROOM)
						volume.setIsReadingRoom(true);

					HibernateUtil.getSession().save(volume);
					HibernateUtil.commitTransaction();

					if (loanOrReadingRoom == Context.LOAN)
						volumeTableLoanModel.add(volume);
					else if (loanOrReadingRoom == Context.READING_ROOM)
						volumeTableReadingRoomModel.add(volume);

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
								.toString()
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
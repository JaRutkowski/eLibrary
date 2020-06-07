package com.javafee.elibrary.core.tabbedform;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Context;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedLibraryEventLoadingException;
import com.javafee.elibrary.core.model.VolumeLoanTableModel;
import com.javafee.elibrary.core.model.VolumeReadingRoomTableModel;
import com.javafee.elibrary.core.model.VolumeTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import com.javafee.elibrary.core.uniform.ImagePanel;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Book;
import com.javafee.elibrary.hibernate.dto.library.Volume;

import lombok.Setter;

public class TabLibraryEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabLibraryEvent libraryEvent = null;
	private LibraryAddModEvent libraryAddModEvent;

	private TabLibraryEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabLibraryEvent getInstance(TabbedForm tabbedForm) {
		if (libraryEvent == null) {
			libraryEvent = new TabLibraryEvent(tabbedForm);
		} else
			new RefusedLibraryEventLoadingException("Cannot book event loading");
		return libraryEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().getBtnAdd()
				.addActionListener(e -> onClickBtnAddVolumeLoan());
		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().getBtnModify()
				.addActionListener(e -> onClickBtnModifyVolumeLoan());
		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().getBtnDelete()
				.addActionListener(e -> onClickBtnDeleteVolumeLoan());
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().getBtnAdd()
				.addActionListener(e -> onClickBtnAddVolumeReadingRoom());
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().getBtnModify()
				.addActionListener(e -> onClickBtnModifyVolumeReadingRoom());
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().getBtnDelete()
				.addActionListener(e -> onClickBtnDeleteVolumeReadingRoom());

		tabbedForm.getPanelLibrary().getLoanVolumeTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onBookTableListSelectionChange(tabbedForm.getPanelLibrary().getLoanVolumeTable(),
						tabbedForm.getPanelLibrary().getImagePreviewPanelLoan());
		});
		tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onBookTableListSelectionChange(tabbedForm.getPanelLibrary().getReadingRoomVolumeTable(),
						tabbedForm.getPanelLibrary().getImagePreviewPanelReadingRoom());
		});
	}

	@Override
	public void initializeForm() {
		switchPerspectiveToClient(LogInEvent.getRole() == Role.CLIENT);
		onBookTableListSelectionChange(tabbedForm.getPanelLibrary().getReadingRoomVolumeTable(),
				tabbedForm.getPanelLibrary().getImagePreviewPanelReadingRoom());
		onBookTableListSelectionChange(tabbedForm.getPanelLibrary().getLoanVolumeTable(),
				tabbedForm.getPanelLibrary().getImagePreviewPanelLoan());
	}

	private void onBookTableListSelectionChange(ImportExportableJTable jTable, ImagePanel imagePreviewPanel) {
		if (jTable.getSelectedRow() != -1 && jTable
				.convertRowIndexToModel(jTable.getSelectedRow()) != -1) {

			int selectedRowIndex = jTable
					.convertRowIndexToModel(jTable.getSelectedRow());

			if (selectedRowIndex != -1) {
				Book selectedBook = ((VolumeTableModel) jTable.getModel())
						.getVolume(selectedRowIndex).getBook();
				reloadImagePreviewPanel(imagePreviewPanel, selectedBook);
			}
		}
	}

	private void reloadImagePreviewPanel(ImagePanel imagePreviewPanel, Book book) {
		File dir = new File("tmp/test");
		dir.mkdirs();
		File tmp = new File(dir, "tmp.txt");
		try {
			tmp.createNewFile();
			if (book != null && book.getFile() != null
					&& book.getFile().length > 0) {
				FileUtils.writeByteArrayToFile(tmp, book.getFile());
				imagePreviewPanel.loadImage(tmp.getAbsolutePath());
				imagePreviewPanel.paint(imagePreviewPanel.getGraphics());
				tmp.delete();
			} else {
				imagePreviewPanel.setImage(null);
				imagePreviewPanel.setScaledImage(null);
				imagePreviewPanel.paint(imagePreviewPanel.getGraphics());
			}
		} catch (IOException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("bookAddModEvent.loadingFileErrorTitle"), e);
			e.printStackTrace();
		}
	}

	private void onClickBtnDeleteVolumeReadingRoom() {
		if (tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getReadingRoomVolumeTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow());
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Volume selectedVolume = ((VolumeReadingRoomTableModel) tabbedForm.getPanelLibrary()
							.getReadingRoomVolumeTable().getModel()).getVolume(selectedRowIndex);

					if (!checkIfVolumeIsLentOrReserved(selectedVolume)) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeReadingRoomTableModel) tabbedForm.getPanelLibrary().getReadingRoomVolumeTable()
								.getModel()).remove(selectedVolume);
					} else {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarning"));
					}
				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarning"));
		}
	}

	private void onClickBtnDeleteVolumeLoan() {
		if (tabbedForm.getPanelLibrary().getLoanVolumeTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getLoanVolumeTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getLoanVolumeTable().getSelectedRow());
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Volume selectedVolume = ((VolumeLoanTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable()
							.getModel()).getVolume(selectedRowIndex);

					if (!checkIfVolumeIsLentOrReserved(selectedVolume)) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeLoanTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel())
								.remove(selectedVolume);
					} else {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarning"));
					}
				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarning"));
		}
	}

	private void onClickBtnModifyVolumeReadingRoom() {
		if (tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getReadingRoomVolumeTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Volume selectedVolume = ((VolumeReadingRoomTableModel) tabbedForm.getPanelLibrary()
						.getReadingRoomVolumeTable().getModel()).getVolume(selectedRowIndex);
				Volume volumeShallowClone = (Volume) selectedVolume.clone();

				Params.getInstance().add("selectedVolume", volumeShallowClone);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (libraryAddModEvent == null)
					libraryAddModEvent = new LibraryAddModEvent();
				libraryAddModEvent.control(Constants.Context.MODIFICATION, Context.READING_ROOM,
						(VolumeTableModel) tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getModel());

			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarning"));
		}

	}

	private void onClickBtnModifyVolumeLoan() {
		if (tabbedForm.getPanelLibrary().getLoanVolumeTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getLoanVolumeTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getLoanVolumeTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Volume selectedVolume = ((VolumeLoanTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable()
						.getModel()).getVolume(selectedRowIndex);
				Volume volumeShallowClone = (Volume) selectedVolume.clone();

				if (!checkIfVolumeIsLentOrReserved(selectedVolume)) {

					Params.getInstance().add("selectedVolume", volumeShallowClone);
					Params.getInstance().add("selectedRowIndex", selectedRowIndex);

					if (libraryAddModEvent == null)
						libraryAddModEvent = new LibraryAddModEvent();
					libraryAddModEvent.control(Constants.Context.MODIFICATION, Context.LOAN,
							(VolumeTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel());
				} else {
					LogGuiException.logWarning(
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLibraryEvent.modificateLoanedReservedVolumeWarningTitle"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabLibraryEvent.modificateLoanedReservedVolumeWarning"));
				}

			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarning"));
		}
	}

	private void onClickBtnAddVolumeLoan() {
		if (libraryAddModEvent == null)
			libraryAddModEvent = new LibraryAddModEvent();
		libraryAddModEvent.control(Constants.Context.ADDITION, Constants.Context.LOAN,
				(VolumeTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel());
	}

	private void onClickBtnAddVolumeReadingRoom() {
		if (libraryAddModEvent == null)
			libraryAddModEvent = new LibraryAddModEvent();
		libraryAddModEvent.control(Constants.Context.ADDITION, Constants.Context.READING_ROOM,
				(VolumeReadingRoomTableModel) tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getModel());

	}

	private boolean checkIfVolumeIsLentOrReserved(Volume volume) {
		return !volume.getLend().isEmpty() && volume.getLend().stream().filter(l -> !l.getIsReturned()).findFirst().isPresent()
				|| !Validator.validateIfVolumeActiveReservationExists(volume.getIdVolume());
	}

	private void switchPerspectiveToClient(boolean isClient) {
		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().setVisible(!isClient);
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().setVisible(!isClient);
	}
}

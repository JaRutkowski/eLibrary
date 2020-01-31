package com.javafee.elibrary.core.tabbedform;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Context;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.hibernate.dto.library.Volume;
import com.javafee.elibrary.core.exception.RefusedLibraryEventLoadingException;
import com.javafee.elibrary.core.model.VolumeTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.core.model.VolumeTableLoanModel;
import com.javafee.elibrary.core.model.VolumeTableReadingRoomModel;

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
		View.forceRefresh(tabbedForm, LogInEvent.getRole() == Role.CLIENT);
		((VolumeTableLoanModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel()).reloadData();
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
	}

	private void onClickBtnDeleteVolumeReadingRoom() {
		if (tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getReadingRoomVolumeTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getSelectedRow());
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Volume selectedVolume = ((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary()
							.getReadingRoomVolumeTable().getModel()).getVolume(selectedRowIndex);

					if (!selectedVolume.getIsLended() && !selectedVolume.getIsReserve()) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary().getReadingRoomVolumeTable()
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
					Volume selectedVolume = ((VolumeTableLoanModel) tabbedForm.getPanelLibrary().getLoanVolumeTable()
							.getModel()).getVolume(selectedRowIndex);

					if (!selectedVolume.getIsLended() && !selectedVolume.getIsReserve()) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeTableLoanModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel())
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
				Volume selectedVolume = ((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary()
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
				Volume selectedVolume = ((VolumeTableLoanModel) tabbedForm.getPanelLibrary().getLoanVolumeTable()
						.getModel()).getVolume(selectedRowIndex);
				Volume volumeShallowClone = (Volume) selectedVolume.clone();

				if (!selectedVolume.getIsLended() && !selectedVolume.getIsReserve()) {

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
				(VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary().getReadingRoomVolumeTable().getModel());

	}

	@Override
	public void initializeForm() {
		switchPerspectiveToClient(LogInEvent.getRole() == Role.CLIENT);
	}

	private void switchPerspectiveToClient(boolean b) {
		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().setVisible(!b);
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().setVisible(!b);
	}

	private static class View {
		private static void forceRefresh(TabbedForm tabbedForm, Boolean isClient) {
			tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().setVisible(!isClient);
			tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().setVisible(!isClient);
		}
	}
}

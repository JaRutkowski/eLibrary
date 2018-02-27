package com.javafee.tabbedform;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Context;
import com.javafee.common.Constans.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedBookEventLoadingException;
import com.javafee.exception.RefusedLibraryEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.BookTableModel;
import com.javafee.model.ClientTableModel;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.model.VolumeTableReadingRoomModel;
import com.javafee.startform.LogInEvent;

public class TabLibraryEvent implements IActionForm {
	private TabbedForm tabbedForm;

	@SuppressWarnings("unused")
	public static TabLibraryEvent libraryEvent = null;
	private LibraryAddModEvent libraryAddModEvent;

	private List<Author> authorList = new ArrayList<Author>();
	private List<Category> categoryList = new ArrayList<Category>();
	private List<PublishingHouse> publishingHouseList = new ArrayList<PublishingHouse>();

	private TabLibraryEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabLibraryEvent getInstance(TabbedForm tabbedForm) {
		View.forceRefresh(tabbedForm, LogInEvent.getRole() == Role.CLIENT);
		((VolumeTableLoanModel)tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel()).reloadData();
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

		// TODO dla Volume
		// tabbedForm.getPanelVolume().getCockpitEditionPanelLoan().getBtnAdd()
		// .addActionListener(e -> onClickBtnAddBookLoan());
		// tabbedForm.getPanelVolume().getCockpitEditionPanelLoan().getBtnModify()
		// .addActionListener(e -> onClickBtnModifyBookLoan());
		// tabbedForm.getPanelVolume().getCockpitEditionPanelLoan().getBtnDelete()
		// .addActionListener(e -> onClickBtnDeleteBookLoan());
		// tabbedForm.getPanelVolume().getCockpitEditionPanelReadingRoom().getBtnAdd()
		// .addActionListener(e -> onClickBtnAddBookReadingRoom());
		// tabbedForm.getPanelVolume().getCockpitEditionPanelReadingRoom().getBtnModify()
		// .addActionListener(e -> onClickBtnModifyBookReadingRoom());
		// tabbedForm.getPanelVolume().getCockpitEditionPanelReadingRoom().getBtnDelete()
		// .addActionListener(e -> onClickBtnDeleteBookReadingRoom());

		// tabbedForm.getPanelBook().getBookFilterPanel().getAuthorAddMultiPanel().getBtnAddToList().addActionListener(e
		// -> onClickBtnAddToList(Author.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getAuthorAddMultiPanel().getBtnRemoveFromList().addActionListener(e
		// -> onClickBtnRemoveFromList(Author.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getAuthorAddMultiPanel().getBtnCheckList().addActionListener(e
		// -> onClickBtnCheckList(Author.class));
		//
		// tabbedForm.getPanelBook().getBookFilterPanel().getCategoryAddMultiPanel().getBtnAddToList().addActionListener(e
		// -> onClickBtnAddToList(Category.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getCategoryAddMultiPanel().getBtnRemoveFromList().addActionListener(e
		// -> onClickBtnRemoveFromList(Category.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getCategoryAddMultiPanel().getBtnCheckList().addActionListener(e
		// -> onClickBtnCheckList(Category.class));
		//
		// tabbedForm.getPanelBook().getBookFilterPanel().getPublishingHouseAddMultiPanel().getBtnAddToList().addActionListener(e
		// -> onClickBtnAddToList(PublishingHouse.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getPublishingHouseAddMultiPanel().getBtnRemoveFromList().addActionListener(e
		// -> onClickBtnRemoveFromList(PublishingHouse.class));
		// tabbedForm.getPanelBook().getBookFilterPanel().getPublishingHouseAddMultiPanel().getBtnCheckList().addActionListener(e
		// -> onClickBtnCheckList(PublishingHouse.class));
		//
		// tabbedForm.getPanelBook().getBookFilterPanel().getDecisionPanel().getBtnAccept().addActionListener(e
		// -> onClickBtnAccept());
		// tabbedForm.getPanelBook().getReloadPanel().getBtnReload().addActionListener(e
		// -> onClickBtnReload());
		//
		// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getBtnClear().addActionListener(e
		// -> onClickBtnClear());
		// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getCockpitEditionPanel().getBtnAdd().addActionListener(e
		// -> onClickBtnAdd());
		// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getCockpitEditionPanel().getBtnModify().addActionListener(e
		// -> onClickBtnModify());
		// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getCockpitEditionPanel().getBtnDelete().addActionListener(e
		// -> onClickBtnDelete());
		// tabbedForm.getPanelBook().getBookTable().getModel().addTableModelListener(e
		// -> reloadBookTable());
		// tabbedForm.getPanelBook().getBookTable().getSelectionModel().addListSelectionListener(e
		// -> {
		// if (!e.getValueIsAdjusting())
		// onBookTableListSelectionChange();
		// });
	}

	private void onClickBtnDeleteVolumeReadingRoom() {
		if (tabbedForm.getPanelLibrary().getReadingRoomBookTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getReadingRoomBookTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getReadingRoomBookTable().getSelectedRow());
			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Volume selectedVolume = ((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary().getReadingRoomBookTable()
							.getModel()).getVolume(selectedRowIndex);
//					Volume volumeShallowClone = (Volume) selectedVolume.clone();

					if (!selectedVolume.getIsLended() && !selectedVolume.getIsReserve()) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary().getReadingRoomBookTable().getModel()).remove(selectedVolume);
					} else {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarning"));
					}
				}

				// id_volume w lib_lend
			} 
		}else {
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
//					Volume volumeShallowClone = (Volume) selectedVolume.clone();

					if (!selectedVolume.getIsLended() && !selectedVolume.getIsReserve()) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedVolume);
						HibernateUtil.commitTransaction();
						((VolumeTableLoanModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel()).remove(selectedVolume);
					} else {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarningTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabLibraryEvent.deleteLoanedReservedVolumeWarning"));
					}
				}

				// id_volume w lib_lend
			} 
		}else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("libraryAddModEvent.notSelectedVolumeWarning"));
		}
	}

	private void onClickBtnModifyVolumeReadingRoom() {
		if (tabbedForm.getPanelLibrary().getReadingRoomBookTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelLibrary().getReadingRoomBookTable()
					.convertRowIndexToModel(tabbedForm.getPanelLibrary().getReadingRoomBookTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Volume selectedVolume = ((VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary()
						.getReadingRoomBookTable().getModel()).getVolume(selectedRowIndex);
				Volume volumeShallowClone = (Volume) selectedVolume.clone();

				Params.getInstance().add("selectedVolumeShallowClone", volumeShallowClone);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (libraryAddModEvent == null)
					libraryAddModEvent = new LibraryAddModEvent();
				libraryAddModEvent.control(Constans.Context.MODIFICATION, Context.READING_ROOM,
						(VolumeTableModel) tabbedForm.getPanelLibrary().getReadingRoomBookTable().getModel());

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

				Params.getInstance().add("selectedVolumeShallowClone", volumeShallowClone);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (libraryAddModEvent == null)
					libraryAddModEvent = new LibraryAddModEvent();
				libraryAddModEvent.control(Constans.Context.MODIFICATION, Context.LOAN,
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
		libraryAddModEvent.control(Constans.Context.ADDITION, Constans.Context.LOAN,
				(VolumeTableModel) tabbedForm.getPanelLibrary().getLoanVolumeTable().getModel());
	}

	private void onClickBtnAddVolumeReadingRoom() {
		if (libraryAddModEvent == null)
			libraryAddModEvent = new LibraryAddModEvent();
		libraryAddModEvent.control(Constans.Context.ADDITION, Constans.Context.READING_ROOM,
				(VolumeTableReadingRoomModel) tabbedForm.getPanelLibrary().getReadingRoomBookTable().getModel());

	}

	// private <T> void onClickBtnAddToList(Class<?> c) {
	// if (c.getSimpleName().equals("Author")) {
	// if (!authorList.contains((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem()))
	// authorList.add((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// if (c.getSimpleName().equals("Category")) {
	// if (!categoryList.contains((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem()))
	// categoryList.add((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// if (c.getSimpleName().equals("PublishingHouse")) {
	// if (!publishingHouseList.contains((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem()))
	// publishingHouseList.add((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.existingElementAddToListWarning1Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// }

	// private <T> void onClickBtnRemoveFromList(Class<?> c) {
	// if (c.getSimpleName().equals("Author")) {
	// if (authorList.contains((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem()))
	// authorList.remove((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// if (c.getSimpleName().equals("Category")) {
	// if (categoryList.contains((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem()))
	// categoryList.remove((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// if (c.getSimpleName().equals("PublishingHouse")) {
	// if (publishingHouseList.contains((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem()))
	// publishingHouseList.remove((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem());
	// else
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2"),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.notExistingElementInListWarning2Title"),
	// JOptionPane.WARNING_MESSAGE);
	// }
	// }

	// private <T> void onClickBtnCheckList(Class<?> c) {
	// StringBuilder author = new StringBuilder();
	// if (c.getSimpleName().equals("Author")) {
	// authorList.forEach(e -> {
	// if (e != null)
	// author.append(e + "<br> ");
	// else
	// author.append("-" + "<br> ");
	// });
	// Utils.displayOptionPane(author.toString(),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.authorElementInListInformation3Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	//
	// StringBuilder category = new StringBuilder();
	// if (c.getSimpleName().equals("Category")) {
	// categoryList.forEach(e -> {
	// if (e != null)
	// category.append(e + "<br> ");
	// else
	// category.append("-" + "<br> ");
	// });
	// Utils.displayOptionPane(category.toString(),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.categoryElementInListInformation3Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	//
	// StringBuilder publishingHouse = new StringBuilder();
	// if (c.getSimpleName().equals("PublishingHouse")) {
	// publishingHouseList.forEach(e -> {
	// if (e != null)
	// publishingHouse.append(e + "<br> ");
	// else
	// publishingHouse.append("-" + "<br> ");
	// });
	// Utils.displayOptionPane(publishingHouse.toString(),
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.publishingHouseElementInListInformation3Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// }

	// TODO dla Volume
	// private void onClickBtnAddBookLoan() {
	// if (bookAddModEvent == null)
	// bookAddModEvent = new BookAddModEvent();
	// bookAddModEvent.control(Constans.Context.ADDITION,
	// (VolumeTableLoanModel)
	// tabbedForm.getPanelVolume().getLoanVolumeTable().getModel());
	// }
	//
	// private void onClickBtnModifyBookLoan() {
	//
	// }
	//
	// private void onClickBtnDeleteBookLoan() {
	// if (tabbedForm.getPanelVolume().getLoanVolumeTable().getSelectedRow() != -1)
	// {
	// int selectedRowIndex = tabbedForm.getPanelVolume().getLoanVolumeTable()
	// .convertRowIndexToModel(tabbedForm.getPanelVolume().getLoanVolumeTable().getSelectedRow());
	// if (selectedRowIndex != -1) {
	// Volume selectedVolume = ((VolumeTableLoanModel)
	// tabbedForm.getPanelVolume().getLoanVolumeTable()
	// .getModel()).getVolume(selectedRowIndex);
	// if (!selectedVolume.getIsLended()) {
	// if (Utils.displayConfirmDialog(
	// SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
	// "") == JOptionPane.YES_OPTION) {
	//
	// HibernateUtil.beginTransaction();
	// HibernateUtil.getSession().delete(selectedVolume);
	// HibernateUtil.commitTransaction();
	// ((VolumeTableLoanModel)
	// tabbedForm.getPanelVolume().getLoanVolumeTable().getModel())
	// .remove(selectedVolume);
	//
	// }
	// } else {
	// LogGuiException.logWarning(
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.alreadyLendedLoanBookWarningTitle"),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.alreadyLendedLoanBookWarning"));
	// }
	// }
	// } else {
	// LogGuiException.logWarning(
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.notSelectedLoanBookWarningTitle"),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.notSelectedLoanBookWarning"));
	// }
	// }
	//
	// private void onClickBtnAddBookReadingRoom() {
	//
	// }
	//
	// private void onClickBtnModifyBookReadingRoom() {
	//
	// }
	//
	// private void onClickBtnDeleteBookReadingRoom() {
	//
	// }

	// private void onClickBtnAdd() {
	// if (bookAddModEvent == null)
	// bookAddModEvent = new BookAddModEvent();
	// bookAddModEvent.control(Constans.Context.ADDITION,
	// (BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel());
	// }

	// private void onClickBtnModify() {
	// if (tabbedForm.getPanelBook().getBookTable().getSelectedRow() != -1) {
	// int selectedRowIndex = tabbedForm.getPanelBook().getBookTable()
	// .convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());
	// if (selectedRowIndex != -1) {
	// Book selectedBook = ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel())
	// .getBook(selectedRowIndex);
	// Book bookShallowClone = (Book) selectedBook.clone();
	//
	// Params.getInstance().add("selectedBookShallowClone", bookShallowClone);
	// Params.getInstance().add("selectedRowIndex", selectedRowIndex);
	//
	// if (bookAddModEvent == null)
	// bookAddModEvent = new BookAddModEvent();
	// bookAddModEvent.control(Constans.Context.MODIFICATION,
	// (BookTableModel) tabbedForm.getPanelBook().getBookTable().getModel());
	//
	//
	// }
	// } else {
	// LogGuiException.logWarning(
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.notSelectedBookWarningTitle"),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("tabBookEvent.notSelectedBookWarning"));
	// }
	// }

	private void onClickBtnDelete() {

	}

	@Override
	public void initializeForm() {
		switchPerspectiveToClient(LogInEvent.getRole() == Role.CLIENT);
		// reloadBookFilterPanel();
		// switchPerspectiveToAdm(LogInEvent.getRole() == Role.ADMIN ||
		// LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
	}

	private void switchPerspectiveToClient(boolean b) {
		tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().setVisible(!b);
		tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().setVisible(!b);
	}

	public void setTabbedForm(TabbedForm tabbedForm) {
		this.tabbedForm = tabbedForm;
	}

	// private void reloadBookFilterPanel() {
	// reloadComboBoxAuthor();
	// reloadComboBoxCategory();
	// reloadComboBoxPublishingHouse();
	// }

	// private void reloadBookTable() {
	// tabbedForm.getPanelBook().getBookTable().repaint();
	// }

	// private void reloadTextAreaDetails() {
	// int selectedRowIndex =
	// tabbedForm.getPanelBook().getBookTable().convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());
	// if (selectedRowIndex != -1) {
	// Book selectedBook = ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).getBook(selectedRowIndex);
	//
	// StringBuilder author = new StringBuilder();
	// StringBuilder category = new StringBuilder();
	// StringBuilder publishingHouse = new StringBuilder();
	// selectedBook.getAuthor().forEach(e -> author.append(e.getSurname() != null ?
	// e.getSurname() : "-" + " " + e.getSurname() != null ? e.getName() :
	// "-").append("\n"));
	// selectedBook.getCategory().forEach(e -> category.append(e.getName() != null ?
	// e.getName() : "-").append("\n"));
	// selectedBook.getPublishingHouse().forEach(e ->
	// publishingHouse.append(e.getName() != null ? e.getName() :
	// "-").append("\n"));
	//
	// tabbedForm.getPanelBook().getBookFilterPanel().getTextAreaDetails()
	// .setText(SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.author")
	// + "\n" + author.toString() + "\n"
	// +
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.category")
	// + "\n" + category.toString() + "\n"
	// +
	// SystemProperties.getInstance().getResourceBundle().getString("tabBookEvent.publishingHouse")
	// + "\n" + publishingHouse.toString());
	// }
	// }

	// private void reloadComboBoxAuthor() {
	// DefaultComboBoxModel<Author> comboBoxAuthor = new
	// DefaultComboBoxModel<Author>();
	// HibernateDao<Author, Integer> author = new HibernateDao<Author,
	// Integer>(Author.class);
	// List<Author> authorListToSort = author.findAll();
	// authorListToSort.sort(Comparator.comparing(Author::getSurname,
	// Comparator.nullsFirst(Comparator.naturalOrder())));
	// authorListToSort.forEach(c -> comboBoxAuthor.addElement(c));
	// comboBoxAuthor.insertElementAt(null, 0);
	//
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().setModel(comboBoxAuthor);
	// }

	// private void reloadComboBoxCategory() {
	// DefaultComboBoxModel<Category> comboBoxCategory = new
	// DefaultComboBoxModel<Category>();
	// HibernateDao<Category, Integer> category = new HibernateDao<Category,
	// Integer>(Category.class);
	// List<Category> categoryListToSort = category.findAll();
	// categoryListToSort.sort(Comparator.comparing(Category::getName,
	// Comparator.nullsFirst(Comparator.naturalOrder())));
	// categoryListToSort.forEach(c -> comboBoxCategory.addElement(c));
	// comboBoxCategory.insertElementAt(null, 0);
	//
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().setModel(comboBoxCategory);
	// }

	// private void reloadComboBoxPublishingHouse() {
	// DefaultComboBoxModel<PublishingHouse> comboBoxPublishingHouse = new
	// DefaultComboBoxModel<PublishingHouse>();
	// HibernateDao<PublishingHouse, Integer> publishingHouse = new
	// HibernateDao<PublishingHouse, Integer>(PublishingHouse.class);
	// List<PublishingHouse> publishingHouseListToSort = publishingHouse.findAll();
	// publishingHouseListToSort.sort(Comparator.comparing(PublishingHouse::getName,
	// Comparator.nullsFirst(Comparator.naturalOrder())));
	// publishingHouseListToSort.forEach(c ->
	// comboBoxPublishingHouse.addElement(c));
	// comboBoxPublishingHouse.insertElementAt(null, 0);
	//
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().setModel(comboBoxPublishingHouse);
	// }

	// private void bookTableClearFilteres() {
	// HibernateDao<Book, Integer> bookDao = new HibernateDao<Book,
	// Integer>(Book.class);
	// ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).setBooks(bookDao.findAll());
	// }

	// private void clearTextAreaDetails() {
	// tabbedForm.getPanelBook().getBookFilterPanel().getTextAreaDetails().setText("");
	// }

	// @SuppressWarnings("deprecation")
	// private void onClickBtnDelete() {
	// int selectedRowIndex =
	// tabbedForm.getPanelBook().getBookTable().convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());
	// if (selectedRowIndex != -1) {
	// Book selectedBook = ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).getBook(selectedRowIndex);
	// @SuppressWarnings("unchecked")
	// List<Volume> result = (List<Volume>)
	// HibernateUtil.getSession().createQuery("from Volume where book.idBook =
	// :idBook").setParameter("idBook", selectedBook.getIdBook()).getResultList();
	// if (result == null) {
	// HibernateUtil.beginTransaction();
	// HibernateUtil.getSession().delete(Book.class.getName(), selectedBook);
	// HibernateUtil.getSession().createSQLQuery("delete from lib_book_author where
	// id_book = :idBook").setParameter("idBook", selectedBook.getIdBook());
	// HibernateUtil.getSession().createSQLQuery("delete from lib_book_category
	// where id_book = :idBook").setParameter("idBook", selectedBook.getIdBook());
	// HibernateUtil.getSession().createSQLQuery("delete from
	// lib_book_publishing_house where id_book = :idBook").setParameter("idBook",
	// selectedBook.getIdBook());
	// HibernateUtil.commitTransaction();
	//
	// ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).deleteBook(selectedBook);
	// ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).fireTableDataChanged();
	// reloadBookTable();
	// } else {
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.volumesExistsError3"),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.volumesExistsError3Title"),
	// JOptionPane.ERROR_MESSAGE);
	// }
	// }
	// }
	//
	// private void onClickBtnModify() {
	// if (validateTextBoxForNumber()) {
	// int selectedRowIndex =
	// tabbedForm.getPanelBook().getBookTable().convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow());
	// if (selectedRowIndex != -1) {
	// Book selectedBook = ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).getBook(selectedRowIndex);
	// Book bookShallowClone = (Book) selectedBook.clone();
	//
	// bookShallowClone.setTitle(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldTitle().getText());
	// bookShallowClone.setIsbnNumber(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldIsbnNumber().getText());
	// bookShallowClone.setNumberOfPage(!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText().isEmpty()
	// ?
	// Integer.parseInt(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText())
	// : null);
	// bookShallowClone.setNumberOfTomes(!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText().isEmpty()
	// ?
	// Integer.parseInt(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText())
	// : null);
	// if (authorList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem()
	// != null)
	// bookShallowClone.getAuthor().add((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem());
	// } else {
	// bookShallowClone.getAuthor().clear();
	// authorList.forEach(e -> bookShallowClone.getAuthor().add(e));
	// authorList.clear();
	// }
	// if (categoryList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem()
	// != null)
	// bookShallowClone.getCategory().add((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem());
	// } else {
	// bookShallowClone.getCategory().clear();
	// categoryList.forEach(e -> bookShallowClone.getCategory().add(e));
	// categoryList.clear();
	// }
	// if (publishingHouseList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem()
	// != null)
	// bookShallowClone.getPublishingHouse().add((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem());
	// } else {
	// bookShallowClone.getPublishingHouse().clear();
	// publishingHouseList.forEach(e ->
	// bookShallowClone.getPublishingHouse().add(e));
	// publishingHouseList.clear();
	// }
	//
	// HibernateUtil.beginTransaction();
	// HibernateUtil.getSession().evict(selectedBook);
	// HibernateUtil.getSession().update(Book.class.getName(), bookShallowClone);
	// HibernateUtil.commitTransaction();
	//
	// ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).setBook(selectedRowIndex,
	// bookShallowClone);
	// reloadBookTable();
	// }
	// } else {
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2"),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2Title"),
	// JOptionPane.ERROR_MESSAGE);
	// }
	// }
	//
	// private void onClickBtnAdd() {
	// if (validateTextBoxForNumber()) {
	// try {
	// HibernateUtil.beginTransaction();
	// Book book = new Book();
	// book.setTitle(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldTitle().getText());
	// book.setIsbnNumber(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldIsbnNumber().getText());
	// book.setNumberOfPage(!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText().isEmpty()
	// ?
	// Integer.parseInt(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText())
	// : null);
	// book.setNumberOfTomes(!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText().isEmpty()
	// ?
	// Integer.parseInt(tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText())
	// : null);
	// if (authorList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem()
	// != null)
	// book.getAuthor().add((Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem());
	// } else {
	// authorList.forEach(e -> book.getAuthor().add(e));
	// authorList.clear();
	// }
	// if (categoryList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem()
	// != null)
	// book.getCategory().add((Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem());
	// } else {
	// categoryList.forEach(e -> book.getCategory().add(e));
	// categoryList.clear();
	// }
	// if (publishingHouseList.isEmpty()) {
	// if
	// (tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem()
	// != null)
	// book.getPublishingHouse().add((PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem());
	// } else {
	// publishingHouseList.forEach(e -> book.getPublishingHouse().add(e));
	// publishingHouseList.clear();
	// }
	// HibernateUtil.getSession().save(book);
	// HibernateUtil.commitTransaction();
	//
	// BookTableModel btm = (BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel();
	// btm.add(book);
	// } catch (NumberFormatException e) {
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// } catch (RollbackException e) {
	// e.printStackTrace();
	// }
	// } else {
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2"),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2Title"),
	// JOptionPane.ERROR_MESSAGE);
	// }
	// }

	// private void onClickBtnClear() {
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldTitle().setText("");
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldIsbnNumber().setText("");
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().setText("");
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().setText("");
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().setSelectedIndex(0);
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().setSelectedIndex(0);
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().setSelectedIndex(0);
	// this.authorList.clear();
	// this.categoryList.clear();
	// this.publishingHouseList.clear();
	// }

	// private void onClickBtnReload() {
	// bookTableClearFilteres();
	// clearTextAreaDetails();
	// }

	// @SuppressWarnings("unchecked")
	// private void onClickBtnAccept() {
	// Author author = (Author)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxAuthor().getSelectedItem();
	// Category category = (Category)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxCategory().getSelectedItem();
	// PublishingHouse publishingHouse = (PublishingHouse)
	// tabbedForm.getPanelBook().getBookFilterPanel().getComboBoxPublishingHouse().getSelectedItem();
	//
	// if (Validator.validateBookFilter(author, category, publishingHouse)) {
	// Query query =
	// HibernateUtil.getSession().createQuery(prepareSelectQueryFilter(author,
	// category, publishingHouse));
	// setParametersSelectQueryFilter(author, category, publishingHouse, query);
	//
	// ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).setBooks((List<Book>)
	// query.getResultList());
	// } else {
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateBookFilterError1"),
	// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateBookFilterError1Title"),
	// JOptionPane.ERROR_MESSAGE);
	// bookTableClearFilteres();
	// }
	// }

	private String prepareSelectQueryFilter(Author author, Category category, PublishingHouse publishingHouse) {
		StringBuilder selectQueryFilter = new StringBuilder().append("from Book b where ");
		String prefix = "";

		if (author != null) {
			selectQueryFilter.append(":author in elements(b.author)");
			prefix = " and ";
		}

		if (category != null) {
			selectQueryFilter.append(prefix).append(":category in elements(b.category)");
			prefix = " and ";
		}

		if (publishingHouse != null)
			selectQueryFilter.append(prefix).append(":publishingHouse in elements(b.publishingHouse)");

		return selectQueryFilter.toString();
	}

	private void setParametersSelectQueryFilter(Author author, Category category, PublishingHouse publishingHouse,
			Query query) {
		if (author != null)
			query.setParameter("author", author);
		if (category != null)
			query.setParameter("category", category);
		if (publishingHouse != null)
			query.setParameter("publishingHouse", publishingHouse);
	}

	// private void onBookTableListSelectionChange() {
	// if
	// (tabbedForm.getPanelBook().getBookTable().convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow())
	// != -1) {
	// fillCloudListAndAdmBookDataPanel(tabbedForm.getPanelBook().getBookTable().convertRowIndexToModel(tabbedForm.getPanelBook().getBookTable().getSelectedRow()));
	// reloadTextAreaDetails();
	// }
	// }

	// private void fillCloudListAndAdmBookDataPanel(int selectedRow) {
	// System.out.println(selectedRow);
	// Book selectedBook = ((BookTableModel)
	// tabbedForm.getPanelBook().getBookTable().getModel()).getBook(selectedRow);
	// authorList.clear();
	// categoryList.clear();
	// publishingHouseList.clear();
	// authorList.addAll(selectedBook.getAuthor());
	// categoryList.addAll(selectedBook.getCategory());
	// publishingHouseList.addAll(selectedBook.getPublishingHouse());
	//
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldTitle().setText(selectedBook.getTitle());
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldIsbnNumber().setText(selectedBook.getIsbnNumber());
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().setText(selectedBook.getNumberOfPage()
	// != null ? selectedBook.getNumberOfPage().toString() : null);
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().setText(selectedBook.getNumberOfTomes()
	// != null ? selectedBook.getNumberOfTomes().toString() : null);
	// }

	// private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().setEnabled(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().setVisible(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getAuthorAddMultiPanel().setEnabled(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getAuthorAddMultiPanel().setVisible(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getCategoryAddMultiPanel().setEnabled(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getCategoryAddMultiPanel().setVisible(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getPublishingHouseAddMultiPanel().setEnabled(isAdminOrAccountant);
	// tabbedForm.getPanelBook().getBookFilterPanel().getPublishingHouseAddMultiPanel().setVisible(isAdminOrAccountant);
	// }

	// private boolean validateTextBoxForNumber() {
	// boolean result = true;
	// if
	// (!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText().isEmpty()
	// &&
	// !tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfPage().getText().trim().matches("^[0-9]+"))
	// result = false;
	// if
	// (!tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText().isEmpty()
	// &&
	// !tabbedForm.getPanelBook().getBookFilterPanel().getAdmBookDataPanel().getTextFieldNumberOfTomes().getText().trim().matches("^[0-9]+"))
	// result = false;
	// return result;
	// }
	
	private static class View{
		private static void forceRefresh(TabbedForm tabbedForm, Boolean isClient) {
			tabbedForm.getPanelLibrary().getCockpitEditionPanelLoan().setVisible(!isClient);
			tabbedForm.getPanelLibrary().getCockpitEditionPanelReadingRoom().setVisible(!isClient);
		}
	}
}

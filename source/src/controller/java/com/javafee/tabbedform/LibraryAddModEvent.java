package com.javafee.tabbedform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Constans.Context;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.AuthorTableModel;
import com.javafee.model.BookTableModel;
import com.javafee.model.CategoryTableModel;
import com.javafee.model.PublishingHouseTableModel;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.model.VolumeTableModel;
import com.javafee.model.VolumeTableReadingRoomModel;
import com.javafee.tabbedform.books.frames.BookAddModFrame;
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
				Params.getInstance().remove("selectedVolumeShallowClone");
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
		// if (validateRegistration())
		// registerNow();
	}

	private void modificateVolume(Context loanOrReadingRoom) {
		if (libraryAddModFrame.getBookTable().getSelectedRow() != -1) {
			Volume volumeShallowClone = (Volume) Params.getInstance().get("selectedVolumeShallowClone");
			// Params.getInstance().remove("selectedVolumeShallowClone");

			int selectedRowIndex = libraryAddModFrame.getBookTable()
					.convertRowIndexToModel(libraryAddModFrame.getBookTable().getSelectedRow());
			if(libraryAddModFrame.getTextFieldInventoryNumber().getText().length() == Constans.DATA_BASE_INVENTORY_NUMBER_LENGHT) {
				
			Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
					.getBook(selectedRowIndex);
			volumeShallowClone.setInventoryNumber(libraryAddModFrame.getTextFieldInventoryNumber().getText());
			volumeShallowClone.setBook(selectedBook);

			HibernateUtil.beginTransaction();
			if (loanOrReadingRoom == Context.LOAN) {
				HibernateUtil.getSession()
						.evict(volumeTableLoanModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
				HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
			} else if (loanOrReadingRoom == Context.READING_ROOM) {
				HibernateUtil.getSession().evict(
						volumeTableReadingRoomModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
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

			// Params.getInstance().remove("selectedRowIndex");
			}else {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("libraryAddModEvent.incorrectInventoryNumberWarning")
						);
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
				if(inventoryNumber.length() == Constans.DATA_BASE_INVENTORY_NUMBER_LENGHT) {
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
									.getString("libraryAddModEvent.incorrectInventoryNumberWarning")
							);
				}
			}
		}
		//
		// try {
		// Book book = libraryAddModFrame.getBookTable().getSele
		//
		// List<Author> authorList = getSelectedAuthors();
		// List<Category> categoryList = getSelectedCategories();
		// List<PublishingHouse> publishingHouseList = getSelectedPublishingHouses();
		// String title =
		// libraryAddModFrame.getBookDataPanel().getTextFieldTitle().getText();
		// String isbnNumber =
		// libraryAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText();
		// Integer numberOfPage =
		// libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText() !=
		// null
		// ?
		// Integer.parseInt(libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
		// : null;
		// Integer numberOfTomes =
		// libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText()
		// != null
		// ?
		// Integer.parseInt(libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
		// : null;
		//
		// HibernateUtil.beginTransaction();
		// Book book = new Book();
		// authorList.forEach(e -> book.getAuthor().add(e));
		// categoryList.forEach(e -> book.getCategory().add(e));
		// publishingHouseList.forEach(e -> book.getPublishingHouse().add(e));
		// book.setTitle(title);
		// book.setIsbnNumber(isbnNumber);
		// book.setNumberOfPage(numberOfPage);
		// book.setNumberOfTomes(numberOfTomes);
		// HibernateUtil.getSession().save(book);
		// HibernateUtil.commitTransaction();
		//
		// bookTableModel.add(book);
		// } catch (NumberFormatException e) {
		// LogGuiException.logError(
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("bookAddModEvent.savingBookParseErrorTitle"),
		// SystemProperties.getInstance().getResourceBundle()
		// .getString("bookAddModEvent.savingBookParseError"),
		// e);
		// }
	}

	// private List<Author> getSelectedAuthors() {
	// List<Author> authorList = new ArrayList<Author>();
	// for (int index : libraryAddModFrame.getAuthorTable().getSelectedRows())
	// authorList.add(((AuthorTableModel)
	// libraryAddModFrame.getAuthorTable().getModel()).getAuthor(index));
	// return !authorList.isEmpty() ? authorList : null;
	// }
	//
	// private List<Category> getSelectedCategories() {
	// List<Category> categoryList = new ArrayList<Category>();
	// for (int index : libraryAddModFrame.getCategoryTable().getSelectedRows())
	// categoryList
	// .add(((CategoryTableModel)
	// libraryAddModFrame.getCategoryTable().getModel()).getCategory(index));
	// return !categoryList.isEmpty() ? categoryList : null;
	// }
	//
	// private List<PublishingHouse> getSelectedPublishingHouses() {
	// List<PublishingHouse> publishingHouseList = new ArrayList<PublishingHouse>();
	// for (int index :
	// libraryAddModFrame.getPublishingHouseTable().getSelectedRows())
	// publishingHouseList
	// .add(((PublishingHouseTableModel)
	// libraryAddModFrame.getPublishingHouseTable().getModel())
	// .getPublishingHouse(index));
	// return !publishingHouseList.isEmpty() ? publishingHouseList : null;
	// }

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

		// ((Book)
		// Params.getInstance().get("selectedVolumeShallowClone")).getIdBook()().forEach(e
		// -> authorIndexes
		// .add(((AuthorTableModel)
		// libraryAddModFrame.getAuthorTable().getModel()).getAuthors().indexOf(e)));
		//
		Integer selectedBookIndex = ((BookTableModel) libraryAddModFrame.getBookTable().getModel()).getBooks()
				.indexOf(((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getBook());
		libraryAddModFrame.getBookTable().setRowSelectionInterval(selectedBookIndex, selectedBookIndex);
		//
		//
		// ((Book)
		// Params.getInstance().get("selectedBookShallowClone")).getCategory().forEach(e
		// -> categoryIndexes.add(
		// ((CategoryTableModel)
		// libraryAddModFrame.getCategoryTable().getModel()).getCategories().indexOf(e)));
		// categoryIndexes.forEach(e ->
		// libraryAddModFrame.getCategoryTable().addRowSelectionInterval(e, e));
		// ((Book)
		// Params.getInstance().get("selectedBookShallowClone")).getPublishingHouse()
		// .forEach(e -> publishingHouseIndexes
		// .add(((PublishingHouseTableModel)
		// libraryAddModFrame.getPublishingHouseTable().getModel())
		// .getPublishingHouses().indexOf(e)));
		// publishingHouseIndexes.forEach(e ->
		// libraryAddModFrame.getPublishingHouseTable().addRowSelectionInterval(e, e));
	}

	private void fillTextBoxInventoryNumber() {
		libraryAddModFrame.getTextFieldInventoryNumber()
				.setText(((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getInventoryNumber() != null
						? ((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getInventoryNumber()
								.toString()
						: "");
	}

	// private void reloadRegistrationPanel() {
	// reloadComboBoxCity();
	// fillRegistrationPanel();
	// }

	// private void reloadComboBoxCity() {
	// DefaultComboBoxModel<City> comboBoxCity = new DefaultComboBoxModel<City>();
	// HibernateDao<City, Integer> city = new HibernateDao<City,
	// Integer>(City.class);
	// List<City> cityListToSort = city.findAll();
	// cityListToSort.sort(Comparator.comparing(City::getName,
	// Comparator.nullsFirst(Comparator.naturalOrder())));
	// cityListToSort.forEach(c -> comboBoxCity.addElement(c));
	//
	// volumeAddModFrame.getClientDataPanel().getComboBoxCity().setModel(comboBoxCity);
	// }

	// private void fillRegistrationPanel() {
	// volumeAddModFrame.getClientDataPanel().getTextFieldPeselNumber()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getPeselNumber() !=
	// null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getPeselNumber().toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getTextFieldDocumentNumber()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber()
	// != null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber()
	// .toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getTextFieldLogin()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getLogin() != null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getLogin().toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getTextFieldName()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getName() != null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getName().toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getTextFieldSurname()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSurname() != null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSurname().toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getTextFieldAddress()
	// .setText(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getAddress() != null
	// ? ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getAddress().toString()
	// : "");
	//
	// volumeAddModFrame.getClientDataPanel().getComboBoxCity()
	// .setSelectedItem(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getCity() != null
	// ? ((Client) Params.getInstance().get("selectedClientShallowClone")).getCity()
	// : null);
	//
	// if (((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSex() != null
	// &&
	// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredMaleVal")
	// .equals(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
	// .setSelected(volumeAddModFrame.getClientDataPanel().getRadioButtonMale().getModel(),
	// true);
	// else if (((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSex() != null
	// &&
	// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredFemaleVal")
	// .equals(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
	// .setSelected(volumeAddModFrame.getClientDataPanel().getRadioButtonFemale().getModel(),
	// true);
	//
	// try {
	// volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate()
	// .setDate(((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getBirthDate() !=
	// null
	// ?
	// Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(
	// ((Client)
	// Params.getInstance().get("selectedClientShallowClone")).getBirthDate()))
	// : null);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// }

	// private void registerNow() {
	// try {
	// Character sex =
	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection()
	// != null
	// ?
	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection().getActionCommand()
	// .charAt(0)
	// : null;
	// Date birthDate =
	// volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate() !=
	// null
	// ? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT
	// .format(volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate()))
	// : null;
	//
	// RegistrationEvent.forceClearRegistrationEvenet();
	// if (birthDate != null && birthDate.before(new Date())) {
	// registrationEvent = RegistrationEvent.getInstance(
	// volumeAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText(),
	// volumeAddModFrame.getClientDataPanel().getTextFieldDocumentNumber().getText(),
	// volumeAddModFrame.getClientDataPanel().getTextFieldName().getText(),
	// volumeAddModFrame.getClientDataPanel().getTextFieldSurname().getText(),
	// volumeAddModFrame.getClientDataPanel().getTextFieldAddress().getText(),
	// (City)
	// volumeAddModFrame.getClientDataPanel().getComboBoxCity().getSelectedItem(),
	// sex,
	// birthDate,
	// volumeAddModFrame.getClientDataPanel().getTextFieldLogin().getText(),
	// String.valueOf(volumeAddModFrame.getClientDataPanel().getPasswordField().getPassword()),
	// Role.CLIENT);
	//
	// volumeTableLoanModel.add((Client) RegistrationEvent.userData);
	// } else {
	// Utils.displayOptionPane(
	// "Podana data urodzenia nie jest wczeœniejszza od be¿¹cej, wype³nione muszê
	// byæ dane nazwiska, imienia, numeru pesel.",
	// "Z³a data", JOptionPane.INFORMATION_MESSAGE);
	// }
	// } catch (RefusedRegistrationException e) {
	// StringBuilder errorBuilder = new StringBuilder();
	//
	// if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError5"));
	// Params.getInstance().remove("ALREADY_REGISTERED");
	// }
	// if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError6"));
	// }
	// if (Params.getInstance().get("WEAK_PASSWORD") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError7"));
	// }
	//
	// LogGuiException.logError(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
	// errorBuilder.toString(), e);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// if (registrationEvent != null)
	// Utils.displayOptionPane(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// }

	// private boolean validateRegistration() {
	// boolean result = false;
	//
	// if
	// (volumeAddModFrame.getClientDataPanel().getTextFieldLogin().getText().isEmpty()
	// ||
	// volumeAddModFrame.getClientDataPanel().getPasswordField().getPassword().length
	// == 0)
	// JOptionPane.showMessageDialog(volumeAddModFrame,
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("startForm.validateRegistrationError8"),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("startForm.validateRegistrationError8Title"),
	// JOptionPane.ERROR_MESSAGE);
	// else
	// result = true;
	//
	// return result;
	// }

	private boolean validateTablesSelection() {
		boolean result = false;

		if (libraryAddModFrame.getBookTable().getSelectedRowCount() != 0) {
			result = true;
		}

		return result;
	}
}


//package com.javafee.tabbedform;
//
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.event.ContainerEvent;
//import java.awt.event.ContainerListener;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//import javax.swing.JOptionPane;
//
//import com.javafee.common.Params;
//import com.javafee.common.SystemProperties;
//import com.javafee.common.Utils;
//import com.javafee.common.Constans.Context;
//import com.javafee.exception.LogGuiException;
//import com.javafee.hibernate.dao.HibernateUtil;
//import com.javafee.hibernate.dto.library.Author;
//import com.javafee.hibernate.dto.library.Book;
//import com.javafee.hibernate.dto.library.Category;
//import com.javafee.hibernate.dto.library.Client;
//import com.javafee.hibernate.dto.library.PublishingHouse;
//import com.javafee.hibernate.dto.library.Volume;
//import com.javafee.model.AuthorTableModel;
//import com.javafee.model.BookTableModel;
//import com.javafee.model.CategoryTableModel;
//import com.javafee.model.PublishingHouseTableModel;
//import com.javafee.model.VolumeTableLoanModel;
//import com.javafee.model.VolumeTableModel;
//import com.javafee.model.VolumeTableReadingRoomModel;
//import com.javafee.tabbedform.books.frames.BookAddModFrame;
//import com.javafee.tabbedform.library.frames.LibraryAddModFrame;
//
//public class LibraryAddModEvent {
//
//	private LibraryAddModFrame libraryAddModFrame;
//
//	private VolumeTableLoanModel volumeTableLoanModel;
//	private VolumeTableReadingRoomModel volumeTableReadingRoomModel;
//
//	public void control(Context context, Context loanOrReadingRoom, VolumeTableModel volumeTableModel) {
//		if (loanOrReadingRoom == Context.LOAN)
//			volumeTableLoanModel = (VolumeTableLoanModel) volumeTableModel;
//		else if (loanOrReadingRoom == Context.READING_ROOM)
//			volumeTableReadingRoomModel = (VolumeTableReadingRoomModel) volumeTableModel;
//
//		openLibraryAddModFrame(context);
//
//		libraryAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
//				.addActionListener(e -> onClickBtnAccept(context, loanOrReadingRoom));
//	}
//
//	private void onClickBtnAccept(Context context, Context loanOrReadingRoom) {
//		if (context == Context.ADDITION) {
//			if (validateTablesSelection()) {
//				createVolume(loanOrReadingRoom);
//			} else {
//				LogGuiException.logWarning(
//						SystemProperties.getInstance().getResourceBundle()
//								.getString("bookAddModEvent.notSelectedTablesWarningTitle"),
//						SystemProperties.getInstance().getResourceBundle()
//								.getString("bookAddModEvent.notSelectedTablesWarning"));
//			}
//		} else if (context == Context.MODIFICATION) {
//			modificateVolume(loanOrReadingRoom);
//		}
//		// if (validateRegistration())
//		// registerNow();
//	}
//
//	private void modificateVolume(Context loanOrReadingRoom) {
//		if (libraryAddModFrame.getBookTable().getSelectedRow() != -1) {
//			Volume volumeShallowClone = (Volume) Params.getInstance().get("selectedVolumeShallowClone");
//			Params.getInstance().remove("selectedVolumeShallowClone");
//
//			int selectedRowIndex = libraryAddModFrame.getBookTable()
//					.convertRowIndexToModel(libraryAddModFrame.getBookTable().getSelectedRow());
//
//			Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
//					.getBook(selectedRowIndex);
//			volumeShallowClone.setInventoryNumber(libraryAddModFrame.getTextFieldInventoryNumber().getText());
//			volumeShallowClone.setBook(selectedBook);
//
//			HibernateUtil.beginTransaction();
//			if (loanOrReadingRoom == Context.LOAN) {
//				HibernateUtil.getSession()
//				.evict(volumeTableLoanModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
//				HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
//			} else if (loanOrReadingRoom == Context.READING_ROOM) {
//				HibernateUtil.getSession().evict(
//						volumeTableReadingRoomModel.getVolume((Integer) Params.getInstance().get("selectedRowIndex")));
//				HibernateUtil.getSession().update(Volume.class.getName(), volumeShallowClone);
//			}
//			HibernateUtil.commitTransaction();
//
//			if (loanOrReadingRoom == Context.LOAN) {
//				volumeTableLoanModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
//						volumeShallowClone);
//				volumeTableLoanModel.fireTableDataChanged();
//			} else if (loanOrReadingRoom == Context.READING_ROOM) {
//				volumeTableReadingRoomModel.setVolume((Integer) Params.getInstance().get("selectedRowIndex"),
//						volumeShallowClone);
//				volumeTableReadingRoomModel.fireTableDataChanged();
//			}
//			
//			Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
//					.getString("libraryAddModEvent.updatingVolumeSuccess"), SystemProperties.getInstance().getResourceBundle()
//					.getString("libraryAddModEvent.updatingVolumeSuccessTitle"), JOptionPane.INFORMATION_MESSAGE);
//			
//			libraryAddModFrame.dispose();
//			
//			Params.getInstance().remove("selectedRowIndex");
//
//		} else {
//			LogGuiException.logWarning(
//					SystemProperties.getInstance().getResourceBundle()
//							.getString("libraryAddModEvent.notSelectedBookWarningTitle"),
//					SystemProperties.getInstance().getResourceBundle()
//							.getString("libraryAddModEvent.notSelectedBookWarning"));
//		}
//		// ((BookTableModel)
//		// tabbedForm.getPanelBook().getBookTable().getModel()).setBook(selectedRowIndex,
//		// bookShallowClone);
//		// reloadBookTable();
//		// }
//		// } else {
//		// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
//		// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2"),
//		// SystemProperties.getInstance().getResourceBundle().getString("bookFilterPanel.validateTextBoxForNumberError2Title"),
//		// JOptionPane.ERROR_MESSAGE);
//		// }
//	}
//
//	private void createVolume(Context loanOrReadingRoom) {
//		if (libraryAddModFrame.getBookTable().getSelectedRow() != -1) {
//			int selectedRowIndex = libraryAddModFrame.getBookTable()
//					.convertRowIndexToModel(libraryAddModFrame.getBookTable().getSelectedRow());
//			if (selectedRowIndex != -1) {
//				Book selectedBook = ((BookTableModel) libraryAddModFrame.getBookTable().getModel())
//						.getBook(selectedRowIndex);
//				String inventoryNumber = libraryAddModFrame.getTextFieldInventoryNumber().getText();
//
//				HibernateUtil.beginTransaction();
//				Volume volume = new Volume();
//				volume.setBook(selectedBook);
//				volume.setInventoryNumber(inventoryNumber);
//				if (loanOrReadingRoom == Context.READING_ROOM)
//					volume.setIsReadingRoom(true);
//
//				HibernateUtil.getSession().save(volume);
//				HibernateUtil.commitTransaction();
//
//				if (loanOrReadingRoom == Context.LOAN)
//					volumeTableLoanModel.add(volume);
//				else if (loanOrReadingRoom == Context.READING_ROOM)
//					volumeTableReadingRoomModel.add(volume);
//				
//				Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
//						.getString("libraryAddModEvent.savingVolumeSuccess"), SystemProperties.getInstance().getResourceBundle()
//						.getString("libraryAddModEvent.savingVolumeSuccessTitle"), JOptionPane.INFORMATION_MESSAGE);
//				
//				libraryAddModFrame.dispose();
//			}
//		}
//		//
//		// try {
//		// Book book = libraryAddModFrame.getBookTable().getSele
//		//
//		// List<Author> authorList = getSelectedAuthors();
//		// List<Category> categoryList = getSelectedCategories();
//		// List<PublishingHouse> publishingHouseList = getSelectedPublishingHouses();
//		// String title =
//		// libraryAddModFrame.getBookDataPanel().getTextFieldTitle().getText();
//		// String isbnNumber =
//		// libraryAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText();
//		// Integer numberOfPage =
//		// libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText() !=
//		// null
//		// ?
//		// Integer.parseInt(libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
//		// : null;
//		// Integer numberOfTomes =
//		// libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText()
//		// != null
//		// ?
//		// Integer.parseInt(libraryAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
//		// : null;
//		//
//		// HibernateUtil.beginTransaction();
//		// Book book = new Book();
//		// authorList.forEach(e -> book.getAuthor().add(e));
//		// categoryList.forEach(e -> book.getCategory().add(e));
//		// publishingHouseList.forEach(e -> book.getPublishingHouse().add(e));
//		// book.setTitle(title);
//		// book.setIsbnNumber(isbnNumber);
//		// book.setNumberOfPage(numberOfPage);
//		// book.setNumberOfTomes(numberOfTomes);
//		// HibernateUtil.getSession().save(book);
//		// HibernateUtil.commitTransaction();
//		//
//		// bookTableModel.add(book);
//		// } catch (NumberFormatException e) {
//		// LogGuiException.logError(
//		// SystemProperties.getInstance().getResourceBundle()
//		// .getString("bookAddModEvent.savingBookParseErrorTitle"),
//		// SystemProperties.getInstance().getResourceBundle()
//		// .getString("bookAddModEvent.savingBookParseError"),
//		// e);
//		// }
//	}
//
//	// private List<Author> getSelectedAuthors() {
//	// List<Author> authorList = new ArrayList<Author>();
//	// for (int index : libraryAddModFrame.getAuthorTable().getSelectedRows())
//	// authorList.add(((AuthorTableModel)
//	// libraryAddModFrame.getAuthorTable().getModel()).getAuthor(index));
//	// return !authorList.isEmpty() ? authorList : null;
//	// }
//	//
//	// private List<Category> getSelectedCategories() {
//	// List<Category> categoryList = new ArrayList<Category>();
//	// for (int index : libraryAddModFrame.getCategoryTable().getSelectedRows())
//	// categoryList
//	// .add(((CategoryTableModel)
//	// libraryAddModFrame.getCategoryTable().getModel()).getCategory(index));
//	// return !categoryList.isEmpty() ? categoryList : null;
//	// }
//	//
//	// private List<PublishingHouse> getSelectedPublishingHouses() {
//	// List<PublishingHouse> publishingHouseList = new ArrayList<PublishingHouse>();
//	// for (int index :
//	// libraryAddModFrame.getPublishingHouseTable().getSelectedRows())
//	// publishingHouseList
//	// .add(((PublishingHouseTableModel)
//	// libraryAddModFrame.getPublishingHouseTable().getModel())
//	// .getPublishingHouse(index));
//	// return !publishingHouseList.isEmpty() ? publishingHouseList : null;
//	// }
//
//	private void openLibraryAddModFrame(Context context) {
//		if (libraryAddModFrame == null || (libraryAddModFrame != null && !libraryAddModFrame.isDisplayable())) {
//			libraryAddModFrame = new LibraryAddModFrame();
//			if (context == Context.MODIFICATION) {
//				fillTextBoxInventoryNumber();
//				reloadTable();
//			}
//			libraryAddModFrame.setVisible(true);
//		} else {
//			libraryAddModFrame.toFront();
//		}
//	}
//
//	private void reloadTable() {
//
//		// ((Book)
//		// Params.getInstance().get("selectedVolumeShallowClone")).getIdBook()().forEach(e
//		// -> authorIndexes
//		// .add(((AuthorTableModel)
//		// libraryAddModFrame.getAuthorTable().getModel()).getAuthors().indexOf(e)));
//		//
//		Integer selectedBookIndex = ((BookTableModel) libraryAddModFrame.getBookTable().getModel()).getBooks()
//				.indexOf(((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getBook());
//		libraryAddModFrame.getBookTable().setRowSelectionInterval(selectedBookIndex, selectedBookIndex);
//		//
//		//
//		// ((Book)
//		// Params.getInstance().get("selectedBookShallowClone")).getCategory().forEach(e
//		// -> categoryIndexes.add(
//		// ((CategoryTableModel)
//		// libraryAddModFrame.getCategoryTable().getModel()).getCategories().indexOf(e)));
//		// categoryIndexes.forEach(e ->
//		// libraryAddModFrame.getCategoryTable().addRowSelectionInterval(e, e));
//		// ((Book)
//		// Params.getInstance().get("selectedBookShallowClone")).getPublishingHouse()
//		// .forEach(e -> publishingHouseIndexes
//		// .add(((PublishingHouseTableModel)
//		// libraryAddModFrame.getPublishingHouseTable().getModel())
//		// .getPublishingHouses().indexOf(e)));
//		// publishingHouseIndexes.forEach(e ->
//		// libraryAddModFrame.getPublishingHouseTable().addRowSelectionInterval(e, e));
//	}
//
//	private void fillTextBoxInventoryNumber() {
//		libraryAddModFrame.getTextFieldInventoryNumber()
//				.setText(((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getInventoryNumber() != null
//						? ((Volume) Params.getInstance().get("selectedVolumeShallowClone")).getInventoryNumber()
//								.toString()
//						: "");
//	}
//
//	// private void reloadRegistrationPanel() {
//	// reloadComboBoxCity();
//	// fillRegistrationPanel();
//	// }
//
//	// private void reloadComboBoxCity() {
//	// DefaultComboBoxModel<City> comboBoxCity = new DefaultComboBoxModel<City>();
//	// HibernateDao<City, Integer> city = new HibernateDao<City,
//	// Integer>(City.class);
//	// List<City> cityListToSort = city.findAll();
//	// cityListToSort.sort(Comparator.comparing(City::getName,
//	// Comparator.nullsFirst(Comparator.naturalOrder())));
//	// cityListToSort.forEach(c -> comboBoxCity.addElement(c));
//	//
//	// volumeAddModFrame.getClientDataPanel().getComboBoxCity().setModel(comboBoxCity);
//	// }
//
//	// private void fillRegistrationPanel() {
//	// volumeAddModFrame.getClientDataPanel().getTextFieldPeselNumber()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getPeselNumber() !=
//	// null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getPeselNumber().toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getTextFieldDocumentNumber()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber()
//	// != null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getDocumentNumber()
//	// .toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getTextFieldLogin()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getLogin() != null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getLogin().toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getTextFieldName()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getName() != null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getName().toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getTextFieldSurname()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSurname() != null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSurname().toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getTextFieldAddress()
//	// .setText(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getAddress() != null
//	// ? ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getAddress().toString()
//	// : "");
//	//
//	// volumeAddModFrame.getClientDataPanel().getComboBoxCity()
//	// .setSelectedItem(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getCity() != null
//	// ? ((Client) Params.getInstance().get("selectedClientShallowClone")).getCity()
//	// : null);
//	//
//	// if (((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSex() != null
//	// &&
//	// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredMaleVal")
//	// .equals(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
//	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
//	// .setSelected(volumeAddModFrame.getClientDataPanel().getRadioButtonMale().getModel(),
//	// true);
//	// else if (((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSex() != null
//	// &&
//	// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredFemaleVal")
//	// .equals(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getSex().toString()))
//	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex()
//	// .setSelected(volumeAddModFrame.getClientDataPanel().getRadioButtonFemale().getModel(),
//	// true);
//	//
//	// try {
//	// volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate()
//	// .setDate(((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getBirthDate() !=
//	// null
//	// ?
//	// Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(
//	// ((Client)
//	// Params.getInstance().get("selectedClientShallowClone")).getBirthDate()))
//	// : null);
//	// } catch (ParseException e) {
//	// e.printStackTrace();
//	// }
//	// }
//
//	// private void registerNow() {
//	// try {
//	// Character sex =
//	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection()
//	// != null
//	// ?
//	// volumeAddModFrame.getClientDataPanel().getGroupRadioButtonSex().getSelection().getActionCommand()
//	// .charAt(0)
//	// : null;
//	// Date birthDate =
//	// volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate() !=
//	// null
//	// ? Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT
//	// .format(volumeAddModFrame.getClientDataPanel().getDateChooserBirthDate().getDate()))
//	// : null;
//	//
//	// RegistrationEvent.forceClearRegistrationEvenet();
//	// if (birthDate != null && birthDate.before(new Date())) {
//	// registrationEvent = RegistrationEvent.getInstance(
//	// volumeAddModFrame.getClientDataPanel().getTextFieldPeselNumber().getText(),
//	// volumeAddModFrame.getClientDataPanel().getTextFieldDocumentNumber().getText(),
//	// volumeAddModFrame.getClientDataPanel().getTextFieldName().getText(),
//	// volumeAddModFrame.getClientDataPanel().getTextFieldSurname().getText(),
//	// volumeAddModFrame.getClientDataPanel().getTextFieldAddress().getText(),
//	// (City)
//	// volumeAddModFrame.getClientDataPanel().getComboBoxCity().getSelectedItem(),
//	// sex,
//	// birthDate,
//	// volumeAddModFrame.getClientDataPanel().getTextFieldLogin().getText(),
//	// String.valueOf(volumeAddModFrame.getClientDataPanel().getPasswordField().getPassword()),
//	// Role.CLIENT);
//	//
//	// volumeTableLoanModel.add((Client) RegistrationEvent.userData);
//	// } else {
//	// Utils.displayOptionPane(
//	// "Podana data urodzenia nie jest wczeœniejszza od be¿¹cej, wype³nione muszê
//	// byæ dane nazwiska, imienia, numeru pesel.",
//	// "Z³a data", JOptionPane.INFORMATION_MESSAGE);
//	// }
//	// } catch (RefusedRegistrationException e) {
//	// StringBuilder errorBuilder = new StringBuilder();
//	//
//	// if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
//	// errorBuilder.append(
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError5"));
//	// Params.getInstance().remove("ALREADY_REGISTERED");
//	// }
//	// if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
//	// errorBuilder.append(
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError6"));
//	// }
//	// if (Params.getInstance().get("WEAK_PASSWORD") != null) {
//	// errorBuilder.append(
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError7"));
//	// }
//	//
//	// LogGuiException.logError(
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
//	// errorBuilder.toString(), e);
//	// } catch (ParseException e) {
//	// e.printStackTrace();
//	// }
//	//
//	// if (registrationEvent != null)
//	// Utils.displayOptionPane(
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
//	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2Title"),
//	// JOptionPane.INFORMATION_MESSAGE);
//	// }
//
//	// private boolean validateRegistration() {
//	// boolean result = false;
//	//
//	// if
//	// (volumeAddModFrame.getClientDataPanel().getTextFieldLogin().getText().isEmpty()
//	// ||
//	// volumeAddModFrame.getClientDataPanel().getPasswordField().getPassword().length
//	// == 0)
//	// JOptionPane.showMessageDialog(volumeAddModFrame,
//	// SystemProperties.getInstance().getResourceBundle()
//	// .getString("startForm.validateRegistrationError8"),
//	// SystemProperties.getInstance().getResourceBundle()
//	// .getString("startForm.validateRegistrationError8Title"),
//	// JOptionPane.ERROR_MESSAGE);
//	// else
//	// result = true;
//	//
//	// return result;
//	// }
//
//	private boolean validateTablesSelection() {
//		boolean result = false;
//
//		if (libraryAddModFrame.getBookTable().getSelectedRowCount() != 0) {
//			result = true;
//		}
//
//		return result;
//	}
//}

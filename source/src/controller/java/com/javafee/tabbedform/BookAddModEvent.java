package com.javafee.tabbedform;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

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
import com.javafee.model.AuthorTableModel;
import com.javafee.model.BookTableModel;
import com.javafee.model.CategoryTableModel;
import com.javafee.model.PublishingHouseTableModel;
import com.javafee.model.VolumeTableLoanModel;
import com.javafee.tabbedform.books.frames.BookAddModFrame;

public class BookAddModEvent {

	private BookAddModFrame bookAddModFrame;

	private BookTableModel bookTableModel;

	public void control(Context context, BookTableModel bookTableModel) {
		this.bookTableModel = bookTableModel;
		openBookAddModFrame(context);

		bookAddModFrame.getCockpitConfirmationPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept(context));
	}

	private void onClickBtnAccept(Context context) {
		if (context == Context.ADDITION) {
			if (validateTablesSelection()) {
				createBook();
			} else {
				Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
						.getString("bookAddModEvent.savingBookParseError"), SystemProperties.getInstance().getResourceBundle()
						.getString("bookAddModEvent.notSelectedTablesWarningTitle"), JOptionPane.ERROR_MESSAGE);
			}
		} else if (context == Context.MODIFICATION) {
			modificateBook();
		}
		// if (validateRegistration())
		// registerNow();
	}

	private void modificateBook() {
		try {
			Book bookShallowClone = (Book) Params.getInstance().get("selectedBookShallowClone");
			Params.getInstance().remove("selectedBookShallowClone");

			List<Author> authorList = getSelectedAuthors();
			List<Category> categoryList = getSelectedCategories();
			List<PublishingHouse> publishingHouseList = getSelectedPublishingHouses();
			String title = bookAddModFrame.getBookDataPanel().getTextFieldTitle().getText();
			String isbnNumber = bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText();
			Integer numberOfPage = bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText() != null
					? Integer.parseInt(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
					: null;
			Integer numberOfTomes = bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText() != null
					? Integer.parseInt(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
					: null;

			authorList.forEach(e -> bookShallowClone.getAuthor().add(e));
			categoryList.forEach(e -> bookShallowClone.getCategory().add(e));
			publishingHouseList.forEach(e -> bookShallowClone.getPublishingHouse().add(e));
			bookShallowClone.setTitle(title);
			bookShallowClone.setIsbnNumber(isbnNumber);
			bookShallowClone.setNumberOfPage(numberOfPage);
			bookShallowClone.setNumberOfTomes(numberOfTomes);

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession()
					.evict(bookTableModel.getBook((Integer) Params.getInstance().get("selectedRowIndex")));
			HibernateUtil.getSession().update(Book.class.getName(), bookShallowClone);
			HibernateUtil.commitTransaction();

			bookTableModel.setBook((Integer) Params.getInstance().get("selectedRowIndex"), bookShallowClone);
			bookTableModel.fireTableDataChanged();
			
			Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
					.getString("bookAddModEvent.updatingBookSuccess"), SystemProperties.getInstance().getResourceBundle()
					.getString("bookAddModEvent.updatingBookSuccessTitle"), JOptionPane.INFORMATION_MESSAGE);
			
			bookAddModFrame.dispose();
			
			Params.getInstance().remove("selectedRowIndex");
		} catch (NumberFormatException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseError"),
					e);
		}
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
	}

	private void createBook() {
		try {
			List<Author> authorList = getSelectedAuthors();
			List<Category> categoryList = getSelectedCategories();
			List<PublishingHouse> publishingHouseList = getSelectedPublishingHouses();
			String title = bookAddModFrame.getBookDataPanel().getTextFieldTitle().getText();
			String isbnNumber = bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText();
			Integer numberOfPage = !"".equals(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
					? Integer.parseInt(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
					: null;
			Integer numberOfTomes = !"".equals(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
					? Integer.parseInt(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
					: null;

			HibernateUtil.beginTransaction();
			Book book = new Book();
			authorList.forEach(e -> book.getAuthor().add(e));
			categoryList.forEach(e -> book.getCategory().add(e));
			publishingHouseList.forEach(e -> book.getPublishingHouse().add(e));
			book.setTitle(title);
			book.setIsbnNumber(isbnNumber);
			book.setNumberOfPage(numberOfPage);
			book.setNumberOfTomes(numberOfTomes);
			HibernateUtil.getSession().save(book);
			HibernateUtil.commitTransaction();

			bookTableModel.add(book);
			
			Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
					.getString("bookAddModEvent.savingBookSuccess"), SystemProperties.getInstance().getResourceBundle()
					.getString("bookAddModEvent.savingBookSuccessTitle"), JOptionPane.INFORMATION_MESSAGE);
			
			bookAddModFrame.dispose();
			
		} catch (NumberFormatException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseError"),
					e);
		}
	}

	private List<Author> getSelectedAuthors() {
		List<Author> authorList = new ArrayList<Author>();
		for (int index : bookAddModFrame.getAuthorTable().getSelectedRows())
			authorList.add(((AuthorTableModel) bookAddModFrame.getAuthorTable().getModel()).getAuthor(index));
		return !authorList.isEmpty() ? authorList : null;
	}

	private List<Category> getSelectedCategories() {
		List<Category> categoryList = new ArrayList<Category>();
		for (int index : bookAddModFrame.getCategoryTable().getSelectedRows())
			categoryList.add(((CategoryTableModel) bookAddModFrame.getCategoryTable().getModel()).getCategory(index));
		return !categoryList.isEmpty() ? categoryList : null;
	}

	private List<PublishingHouse> getSelectedPublishingHouses() {
		List<PublishingHouse> publishingHouseList = new ArrayList<PublishingHouse>();
		for (int index : bookAddModFrame.getPublishingHouseTable().getSelectedRows())
			publishingHouseList.add(((PublishingHouseTableModel) bookAddModFrame.getPublishingHouseTable().getModel())
					.getPublishingHouse(index));
		return !publishingHouseList.isEmpty() ? publishingHouseList : null;
	}

	private void openBookAddModFrame(Context context) {
		if (bookAddModFrame == null || (bookAddModFrame != null && !bookAddModFrame.isDisplayable())) {
			bookAddModFrame = new BookAddModFrame();
			if (context == Context.MODIFICATION) {
				fillBookDataPanel();
				reloadTables();
			}
			bookAddModFrame.setVisible(true);
		} else {
			bookAddModFrame.toFront();
		}
	}

	private void reloadTables() {
		List<Integer> authorIndexes = new ArrayList<Integer>();
		List<Integer> categoryIndexes = new ArrayList<Integer>();
		List<Integer> publishingHouseIndexes = new ArrayList<Integer>();
		
		((Book) Params.getInstance().get("selectedBookShallowClone")).getAuthor().forEach(e -> authorIndexes.add(((AuthorTableModel)bookAddModFrame.getAuthorTable().getModel()).getAuthors().indexOf(e)));
		authorIndexes.forEach(e -> bookAddModFrame.getAuthorTable().addRowSelectionInterval(e, e));
		((Book) Params.getInstance().get("selectedBookShallowClone")).getCategory().forEach(e -> categoryIndexes.add(((CategoryTableModel)bookAddModFrame.getCategoryTable().getModel()).getCategories().indexOf(e)));
		categoryIndexes.forEach(e -> bookAddModFrame.getCategoryTable().addRowSelectionInterval(e, e));
		((Book) Params.getInstance().get("selectedBookShallowClone")).getPublishingHouse().forEach(e -> publishingHouseIndexes.add(((PublishingHouseTableModel)bookAddModFrame.getPublishingHouseTable().getModel()).getPublishingHouses().indexOf(e)));
		publishingHouseIndexes.forEach(e -> bookAddModFrame.getPublishingHouseTable().addRowSelectionInterval(e, e));
	}

	private void fillBookDataPanel() {
		bookAddModFrame.getBookDataPanel().getTextFieldTitle().setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getTitle() != null
				? ((Book) Params.getInstance().get("selectedBookShallowClone")).getTitle().toString()
				: "");
		bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getIsbnNumber() != null
				? ((Book) Params.getInstance().get("selectedBookShallowClone")).getIsbnNumber().toString()
				: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfPage() != null
				? ((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfPage().toString()
				: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfTomes() != null
				? ((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfTomes().toString()
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

		if (bookAddModFrame.getAuthorTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getCategoryTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getPublishingHouseTable().getSelectedRowCount() != 0) {
			result = true;
		}

		return result;
	}
}

package com.javafee.tabbedform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import org.hibernate.exception.ConstraintViolationException;

import com.javafee.common.Constants;
import com.javafee.common.Constants.Context;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.model.AuthorTableModel;
import com.javafee.model.BookTableModel;
import com.javafee.model.CategoryTableModel;
import com.javafee.model.PublishingHouseTableModel;
import com.javafee.tabbedform.books.frames.BookAddModFrame;

public class BookAddModEvent {

	private BookAddModFrame bookAddModFrame;

	private BookTableModel bookTableModel;

	private Context context;

	public void control(Context context, BookTableModel bookTableModel) {
		this.bookTableModel = bookTableModel;
		openBookAddModFrame(context);

		bookAddModFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedBook");
			}
		});

		bookAddModFrame.getCockpitConfirmationPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept(context));
		bookAddModFrame.getBtnRefreshTables().addActionListener(e -> onClickBtnRefreshTables());
	}

	private void onClickBtnAccept(Context context) {
		if (context == Context.ADDITION) {
			if (validateTablesSelection()) {
				createBook();
			} else {
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.savingBookParseError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.notSelectedTablesWarningTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (context == Context.MODIFICATION) {
			modificateBook();
		}
	}

	private void onClickBtnRefreshTables() {
		if (this.context == Context.MODIFICATION)
			reloadTablesWithBookData();
		else
			reloadTablesData();
	}

	private void modificateBook() {
		try {
			Book bookShallowClone = (Book) Params.getInstance().get("selectedBook");

			bookShallowClone.getAuthor().clear();
			bookShallowClone.getCategory().clear();
			bookShallowClone.getPublishingHouse().clear();
			if (Validator.validateIsbnNumberExist(bookShallowClone.getIdBook(),
					bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText())) {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarning1"));
			} else if (bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText()
					.length() == Constants.DATA_BASE_ISBN_NUMBER_LENGTH) {
				if (getSelectedAuthors() != null)
					getSelectedAuthors().forEach(e -> bookShallowClone.getAuthor().add(e));
				if (getSelectedCategories() != null)
					getSelectedCategories().forEach(e -> bookShallowClone.getCategory().add(e));
				if (getSelectedPublishingHouses() != null)
					getSelectedPublishingHouses().forEach(e -> bookShallowClone.getPublishingHouse().add(e));
				bookShallowClone.setTitle(bookAddModFrame.getBookDataPanel().getTextFieldTitle().getText());
				bookShallowClone.setIsbnNumber(bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber().getText());
				bookShallowClone.setNumberOfPage(
						!"".equals(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
								? Integer.parseInt(
								bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage().getText())
								: null);
				bookShallowClone.setNumberOfTomes(
						!"".equals(bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
								? Integer.parseInt(
								bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes().getText())
								: null);

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession()
						.evict(bookTableModel.getBook((Integer) Params.getInstance().get("selectedRowIndex")));
				HibernateUtil.getSession().update(Book.class.getName(), bookShallowClone);
				HibernateUtil.commitTransaction();

				bookTableModel.setBook((Integer) Params.getInstance().get("selectedRowIndex"), bookShallowClone);
				bookTableModel.fireTableDataChanged();

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.updatingBookSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"bookAddModEvent.updatingBookSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);

				bookAddModFrame.dispose();
			} else {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarning2"));
			}
		} catch (NumberFormatException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseError"),
					e);
		} catch (ConstraintViolationException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookConstraintViolationErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookConstraintViolationError"),
					e);
		}
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
			if (Validator.validateIsbnNumberExist(null, isbnNumber)) {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarning1"));
			} else if (isbnNumber.length() == Constants.DATA_BASE_ISBN_NUMBER_LENGTH) {
				HibernateUtil.beginTransaction();
				Book book = new Book();
				if (authorList != null)
					authorList.forEach(e -> book.getAuthor().add(e));
				if (categoryList != null)
					categoryList.forEach(e -> book.getCategory().add(e));
				if (publishingHouseList != null)
					publishingHouseList.forEach(e -> book.getPublishingHouse().add(e));
				book.setTitle(title);
				book.setIsbnNumber(isbnNumber);
				book.setNumberOfPage(numberOfPage);
				book.setNumberOfTomes(numberOfTomes);
				HibernateUtil.getSession().save(book);
				HibernateUtil.commitTransaction();

				bookTableModel.add(book);

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.savingBookSuccess"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.savingBookSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);

				bookAddModFrame.dispose();
			} else {
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("bookAddModEvent.incorrectIsbnNumberWarning2"));
			}
		} catch (NumberFormatException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookParseError"),
					e);
		} catch (PersistenceException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookConstraintViolationErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookConstraintViolationError"),
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
			this.context = context;
			if (this.context == Context.MODIFICATION) {
				fillBookDataPanel();
				reloadTablesWithBookData();
			}
			bookAddModFrame.setVisible(true);
		} else {
			bookAddModFrame.toFront();
		}
	}

	private void reloadTablesWithBookData() {
		List<Integer> authorIndexes = new ArrayList<Integer>();
		List<Integer> categoryIndexes = new ArrayList<Integer>();
		List<Integer> publishingHouseIndexes = new ArrayList<Integer>();

		if (Params.getInstance().get("selectedBook") != null) {
			((Book) Params.getInstance().get("selectedBook")).getAuthor().forEach(e -> authorIndexes
					.add(((AuthorTableModel) bookAddModFrame.getAuthorTable().getModel()).getAuthors().indexOf(e)));
			authorIndexes.forEach(e -> bookAddModFrame.getAuthorTable().addRowSelectionInterval(e, e));
			((Book) Params.getInstance().get("selectedBook")).getCategory().forEach(e -> categoryIndexes.add(
					((CategoryTableModel) bookAddModFrame.getCategoryTable().getModel()).getCategories().indexOf(e)));
			categoryIndexes.forEach(e -> bookAddModFrame.getCategoryTable().addRowSelectionInterval(e, e));
			((Book) Params.getInstance().get("selectedBook")).getPublishingHouse()
					.forEach(e -> publishingHouseIndexes
							.add(((PublishingHouseTableModel) bookAddModFrame.getPublishingHouseTable().getModel())
									.getPublishingHouses().indexOf(e)));
			publishingHouseIndexes
					.forEach(e -> bookAddModFrame.getPublishingHouseTable().addRowSelectionInterval(e, e));
		}
	}

	private void reloadTablesData() {
		((AuthorTableModel) bookAddModFrame.getAuthorTable().getModel()).reloadData();
		((CategoryTableModel) bookAddModFrame.getCategoryTable().getModel()).reloadData();
		((PublishingHouseTableModel) bookAddModFrame.getPublishingHouseTable().getModel()).reloadData();
	}

	private void fillBookDataPanel() {
		bookAddModFrame.getBookDataPanel().getTextFieldTitle()
				.setText(((Book) Params.getInstance().get("selectedBook")).getTitle() != null
						? ((Book) Params.getInstance().get("selectedBook")).getTitle()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber()
				.setText(((Book) Params.getInstance().get("selectedBook")).getIsbnNumber() != null
						? ((Book) Params.getInstance().get("selectedBook")).getIsbnNumber()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage()
				.setText(((Book) Params.getInstance().get("selectedBook")).getNumberOfPage() != null
						? ((Book) Params.getInstance().get("selectedBook")).getNumberOfPage().toString()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes()
				.setText(((Book) Params.getInstance().get("selectedBook")).getNumberOfTomes() != null
						? ((Book) Params.getInstance().get("selectedBook")).getNumberOfTomes().toString()
						: "");
	}

	private boolean validateTablesSelection() {
		return bookAddModFrame.getAuthorTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getCategoryTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getPublishingHouseTable().getSelectedRowCount() != 0;
	}
}
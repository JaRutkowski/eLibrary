package com.javafee.tabbedform;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.javafee.common.Constans.Context;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
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

			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle().getString("bookAddModEvent.updatingBookSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.updatingBookSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);

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

			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle().getString("bookAddModEvent.savingBookSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("bookAddModEvent.savingBookSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);

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

		((Book) Params.getInstance().get("selectedBookShallowClone")).getAuthor().forEach(e -> authorIndexes
				.add(((AuthorTableModel) bookAddModFrame.getAuthorTable().getModel()).getAuthors().indexOf(e)));
		authorIndexes.forEach(e -> bookAddModFrame.getAuthorTable().addRowSelectionInterval(e, e));
		((Book) Params.getInstance().get("selectedBookShallowClone")).getCategory().forEach(e -> categoryIndexes
				.add(((CategoryTableModel) bookAddModFrame.getCategoryTable().getModel()).getCategories().indexOf(e)));
		categoryIndexes.forEach(e -> bookAddModFrame.getCategoryTable().addRowSelectionInterval(e, e));
		((Book) Params.getInstance().get("selectedBookShallowClone")).getPublishingHouse()
				.forEach(e -> publishingHouseIndexes
						.add(((PublishingHouseTableModel) bookAddModFrame.getPublishingHouseTable().getModel())
								.getPublishingHouses().indexOf(e)));
		publishingHouseIndexes.forEach(e -> bookAddModFrame.getPublishingHouseTable().addRowSelectionInterval(e, e));
	}

	private void fillBookDataPanel() {
		bookAddModFrame.getBookDataPanel().getTextFieldTitle()
				.setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getTitle() != null
						? ((Book) Params.getInstance().get("selectedBookShallowClone")).getTitle().toString()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldIsbnNumber()
				.setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getIsbnNumber() != null
						? ((Book) Params.getInstance().get("selectedBookShallowClone")).getIsbnNumber().toString()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfPage()
				.setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfPage() != null
						? ((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfPage().toString()
						: "");
		bookAddModFrame.getBookDataPanel().getTextFieldNumberOfTomes()
				.setText(((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfTomes() != null
						? ((Book) Params.getInstance().get("selectedBookShallowClone")).getNumberOfTomes().toString()
						: "");
	}

	private boolean validateTablesSelection() {
		return bookAddModFrame.getAuthorTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getCategoryTable().getSelectedRowCount() != 0
				&& bookAddModFrame.getPublishingHouseTable().getSelectedRowCount() != 0;
	}
}

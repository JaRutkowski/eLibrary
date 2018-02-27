package com.javafee.model;

import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constans.BookTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Volume;

public class BookTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<Book> books;
	private String[] columns;

	private HibernateDao<Book, Integer> bookDao;

	public BookTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[] { SystemProperties.getInstance().getResourceBundle().getString("bookTableModel.titleCol"),
				SystemProperties.getInstance().getResourceBundle().getString("bookTableModel.isbnNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("bookTableModel.numberOfPageCol"),
				SystemProperties.getInstance().getResourceBundle().getString("bookTableModel.numberOfTomesCol") };
	}

	public void setBooks(List<Book> books) {
		this.books = books;
		fireTableDataChanged();
	}
	
	public Book getBook(int index) {
		return books.get(index);
	}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBook(int index, Book book) {
		books.set(index, book);
	}
	
	public void deleteBook(Book book) {
		books.remove(book);
	}

	public void add(Book book) {
		books.add(book);
		
		this.fireTableDataChanged();
	}
	
	public void remove(Book book) {
		books.remove(book);
		this.fireTableDataChanged();
	}

	private void prepareHibernateDao() {
		this.bookDao = new HibernateDao<Book, Integer>(Book.class);
		this.books = bookDao.findAll();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Book book = books.get(row);
		switch (BookTableColumn.getByNumber(col)) {
		case COL_TITLE:
			return book.getTitle();
		case COL_ISBN_NUMBER:
			return book.getIsbnNumber();
		case COL_NUMBER_OF_PAGES:
			return book.getNumberOfPage();
		case COL_NUMBER_OF_TOMES:
			return book.getNumberOfTomes();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Book book = books.get(row);
		Book bookShallowClone = (Book) book.clone();

		switch (BookTableColumn.getByNumber(col)) {
		case COL_TITLE:
			bookShallowClone.setTitle(value.toString());
			break;
		case COL_ISBN_NUMBER:
			bookShallowClone.setIsbnNumber(value.toString());
		case COL_NUMBER_OF_PAGES:
			bookShallowClone.setNumberOfPage((Integer) value);
			break;
		case COL_NUMBER_OF_TOMES:
			bookShallowClone.setNumberOfTomes((Integer) value);
			break;
		}

		this.fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}

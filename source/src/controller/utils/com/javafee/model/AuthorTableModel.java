package com.javafee.model;

import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constants;
import com.javafee.common.Constants.AuthorTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Author;

import lombok.Getter;

public class AuthorTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	@Getter
	protected List<Author> authors;
	private String[] columns;

	public AuthorTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("authorTableModel.authorNameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("authorTableModel.authorSurnameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("authorTableModel.authorBirthDateCol")};
	}

	public Author getAuthor(int index) {
		return authors.get(index);
	}

	public void setAuthor(int index, Author author) {
		authors.set(index, author);
	}

	public void add(Author author) {
		authors.add(author);
		this.fireTableDataChanged();
	}

	public void remove(Author author) {
		authors.remove(author);
		this.fireTableDataChanged();
	}

	protected void prepareHibernateDao() {
		this.authors = new HibernateDao<Author, Integer>(Author.class).findAll();
	}

	@SuppressWarnings("unused")
	private void executeUpdate(String entityName, Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(entityName, object);
		HibernateUtil.commitTransaction();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return authors.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Author author = authors.get(row);
		switch (AuthorTableColumn.getByNumber(col)) {
			case COL_NAME:
				return author.getName();
			case COL_SURNAME:
				return author.getSurname();
			case COL_BIRTH_DATE:
				return author.getBirthDate() != null ? Constants.APPLICATION_DATE_FORMAT.format(author.getBirthDate())
						: null;
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void reloadData() {
		prepareHibernateDao();
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}

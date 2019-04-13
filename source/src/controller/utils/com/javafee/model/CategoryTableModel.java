package com.javafee.model;

import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constants.CategoryTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Category;

import lombok.Getter;

public class CategoryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	@Getter
	protected List<Category> categories;
	private String[] columns;

	public CategoryTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("categoryTableModel.categoryNameCol")};
	}

	public Category getCategory(int index) {
		return categories.get(index);
	}

	public void setCategory(int index, Category Category) {
		categories.set(index, Category);
	}

	public void add(Category Category) {
		categories.add(Category);
		this.fireTableDataChanged();
	}

	public void remove(Category Category) {
		categories.remove(Category);
		this.fireTableDataChanged();
	}

	protected void prepareHibernateDao() {
		this.categories = new HibernateDao<Category, Integer>(Category.class).findAll();
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
		return categories.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Category category = categories.get(row);
		switch (CategoryTableColumn.getByNumber(col)) {
			case COL_NAME:
				return category.getName();
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

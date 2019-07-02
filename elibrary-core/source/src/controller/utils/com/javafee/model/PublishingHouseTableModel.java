package com.javafee.model;

import com.javafee.common.Constants.PublishingHouseTableColumn;
import com.javafee.common.SystemProperties;


import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.PublishingHouse;
import lombok.Getter;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PublishingHouseTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	@Getter
	protected List<PublishingHouse> publishingHouses;
	private String[] columns;

	public PublishingHouseTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{SystemProperties.getInstance().getResourceBundle()
				.getString("publishingHouseTableModel.publishingHouseNameCol")};
	}

	public PublishingHouse getPublishingHouse(int index) {
		return publishingHouses.get(index);
	}

	public void setPublishingHouse(int index, PublishingHouse PublishingHouse) {
		publishingHouses.set(index, PublishingHouse);
	}

	public void add(PublishingHouse PublishingHouse) {
		publishingHouses.add(PublishingHouse);
		this.fireTableDataChanged();
	}

	public void remove(PublishingHouse PublishingHouse) {
		publishingHouses.remove(PublishingHouse);
		this.fireTableDataChanged();
	}

	protected void prepareHibernateDao() {
		this.publishingHouses = new HibernateDao<PublishingHouse, Integer>(PublishingHouse.class).findAll();
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
		return publishingHouses.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		PublishingHouse publishingHouse = publishingHouses.get(row);
		switch (PublishingHouseTableColumn.getByNumber(col)) {
			case COL_NAME:
				return publishingHouse.getName();
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

package com.javafee.elibrary.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.table.DefaultTableModel;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.pojo.SystemDataFeedingPojo;

public class SystemDataFeedingTableModel extends DefaultTableModel {
	private List<SystemDataFeedingPojo> systemDataFeedingPojos = new ArrayList<>();
	private String[] columns;

	private Object[][] initialData = {
			{Constants.SystemDataFeedingTableData.ADMINISTRATOR_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					Constants.SystemDataFeedingTableData.ADMINISTRATOR_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()], null},
			{Constants.SystemDataFeedingTableData.MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					Constants.SystemDataFeedingTableData.MESSAGES_AND_NOTIFICATIONS_DICTIONARIES_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()], null},
			{Constants.SystemDataFeedingTableData.SYSTEM_PARAMETERS_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					Constants.SystemDataFeedingTableData.SYSTEM_PARAMETERS_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()], null},
			{Constants.SystemDataFeedingTableData.SYSTEM_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					Constants.SystemDataFeedingTableData.SYSTEM_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()], null},
			{Constants.SystemDataFeedingTableData.LIBRARY_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					Constants.SystemDataFeedingTableData.LIBRARY_DATA.getValue()[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()], null}
	};

	public SystemDataFeedingTableModel() {
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableModel.feedKindCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableModel.dataValueCol"),
				SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingTableModel.actionCol")};
		this.prepareInitialData();
		setDataVector(initialData, columns);
	}

	private void prepareInitialData() {
		for (var systemDataFeedingPojo : initialData) {
			SystemDataFeedingPojo sdfp = new SystemDataFeedingPojo((String) systemDataFeedingPojo[Constants.SystemDataFeedingTableColumn.COL_FEEDING_TYPE.getValue()],
					(String) systemDataFeedingPojo[Constants.SystemDataFeedingTableColumn.COL_DATA.getValue()],
					(List<Consumer>) systemDataFeedingPojo[Constants.SystemDataFeedingTableColumn.COL_ACTION.getValue()]);
			systemDataFeedingPojos.add(sdfp);
		}
	}

	public void prepareActionData(List<List> actionColData) {
		int rowIndex = 0;
		for (var action : actionColData) {
			systemDataFeedingPojos.get(rowIndex).setActions(action);
			rowIndex++;
		}
		fireTableDataChanged();
	}

	public List<SystemDataFeedingPojo> getSystemDataFeedingPojos() {
		return this.systemDataFeedingPojos;
	}

	public SystemDataFeedingPojo getSystemDataFeedingPojo(int index) {
		return this.systemDataFeedingPojos.get(index);
	}

	public int update(int row, SystemDataFeedingPojo systemDataFeedingPojo) {
		systemDataFeedingPojos.set(row, systemDataFeedingPojo);
		this.fireTableRowsUpdated(row, row);
		return row;
	}

	@Override
	public Object getValueAt(int row, int col) {
		SystemDataFeedingPojo systemDataFeedingPojo = systemDataFeedingPojos.get(row);
		switch (Constants.SystemDataFeedingTableColumn.getByNumber(col)) {
			case COL_FEEDING_TYPE:
				return systemDataFeedingPojo.getFeedType();
			case COL_DATA:
				return systemDataFeedingPojo.getData();
			case COL_ACTION:
				return systemDataFeedingPojo.getActions();
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		SystemDataFeedingPojo systemDataFeedingPojo = systemDataFeedingPojos.get(row);
		switch (Constants.SystemDataFeedingTableColumn.getByNumber(col)) {
			case COL_FEEDING_TYPE:
				systemDataFeedingPojo.setFeedType(value.toString());
				break;
			case COL_DATA:
				systemDataFeedingPojo.setData(value.toString());
				break;
			case COL_ACTION:
				systemDataFeedingPojo.setActions((List<Consumer>) value);
				break;
		}
		systemDataFeedingPojos.set(row, systemDataFeedingPojo);
		this.fireTableRowsUpdated(row, row);
	}
}

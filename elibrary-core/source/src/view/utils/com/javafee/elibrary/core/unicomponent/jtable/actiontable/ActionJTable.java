package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import javax.swing.table.TableColumn;

import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;

public class ActionJTable extends CustomJTable {
	public void initialize(int columnIndex) {
		setRowHeight(36);
		TableColumn column = getColumnModel().getColumn(columnIndex);
		column.setCellRenderer(new ActionJTableBtnRenderer());
		column.setCellEditor(new ActionJTableBtnEditor(this));
	}
}

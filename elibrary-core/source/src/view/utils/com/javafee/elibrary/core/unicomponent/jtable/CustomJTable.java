package com.javafee.elibrary.core.unicomponent.jtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.javafee.elibrary.core.common.Utils;

public class CustomJTable extends JTable {
	public CustomJTable() {
		super();
		getTableHeader().setFont(Utils.getApplicationUserDefinedFont());
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJTable(DefaultTableModel defaultTableModel) {
		super(defaultTableModel);
		getTableHeader().setFont(Utils.getApplicationUserDefinedFont());
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

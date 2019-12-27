package com.javafee.unicomponent.jtable;

import javax.swing.JTable;

import com.javafee.common.Utils;

public class CustomJTable extends JTable {
	public CustomJTable() {
		super();
		getTableHeader().setFont(Utils.getApplicationUserDefinedFont());
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

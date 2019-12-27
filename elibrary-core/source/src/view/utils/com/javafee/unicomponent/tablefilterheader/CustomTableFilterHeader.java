package com.javafee.unicomponent.tablefilterheader;

import javax.swing.JTable;

import com.javafee.common.Utils;

import net.coderazzi.filters.gui.TableFilterHeader;

public class CustomTableFilterHeader extends TableFilterHeader {
	public CustomTableFilterHeader(JTable table) {
		super(table);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

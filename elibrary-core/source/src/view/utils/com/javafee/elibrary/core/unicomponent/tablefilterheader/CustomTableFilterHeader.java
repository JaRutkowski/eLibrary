package com.javafee.elibrary.core.unicomponent.tablefilterheader;

import javax.swing.JTable;

import com.javafee.elibrary.core.common.Utils;

import net.coderazzi.filters.gui.TableFilterHeader;

public class CustomTableFilterHeader extends TableFilterHeader {
	public CustomTableFilterHeader(JTable table) {
		super(table);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

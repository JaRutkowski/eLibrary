package com.javafee.elibrary.core.unicomponent.jtabbedpane;

import javax.swing.JTabbedPane;

import com.javafee.elibrary.core.common.Utils;

public class CustomJTabbedPane extends JTabbedPane {
	public CustomJTabbedPane(int tabPlacement) {
		super(tabPlacement);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

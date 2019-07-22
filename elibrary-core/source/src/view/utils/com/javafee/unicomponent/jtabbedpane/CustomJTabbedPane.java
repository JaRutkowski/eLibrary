package com.javafee.unicomponent.jtabbedpane;

import javax.swing.JTabbedPane;

import com.javafee.common.Utils;

public class CustomJTabbedPane extends JTabbedPane {
	public CustomJTabbedPane(int tabPlacement) {
		super(tabPlacement);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

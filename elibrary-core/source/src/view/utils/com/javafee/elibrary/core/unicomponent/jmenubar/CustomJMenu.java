package com.javafee.elibrary.core.unicomponent.jmenubar;

import javax.swing.JMenu;

import com.javafee.elibrary.core.common.Utils;

public class CustomJMenu extends JMenu {
	public CustomJMenu(String s) {
		super(s);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

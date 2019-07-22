package com.javafee.unicomponent.jmenubar;

import javax.swing.JMenu;

import com.javafee.common.Utils;

public class CustomJMenu extends JMenu {
	public CustomJMenu(String s) {
		super(s);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

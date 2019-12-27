package com.javafee.unicomponent.jmenubar;

import javax.swing.JMenuItem;

import com.javafee.common.Utils;

public class CustomJMenuItem extends JMenuItem {
	public CustomJMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

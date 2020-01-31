package com.javafee.elibrary.core.unicomponent.jbutton;

import javax.swing.Icon;
import javax.swing.JButton;

import com.javafee.elibrary.core.common.Utils;

public class CustomJButton extends JButton {
	public CustomJButton() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJButton(String text) {
		super(text);
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJButton(Icon icon, String text) {
		super(text, icon);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

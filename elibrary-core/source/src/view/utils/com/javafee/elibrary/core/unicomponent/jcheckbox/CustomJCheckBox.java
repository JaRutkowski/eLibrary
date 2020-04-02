package com.javafee.elibrary.core.unicomponent.jcheckbox;

import javax.swing.JCheckBox;

import com.javafee.elibrary.core.common.Utils;

public class CustomJCheckBox extends JCheckBox {
	public CustomJCheckBox() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
	}

	public CustomJCheckBox(String text) {
		super(text);
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
	}
}

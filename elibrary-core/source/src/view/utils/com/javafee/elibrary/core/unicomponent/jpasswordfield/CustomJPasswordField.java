package com.javafee.elibrary.core.unicomponent.jpasswordfield;

import javax.swing.JPasswordField;

import com.javafee.elibrary.core.common.Utils;

public class CustomJPasswordField extends JPasswordField {
	public CustomJPasswordField() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

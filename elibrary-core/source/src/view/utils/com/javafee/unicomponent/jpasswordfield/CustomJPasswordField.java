package com.javafee.unicomponent.jpasswordfield;

import javax.swing.JPasswordField;

import com.javafee.common.Utils;

public class CustomJPasswordField extends JPasswordField {
	public CustomJPasswordField() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

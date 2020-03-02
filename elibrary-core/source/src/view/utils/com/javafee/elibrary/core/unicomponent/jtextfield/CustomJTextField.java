package com.javafee.elibrary.core.unicomponent.jtextfield;

import javax.swing.JTextField;

import com.javafee.elibrary.core.common.Utils;

public class CustomJTextField extends JTextField {
	public CustomJTextField() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

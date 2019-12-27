package com.javafee.unicomponent.jtextfield;

import javax.swing.JTextField;

import com.javafee.common.Utils;

public class CustomJTextField extends JTextField {
	public CustomJTextField() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

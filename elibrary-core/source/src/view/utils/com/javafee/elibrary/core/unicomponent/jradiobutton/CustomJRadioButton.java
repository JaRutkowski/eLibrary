package com.javafee.elibrary.core.unicomponent.jradiobutton;

import javax.swing.JRadioButton;

import com.javafee.elibrary.core.common.Utils;

public class CustomJRadioButton extends JRadioButton {
	public CustomJRadioButton() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJRadioButton(String text) {
		super(text);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

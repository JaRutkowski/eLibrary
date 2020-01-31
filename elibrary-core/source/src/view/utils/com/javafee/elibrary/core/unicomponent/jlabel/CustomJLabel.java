package com.javafee.elibrary.core.unicomponent.jlabel;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.javafee.elibrary.core.common.Utils;

public class CustomJLabel extends JLabel {
	public CustomJLabel() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJLabel(String text) {
		super(text);
		setFont(Utils.getApplicationUserDefinedFont());
	}

	public CustomJLabel(Icon image) {
		super(image);
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

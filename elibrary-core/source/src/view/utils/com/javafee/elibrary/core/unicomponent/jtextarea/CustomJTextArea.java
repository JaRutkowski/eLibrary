package com.javafee.elibrary.core.unicomponent.jtextarea;

import javax.swing.JTextArea;

import com.javafee.elibrary.core.common.Utils;

public class CustomJTextArea extends JTextArea {
	public CustomJTextArea() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

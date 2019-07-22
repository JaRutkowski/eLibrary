package com.javafee.unicomponent.jtextarea;

import javax.swing.JTextArea;

import com.javafee.common.Utils;

public class CustomJTextArea extends JTextArea {
	public CustomJTextArea() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

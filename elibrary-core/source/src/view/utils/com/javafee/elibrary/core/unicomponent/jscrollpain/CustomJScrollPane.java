package com.javafee.elibrary.core.unicomponent.jscrollpain;

import javax.swing.JScrollPane;

import com.javafee.elibrary.core.common.Utils;

public class CustomJScrollPane extends JScrollPane {
	public CustomJScrollPane() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
	}
}

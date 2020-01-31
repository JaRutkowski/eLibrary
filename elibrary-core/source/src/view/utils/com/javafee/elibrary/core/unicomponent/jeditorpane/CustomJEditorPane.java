package com.javafee.elibrary.core.unicomponent.jeditorpane;

import javax.swing.JEditorPane;

import com.javafee.elibrary.core.common.Utils;

public class CustomJEditorPane extends JEditorPane {
	public CustomJEditorPane() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

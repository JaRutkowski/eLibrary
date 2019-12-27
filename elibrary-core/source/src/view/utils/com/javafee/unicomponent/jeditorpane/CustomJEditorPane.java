package com.javafee.unicomponent.jeditorpane;

import javax.swing.JEditorPane;

import com.javafee.common.Utils;

public class CustomJEditorPane extends JEditorPane {
	public CustomJEditorPane() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

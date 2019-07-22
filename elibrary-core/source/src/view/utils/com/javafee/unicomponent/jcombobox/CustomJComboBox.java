package com.javafee.unicomponent.jcombobox;

import javax.swing.JComboBox;

import com.javafee.common.Utils;

public class CustomJComboBox<E> extends JComboBox<E> {
	public CustomJComboBox() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

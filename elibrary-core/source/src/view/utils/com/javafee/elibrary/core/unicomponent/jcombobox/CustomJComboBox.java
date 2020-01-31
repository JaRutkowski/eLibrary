package com.javafee.elibrary.core.unicomponent.jcombobox;

import javax.swing.JComboBox;

import com.javafee.elibrary.core.common.Utils;

public class CustomJComboBox<E> extends JComboBox<E> {
	public CustomJComboBox() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

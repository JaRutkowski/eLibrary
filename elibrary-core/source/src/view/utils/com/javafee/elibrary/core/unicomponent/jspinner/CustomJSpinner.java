package com.javafee.elibrary.core.unicomponent.jspinner;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.javafee.elibrary.core.common.Utils;

public class CustomJSpinner extends JSpinner {
	private SpinnerNumberModel model;

	public CustomJSpinner() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
	}

	public CustomJSpinner(int value, int minimum, int maximum) {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
		model = new SpinnerNumberModel(value, minimum, maximum, 1);
		this.setModel(model);
	}
}

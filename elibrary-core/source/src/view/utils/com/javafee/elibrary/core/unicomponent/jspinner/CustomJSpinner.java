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

	public CustomJSpinner(double value, double minimum, double maximum) {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
		setBackground(Utils.getApplicationUserDefinedColor());
		model = new SpinnerNumberModel((int) value, (int) minimum, (int) maximum, 1);
		this.setModel(model);
	}
}

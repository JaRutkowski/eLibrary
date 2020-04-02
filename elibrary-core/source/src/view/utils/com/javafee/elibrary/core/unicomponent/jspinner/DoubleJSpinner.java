package com.javafee.elibrary.core.unicomponent.jspinner;

import javax.swing.SpinnerNumberModel;

public class DoubleJSpinner extends CustomJSpinner {
	private SpinnerNumberModel model;

	public DoubleJSpinner(double value, double minimum, double maximum, double stepSize) {
		super();
		model = new SpinnerNumberModel(value, minimum, maximum, stepSize);
		this.setModel(model);
	}

	public Double getDouble() {
		return (Double) getValue();
	}
}

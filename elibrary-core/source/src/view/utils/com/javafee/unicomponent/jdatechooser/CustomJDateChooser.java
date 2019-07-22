package com.javafee.unicomponent.jdatechooser;

import com.javafee.common.Utils;
import com.toedter.calendar.JDateChooser;

public class CustomJDateChooser extends JDateChooser {
	public CustomJDateChooser() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

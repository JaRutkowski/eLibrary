package com.javafee.elibrary.core.unicomponent.jdatechooser;

import com.javafee.elibrary.core.common.Utils;
import com.toedter.calendar.JDateChooser;

public class CustomJDateChooser extends JDateChooser {
	public CustomJDateChooser() {
		super();
		setFont(Utils.getApplicationUserDefinedFont());
	}
}

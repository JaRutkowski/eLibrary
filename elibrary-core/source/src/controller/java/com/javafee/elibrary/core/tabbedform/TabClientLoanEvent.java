package com.javafee.elibrary.core.tabbedform;

import com.javafee.elibrary.core.common.IActionForm;

import lombok.Setter;

public class TabClientLoanEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabClientLoanEvent clientLoanEvent = null;

	public TabClientLoanEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabClientLoanEvent getInstance(TabbedForm tabbedForm) {
		if (clientLoanEvent == null) {
			clientLoanEvent = new TabClientLoanEvent(tabbedForm);
		}
		return clientLoanEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

	}

	@Override
	public void initializeForm() {
	}
}

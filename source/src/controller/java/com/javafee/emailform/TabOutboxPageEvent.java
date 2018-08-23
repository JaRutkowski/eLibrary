package com.javafee.emailform;

import com.javafee.common.IActionForm;
import com.javafee.exception.RefusedTabSendedPageEventLoadingException;

import lombok.Setter;

public class TabOutboxPageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabOutboxPageEvent workingCopyPageEvent = null;

	private TabOutboxPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabOutboxPageEvent getInstance(EmailForm emailForm) {
		if (workingCopyPageEvent == null) {
			workingCopyPageEvent = new TabOutboxPageEvent(emailForm);
		} else
			new RefusedTabSendedPageEventLoadingException("Cannot tab sended page event loading");
		return workingCopyPageEvent;
	}
	
	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();
		
	}

	@Override
	public void initializeForm() {
		
	}
}

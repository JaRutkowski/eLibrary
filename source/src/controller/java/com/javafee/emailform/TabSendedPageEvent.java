package com.javafee.emailform;

import com.javafee.common.IActionForm;
import com.javafee.exception.RefusedClientsEventLoadingException;

import lombok.Setter;

public class TabSendedPageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabSendedPageEvent workingCopyPageEvent = null;

	private TabSendedPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabSendedPageEvent getInstance(EmailForm emailForm) {
		if (workingCopyPageEvent == null) {
			workingCopyPageEvent = new TabSendedPageEvent(emailForm);
		} else
			//TODO
			new RefusedClientsEventLoadingException("Cannot client event loading");
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

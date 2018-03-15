package com.javafee.emailform;

import com.javafee.common.IActionForm;
import com.javafee.exception.RefusedClientsEventLoadingException;

import lombok.Setter;

public class TabWorkingCopyPageEvent implements IActionForm{
	@Setter
	private EmailForm emailForm;

	private static TabWorkingCopyPageEvent workingCopyPageEvent = null;

	private TabWorkingCopyPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabWorkingCopyPageEvent getInstance(EmailForm emailForm) {
		if (workingCopyPageEvent == null) {
			workingCopyPageEvent = new TabWorkingCopyPageEvent(emailForm);
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

package com.javafee.emailform;

import com.javafee.common.IActionForm;
import com.javafee.exception.RefusedClientsEventLoadingException;

import lombok.Setter;

public class TabCreatePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabCreatePageEvent createPageEvent = null;

	private TabCreatePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabCreatePageEvent getInstance(EmailForm emailForm) {
		if (createPageEvent == null) {
			createPageEvent = new TabCreatePageEvent(emailForm);
		} else
			//TODO
			new RefusedClientsEventLoadingException("Cannot client event loading");
		return createPageEvent;
	}
	
	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();
		
	}

	@Override
	public void initializeForm() {
		
	}
}

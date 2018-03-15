package com.javafee.emailform;

import com.javafee.common.IActionForm;
import com.javafee.exception.RefusedClientsEventLoadingException;

import lombok.Setter;

public class TabTemplatePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabTemplatePageEvent templatePageEvent = null;

	private TabTemplatePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabTemplatePageEvent getInstance(EmailForm emailForm) {
		if (templatePageEvent == null) {
			templatePageEvent = new TabTemplatePageEvent(emailForm);
		} else
			//TODO
			new RefusedClientsEventLoadingException("Cannot client event loading");
		return templatePageEvent;
	}
	
	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();
		
	}

	@Override
	public void initializeForm() {
		
	}
}

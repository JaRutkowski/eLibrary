package com.javafee.emailform;

import com.javafee.common.Constans.Tab_Email;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;

public class Actions implements IActionForm {
	private EmailForm emailForm = new EmailForm();
	
	public void control() {
		emailForm.getFrame().setVisible(true);
		initializeForm();
		
		emailForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());
	}
	
	@Override
	public void initializeForm() {
		initializeTabbedPane();
		reloadTabbedPane();
	}
	
	private void initializeTabbedPane() {
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabCreatePageTitle"), null,
				emailForm.getPanelComposePage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabSendedPageTitle"), null,
				emailForm.getPanelSentMailPage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabWorkingCopyPageTitle"), null,
				emailForm.getPanelDraftPage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabTemplatePageTitle"),
				null, emailForm.getPanelTemplatePage(), null);
	}
	
	private void reloadTabbedPane() {
		switch (Tab_Email.getByNumber(emailForm.getTabbedPane().getSelectedIndex())) {
		case TAB_CREATE_PAGE:
			TabCreatePageEvent.getInstance(emailForm);
			break;
		case TAB_SENDED_PAGE:
			TabSendedPageEvent.getInstance(emailForm);
			break;
		case TAB_WORKING_COPY_PAGE:
			TabWorkingCopyPageEvent.getInstance(emailForm);
			break;
		case TAB_TEMPLATE_PAGE:
			TabTemplatePageEvent.getInstance(emailForm);
			break;
		default:
			break;
		}
	}
	
	private void onChangeTabbedPane() {
		reloadTabbedPane();
	}
}

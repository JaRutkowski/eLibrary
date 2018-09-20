package com.javafee.emailform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.javafee.common.Constans.Tab_Email;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;

public class Actions implements IActionForm {
	private EmailForm emailForm;

	public void control() {
		openEmailForm();
		initializeForm();

		emailForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());

		emailForm.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				emailForm.getFrame().dispose();
				emailForm = null;
				clearEvent();
			}
		});
	}

	private void openEmailForm() {
		if (emailForm == null || (emailForm != null && !emailForm.getFrame().isDisplayable())) {
			emailForm = new EmailForm();
			emailForm.getFrame().setVisible(true);
		} else {
			emailForm.getFrame().toFront();
		}
	}

	@Override
	public void initializeForm() {
		initializeTabbedPane();
		reloadTabbedPane();
	}

	private void initializeTabbedPane() {
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabComposePageTitle"), null,
				emailForm.getPanelComposePage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabOutboxPageTitle"), null,
				emailForm.getPanelOutboxPage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabWorkingCopyPageTitle"), null,
				emailForm.getPanelDraftPage(), null);
		emailForm.getTabbedPane().addTab(
				SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabTemplatePageTitle"), null,
				emailForm.getPanelTemplatePage(), null);
	}

	private void reloadTabbedPane() {
		switch (Tab_Email.getByNumber(emailForm.getTabbedPane().getSelectedIndex())) {
		case TAB_CREATE_PAGE:
			TabComposePageEvent.getInstance(emailForm);
			break;
		case TAB_SENDED_PAGE:
			TabOutboxPageEvent.getInstance(emailForm);
			break;
		case TAB_DRAFT_PAGE:
			TabDraftPageEvent.getInstance(emailForm);
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

	private void clearEvent() {
		TabComposePageEvent.composePageEvent = null;
		TabOutboxPageEvent.outboxPageEvent = null;
		TabDraftPageEvent.draftPageEvent = null;
		TabTemplatePageEvent.templatePageEvent = null;
	}
}

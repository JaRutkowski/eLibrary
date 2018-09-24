package com.javafee.emailform;

import com.javafee.common.IActionForm;

import lombok.Setter;

public class TabTemplatePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	protected static TabTemplatePageEvent templatePageEvent = null;

	private TabTemplatePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabTemplatePageEvent getInstance(EmailForm emailForm) {
		if (templatePageEvent == null)
			templatePageEvent = new TabTemplatePageEvent(emailForm);

		return templatePageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();

		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnParse().addActionListener(e -> onClickBtnParse());
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnValidate()
				.addActionListener(e -> onClickBtnValidate());
	}

	@Override
	public void initializeForm() {

	}

	private void onClickBtnParse() {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getEditorPanePreview()
				.setText(emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLEditor().getText());
	}

	private void onClickBtnValidate() {

	}
}

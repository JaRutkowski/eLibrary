package com.javafee.settingsform;

import com.javafee.common.IActionForm;

import lombok.Setter;

public class PasswordChangePanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static PasswordChangePanelEvent passwordChangePanelEvent = null;

	private PasswordChangePanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static PasswordChangePanelEvent getInstance(SettingsForm settingsForm) {
		if (passwordChangePanelEvent == null)
			passwordChangePanelEvent = new PasswordChangePanelEvent(settingsForm);

		return passwordChangePanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getPasswordChangePanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
	}

	public void initializeForm() {
	}

	private void onClickBtnAccept() {
		System.out.println("Btn accept clicked!");
	}
}

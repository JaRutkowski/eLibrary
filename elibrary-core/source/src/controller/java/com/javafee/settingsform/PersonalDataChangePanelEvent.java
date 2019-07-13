package com.javafee.settingsform;

import com.javafee.common.IActionForm;

import lombok.Setter;

public class PersonalDataChangePanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static PersonalDataChangePanelEvent personalDataChangePanelEvent = null;

	private PersonalDataChangePanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static PersonalDataChangePanelEvent getInstance(SettingsForm settingsForm) {
		if (personalDataChangePanelEvent == null)
			personalDataChangePanelEvent = new PersonalDataChangePanelEvent(settingsForm);

		return personalDataChangePanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

	}

	@Override
	public void initializeForm() {

	}
}

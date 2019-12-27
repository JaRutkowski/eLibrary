package com.javafee.settingsform;

import com.javafee.startform.RegistrationPanel;

public class PersonalDataPanel extends RegistrationPanel {
	public PersonalDataPanel() {
		getLblPassword().setVisible(false);
		getPasswordField().setVisible(false);
		getBtnRegisterNow().setVisible(false);
	}
}

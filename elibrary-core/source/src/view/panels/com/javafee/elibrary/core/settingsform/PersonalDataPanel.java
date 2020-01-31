package com.javafee.elibrary.core.settingsform;

import com.javafee.elibrary.core.startform.RegistrationPanel;

public class PersonalDataPanel extends RegistrationPanel {
	public PersonalDataPanel() {
		getLblPassword().setVisible(false);
		getPasswordField().setVisible(false);
		getBtnRegisterNow().setVisible(false);
	}
}

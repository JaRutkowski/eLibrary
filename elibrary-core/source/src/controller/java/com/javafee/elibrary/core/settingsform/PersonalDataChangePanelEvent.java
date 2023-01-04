package com.javafee.elibrary.core.settingsform;

import java.util.Objects;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.model.WorkerTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.common.UserAccount;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Worker;

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

		settingsForm.getSettingsPanel().getPersonalDataChangePanel().getCockpitConfirmationPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
		settingsForm.getSettingsPanel().getRootPane().setDefaultButton(settingsForm.getSettingsPanel()
				.getPersonalDataChangePanel().getCockpitConfirmationPanel().getBtnAccept());
	}

	@Override
	public void initializeForm() {
		reloadPersonalDataPanel();
	}

	private void reloadPersonalDataPanel() {
		Common.fillUserDataPanel(settingsForm.getSettingsPanel().getPersonalDataChangePanel().getPersonalDataPanel(), LogInEvent.getUserData());
	}

	private void onClickBtnAccept() {
		UserData userData = LogInEvent.getUserData();
		PersonalDataPanel personalDataChangePanel = settingsForm.getSettingsPanel().getPersonalDataChangePanel().getPersonalDataPanel();
		boolean roleWorker = LogInEvent.getRole() == Constants.Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Constants.Role.WORKER_LIBRARIAN,
				roleClient = LogInEvent.getRole() == Constants.Role.CLIENT;

		userData.setPeselNumber(personalDataChangePanel.getTextFieldPeselNumber().getText());
		userData.setDocumentNumber(personalDataChangePanel.getTextFieldDocumentNumber().getText());
		userData.setName(personalDataChangePanel.getTextFieldName().getText());
		userData.setSurname(personalDataChangePanel.getTextFieldSurname().getText());
		userData.setAddress(personalDataChangePanel.getTextFieldAddress().getText());
		userData.setCity(Optional.ofNullable(personalDataChangePanel.getComboBoxCity().getSelectedItem()).isPresent()
				? ((City) personalDataChangePanel.getComboBoxCity().getSelectedItem()).getName()
				: null);
		if (personalDataChangePanel.getRadioButtonMale().isSelected()
				|| personalDataChangePanel.getRadioButtonFemale().isSelected())
			userData.setSex(personalDataChangePanel.getRadioButtonMale().isSelected()
					? Constants.DATA_BASE_MALE_SIGN
					: Constants.DATA_BASE_FEMALE_SIGN);
		userData.setBirthDate(personalDataChangePanel.getDateChooserBirthDate().getDate());
		userData.setEMail(personalDataChangePanel.getTextFieldEMail().getText());
		userData.getUserAccount().setLogin(personalDataChangePanel.getTextFieldLogin().getText());

		if (!"".equals(userData.getPeselNumber()) && userData.getPeselNumber().length() != Constants.DATA_BASE_PESEL_NUMBER_LENGHT) {
			Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientPeselErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		} else {
			UserAccount userAccount = (UserAccount) HibernateUtil.getSession().getNamedQuery("UserAccount.checkIfUserDataLoginExist")
					.setParameter("login", personalDataChangePanel.getTextFieldLogin().getText()).uniqueResult();
			if (Objects.isNull(userAccount)) {
				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(UserData.class.getName(), userData);
				HibernateUtil.commitTransaction();

				if (roleWorker && Params.getInstance().get("TABBED_FORM_TABLE_MODEL") != null)
					((WorkerTableModel) Params.getInstance().get("TABBED_FORM_TABLE_MODEL")).fireTableDataChanged();

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"clientAddModEvent.updatingClientSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} else
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientError"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("clientAddModEvent.updatingClientErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
		}
	}
}

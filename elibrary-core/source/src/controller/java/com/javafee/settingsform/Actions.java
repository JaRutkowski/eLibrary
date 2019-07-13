package com.javafee.settingsform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import com.javafee.common.Constants;
import com.javafee.common.Constants.Panel_Settings;
import com.javafee.common.IActionForm;
import com.javafee.startform.LogInEvent;

public class Actions implements IActionForm {
	private SettingsForm settingsForm;

	public void control() {
		openSettingsForm();
		initializeForm();

		settingsForm.getSettingsPanel().getTreeMenu().addTreeSelectionListener(e -> onChangeTreeMenu());

		settingsForm.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				settingsForm.getFrame().dispose();
				settingsForm = null;
				clearEvent();
			}
		});
	}

	private void openSettingsForm() {
		if (settingsForm == null || (settingsForm != null && !settingsForm.getFrame().isDisplayable())) {
			settingsForm = new SettingsForm();
			settingsForm.getFrame().setVisible(true);
		} else {
			settingsForm.getFrame().toFront();
		}
	}

	@Override
	public void initializeForm() {
		setFirstNodeSelectionInTreeMenu();
		reloadContentPanel();
	}

	private void reloadContentPanel() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) settingsForm.getSettingsPanel().getTreeMenu().getLastSelectedPathComponent();
		if (LogInEvent.getRole() == Constants.Role.CLIENT || LogInEvent.getRole() == Constants.Role.WORKER_LIBRARIAN || LogInEvent.getRole() == Constants.Role.WORKER_ACCOUNTANT) {
			if (selectedNode != null && selectedNode.getUserObject() != null) {
				switch (Panel_Settings.getByName((String) selectedNode.getUserObject())) {
					case GENERAL_PANEL:
						System.out.println("general");
						break;
					case THEME_PANEL:
						settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getThemePanel());
						ThemePanelEvent.getInstance(settingsForm);
						break;
					case FONT_PANEL:
						System.out.println("font");
						break;
					case ACCOUNT_PANEL:
						System.out.println("account");
						break;
					case PERSONAL_DATA_CHANGE_PANEL:
						settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getPersonalDataChangePanel());
						PersonalDataChangePanelEvent.getInstance(settingsForm);
						break;
					case PASSWORD_CHANGE_PANEL:
						settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getPasswordChangePanel());
						PasswordChangePanelEvent.getInstance(settingsForm);
						break;
				}
			}
		}

		if (LogInEvent.getRole() == Constants.Role.ADMIN) {
			if (selectedNode != null && selectedNode.getUserObject() != null) {
				switch (Panel_Settings.getByName((String) selectedNode.getUserObject())) {
					case GENERAL_PANEL:
						System.out.println("general");
						break;
					case THEME_PANEL:
						settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getThemePanel());
						ThemePanelEvent.getInstance(settingsForm);
						break;
					case FONT_PANEL:
						System.out.println("font");
						break;
					case ACCOUNT_PANEL:
						System.out.println("account");
						break;
					case PERSONAL_DATA_CHANGE_PANEL:
						System.out.println("data change");
						break;
				}
			}
		}
	}

	private void onChangeTreeMenu() {
		reloadContentPanel();
	}

	private void setFirstNodeSelectionInTreeMenu() {
		settingsForm.getSettingsPanel().getTreeMenu().setSelectionRow(1);
		reloadContentPanel();
	}

	private void clearEvent() {
		ThemePanelEvent.themePanelEvent = null;
		PersonalDataChangePanelEvent.personalDataChangePanelEvent = null;
		PasswordChangePanelEvent.passwordChangePanelEvent = null;
	}
}

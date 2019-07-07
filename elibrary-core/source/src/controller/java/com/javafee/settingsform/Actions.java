package com.javafee.settingsform;

import javax.swing.tree.DefaultMutableTreeNode;

import com.javafee.common.Constants.Panel_Settings;
import com.javafee.common.IActionForm;

public class Actions implements IActionForm {
	private SettingsForm settingsForm;

	public void control() {
		openSettingsForm();
		initializeForm();

		settingsForm.getSettingsPanel().getTreeMenu().addTreeSelectionListener(e -> onChangeTreeMenu());
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

		if (selectedNode != null && selectedNode.getUserObject() != null) {
			switch (Panel_Settings.getByName((String) selectedNode.getUserObject())) {
				case GENERAL_PANEL:
					System.out.println("general");
					break;
				case THEME_PANEL:
					System.out.println("theme");
					break;
				case FONT_PANEL:
					System.out.println("font");
					break;
				case ACCOUNT_PANEL:
					System.out.println("account");
					break;
				case DATA_CHANGE_PANEL:
					System.out.println("data change");
					break;
				case PASSWORD_CHANGE_PANEL:
					settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getPasswordChangePanel());
					PasswordChangePanelEvent.getInstance(settingsForm);
					break;
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
}

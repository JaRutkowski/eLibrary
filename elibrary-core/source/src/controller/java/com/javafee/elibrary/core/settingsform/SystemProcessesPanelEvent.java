package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.RegistrationPanel;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class SystemProcessesPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static SystemProcessesPanelEvent systemProcessesPanelEvent = null;

	private SystemProcessesPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static SystemProcessesPanelEvent getInstance(SettingsForm settingsForm) {
		if (systemProcessesPanelEvent == null)
			systemProcessesPanelEvent = new SystemProcessesPanelEvent(settingsForm);

		return systemProcessesPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);

		settingsForm.getSettingsPanel().getSystemProcessesPanel().getBtnStop().addActionListener(e -> onClickBtnStop());
		settingsForm.getSettingsPanel().getSystemProcessesPanel().getBtnCheckHealth().addActionListener(e -> onClickBtnCheckHealth());
		settingsForm.getSettingsPanel().getRootPane().setDefaultButton(settingsForm.getSettingsPanel()
				.getSystemProcessesPanel().getBtnCheckHealth());
	}

	@Override
	public void initializeForm() {

	}

	private void onClickBtnStop() {
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxNetworkService().isSelected())
			Common.unregisterNetworkServiceListener();
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxDirectoryWatchService().isSelected())
			Common.unregisterWatchServiceListener();
		reloadAllLblsForCheckedProcesses();
		displayRunSuccessDialog();
	}

	private void onClickBtnCheckHealth() {
		reloadAllLblsForCheckedProcesses();
		resetAllLblsForUnchcekcedProcesses();
		displayCheckHealthSuccessDialog();
	}

	private void reloadAllLblsForCheckedProcesses() {
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxNetworkService().isSelected())
			reloadLblWitchImageIcon(settingsForm.getSettingsPanel().getSystemProcessesPanel().getLblNetworkServiceHealth(),
					Common.isNetworkServiceRunning());
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxDirectoryWatchService().isSelected())
			reloadLblWitchImageIcon(settingsForm.getSettingsPanel().getSystemProcessesPanel().getLblDirectoryWatchServiceHealth(),
					Common.isWatchServiceRunning());
	}

	private void resetAllLblsForUnchcekcedProcesses() {
		if (!settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxNetworkService().isSelected())
			reloadLblWitchImageIcon(settingsForm.getSettingsPanel().getSystemProcessesPanel().getLblNetworkServiceHealth(), null);
		if (!settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxDirectoryWatchService().isSelected())
			reloadLblWitchImageIcon(settingsForm.getSettingsPanel().getSystemProcessesPanel().getLblDirectoryWatchServiceHealth(), null);
	}

	private void reloadLblWitchImageIcon(JLabel label, Boolean isPositive) {
		label.setIcon(isPositive != null ? (isPositive ? new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-check-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)) :
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-error-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)))
				: null);
	}

	private void displayRunSuccessDialog() {
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxNetworkService().isSelected()
				|| settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxDirectoryWatchService().isSelected())
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemProcessesPanelEvent.stoppingProcessesSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemProcessesPanelEvent.stoppingProcessesSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
	}

	private void displayCheckHealthSuccessDialog() {
		if (settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxNetworkService().isSelected()
				|| settingsForm.getSettingsPanel().getSystemProcessesPanel().getChckbxDirectoryWatchService().isSelected())
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemProcessesPanelEvent.checkingProcessesHealthSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemProcessesPanelEvent.checkingProcessesHealthSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
	}
}

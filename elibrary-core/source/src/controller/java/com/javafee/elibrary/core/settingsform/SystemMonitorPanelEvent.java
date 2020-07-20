package com.javafee.elibrary.core.settingsform;

import java.awt.*;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Pair;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.RegistrationPanel;

import lombok.Setter;

public class SystemMonitorPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static SystemMonitorPanelEvent systemMonitorPanelEvent = null;

	private SystemMonitorPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static SystemMonitorPanelEvent getInstance(SettingsForm settingsForm) {
		if (systemMonitorPanelEvent == null)
			systemMonitorPanelEvent = new SystemMonitorPanelEvent(settingsForm);
		return systemMonitorPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getSystemMonitorPanel().getBtnCheckHealth().addActionListener(e -> onClickBtnCheckHealth());
	}

	@Override
	public void initializeForm() {
		reloadDetails();
	}

	private void reloadDetails() {
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesHostValue()
				.setText(SystemProperties.getInstance().getConfigProperties().getProperty("app.api.host"));
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesPortValue()
				.setText(SystemProperties.getInstance().getConfigProperties().getProperty("app.api.port"));
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesVersionValue()
				.setText(SystemProperties.getInstance().getConfigProperties().getProperty("app.api.version"));
	}

	private void onClickBtnCheckHealth() {
		reloadAllLblsForCheckedElements();
		resetAllLblsForUncheckedElements();
		displayCheckHealthSuccessDialog();
	}

	private void reloadAllLblsForCheckedElements() {
		if (settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxApiServices().isSelected()) {
			Pair<Boolean, String> elibraryApiStatus = Common.checkELibraryApiConnectivityAndGetHealthStatus();
			reloadLblsWitchImageIconAndStatus(
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesStatus(),
					elibraryApiStatus.getFirst(),
					elibraryApiStatus.getSecond());
		}
	}

	private void resetAllLblsForUncheckedElements() {
		if (!settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxApiServices().isSelected())
			reloadLblsWitchImageIconAndStatus(settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesStatus(),
					null, null);
	}


	private void reloadLblsWitchImageIconAndStatus(JLabel labelHealth, JLabel labelStatus, Boolean isPositive, String status) {
		labelHealth.setIcon(isPositive != null ? (isPositive ? new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-check-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)) :
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-error-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)))
				: null);
		if (Optional.ofNullable(labelStatus).isPresent())
			labelStatus.setText(status);
	}

	private void displayCheckHealthSuccessDialog() {
		if (settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxApiServices().isSelected())
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemMonitorPanelEvent.checkingElementsHealthStatus"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("systemProcessesPanelEvent.checkingProcessesHealthSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
	}
}
package com.javafee.elibrary.core.settingsform;

import java.awt.*;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
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
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionHostValue()
				.setText(SystemProperties.getInstance().getConfigProperties().getProperty("db.ip"));
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionNameValue()
				.setText(SystemProperties.getInstance().getConfigProperties().getProperty("db.name"));
		settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblEmailServerConnectionEmailAddressValue()
				.setText(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_EMAIL_ADDRESS).getValue());
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
		if (settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxDbConnection().isSelected()) {
			Pair<Boolean, String> elibraryDbStatus = Common.checkELibraryDbConnectivityAndGetHealthStatus();
			reloadLblsWitchImageIconAndStatus(
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionStatus(),
					elibraryDbStatus.getFirst(),
					elibraryDbStatus.getSecond());
		}
		if (settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxEmailServerConnection().isSelected()) {
			String validationError = Common.checkEmailServerConnectivity();
			reloadLblsWitchImageIconAndStatus(
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblEmailServerConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblEmailServerConnectionStatus(),
					Strings.isEmpty(validationError),
					Strings.isEmpty(validationError) ? SystemProperties.getInstance().getResourceBundle()
							.getString("systemMonitorPanel.chckbxEmailServerConnectionStatusOk") : validationError);
		}
		if (settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxInternetConnection().isSelected()) {
			boolean internetConnectivity = Common.checkInternetConnectivity();
			reloadLblsWitchImageIconAndStatus(
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblInternetConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblInternetConnectionStatus(),
					internetConnectivity,
					internetConnectivity ? SystemProperties.getInstance().getResourceBundle()
							.getString("systemMonitorPanel.chckbxEmailServerConnectionStatusOk") : null);
		}
	}

	private void resetAllLblsForUncheckedElements() {
		if (!settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxApiServices().isSelected())
			reloadLblsWitchImageIconAndStatus(settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblApiServicesStatus(),
					null, null);
		if (!settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxDbConnection().isSelected())
			reloadLblsWitchImageIconAndStatus(settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblDbConnectionStatus(),
					null, null);
		if (!settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxEmailServerConnection().isSelected())
			reloadLblsWitchImageIconAndStatus(settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblEmailServerConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblEmailServerConnectionStatus(),
					null, null);
		if (!settingsForm.getSettingsPanel().getSystemMonitorPanel().getChckbxInternetConnection().isSelected())
			reloadLblsWitchImageIconAndStatus(settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblInternetConnectionHealth(),
					settingsForm.getSettingsPanel().getSystemMonitorPanel().getLblInternetConnectionStatus(),
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
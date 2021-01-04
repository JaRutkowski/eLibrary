package com.javafee.elibrary.core.settingsform;

import java.awt.*;
import java.util.Optional;

import javax.swing.ImageIcon;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.RegistrationPanel;

import lombok.Setter;

public class SystemInstallationPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static SystemInstallationPanelEvent systemInstallationPanelEvent = null;

	private SystemInstallationPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static SystemInstallationPanelEvent getInstance(SettingsForm settingsForm) {
		if (systemInstallationPanelEvent == null)
			systemInstallationPanelEvent = new SystemInstallationPanelEvent(settingsForm);
		return systemInstallationPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();
	}

	@Override
	public void initializeForm() {
		reloadLblsDynamic();
	}

	private void reloadLblsDynamic() {
		reloadLblVersion();
		reloadLblBuildNumber();
		reloadLblBuildDate();
		reloadLblBuildStatus();

		reloadContentPanel();
		//TODO
		// - Build Logs (API) - #279
		// - DB logs (dbchangelog) - US under #39
		// - db version under 'System data' > 'Installation' (from dbchangelog) - US under #39
	}

	private void reloadContentPanel() {
		settingsForm.getSettingsPanel().reloadContentPanel(settingsForm, settingsForm.getSettingsPanel().getSystemInstallationPanel());
	}

	private void reloadLblVersion() {
		settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblVersionValue()
				.setText(Optional.ofNullable(Common.fetchSystemVersion()).isPresent()
						? Common.constructVersionString(Common.fetchSystemVersion())
						: SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.none"));
	}

	private void reloadLblBuildNumber() {
		settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblBuildNumberValue()
				.setText(Optional.ofNullable(Common.fetchSystemVersion()).isPresent()
						? Common.fetchSystemVersion().getSourceBlob().getVersion()
						: SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.none"));
	}

	private void reloadLblBuildDate() {
		String versionDateString = Optional.ofNullable(Common.fetchSystemVersion()).isPresent()
				? Constants.APPLICATION_DATE_FORMAT.format(Common.fetchSystemVersion().getCreatedAt())
				: SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.none");
		settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblLastBuildDateValue()
				.setText(versionDateString);
	}

	private void reloadLblBuildStatus() {
		if (Optional.ofNullable(Common.fetchSystemVersion()).isPresent())
			if (Constants.APPLICATION_BUILD_STATUS_SUCCEEDED.equals(Common.fetchSystemVersion().getStatus()))
				settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblLastBuildStatusValue().setIcon(
						new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-check-ico.png"))
								.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
			else
				settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblLastBuildStatusValue().setIcon(
						new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-error-ico.png"))
								.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		else
			settingsForm.getSettingsPanel().getSystemInstallationPanel().getLblLastBuildDateValue()
					.setText(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.none"));
	}
}

package com.javafee.settingsform;

import javax.swing.JOptionPane;

import com.javafee.common.Constants;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.startform.LogInEvent;
import com.javafee.tabbedform.Actions;

import lombok.Setter;

public class FontPanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static FontPanelEvent fontPanelEvent = null;

	private FontPanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static FontPanelEvent getInstance(SettingsForm settingsForm) {
		if (fontPanelEvent == null)
			fontPanelEvent = new FontPanelEvent(settingsForm);

		return fontPanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getFontPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
	}

	@Override
	public void initializeForm() {

	}

	private void onClickBtnAccept() {
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.applicationFontChange"),
				"") == JOptionPane.YES_OPTION) {
			String fontName = settingsForm.getSettingsPanel().getFontPanel().getFontChooser().getSelectedFont().getFontName();
			Integer fontSize = settingsForm.getSettingsPanel().getFontPanel().getFontChooser().getSelectedFont().getSize();
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getSystemProperties() != null;

			com.javafee.hibernate.dto.common.SystemProperties systemProperties = Common
					.checkAndGetSystemProperties(LogInEvent.getUserData() != null ? LogInEvent.getUserData().getIdUserData()
							: Constants.DATA_BASE_ADMIN_ID);

			HibernateUtil.beginTransaction();
			if (!systemPropertiesAlreadyExists) {
				systemProperties.setFontName(fontName);
				systemProperties.setFontSize(fontSize);
				LogInEvent.getUserData().setSystemProperties(systemProperties);
				HibernateUtil.getSession().update(UserData.class.getName(), LogInEvent.getUserData());
			} else {
				LogInEvent.getUserData().getSystemProperties().setFontName(fontName);
				LogInEvent.getUserData().getSystemProperties().setFontSize(fontSize);
				HibernateUtil.getSession().update(SystemProperties.class.getName(), LogInEvent.getUserData().getSystemProperties());
			}
			HibernateUtil.commitTransaction();
			delegateLogOutActionExecution();
		}
	}

	private void delegateLogOutActionExecution() {
		settingsForm.getFrame().dispose();
		settingsForm = null;
		((Actions) Params.getInstance().get("TABBED_FORM_ACTIONS")).onClickBtnLogOut();
		Params.getInstance().remove("TABBED_FORM_ACTIONS");
	}
}

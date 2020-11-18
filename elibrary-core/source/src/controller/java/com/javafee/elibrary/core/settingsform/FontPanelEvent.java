package com.javafee.elibrary.core.settingsform;

import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.core.tabbedform.Actions;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.common.UserData;

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
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getUserAccount().getSystemProperties() != null;

			com.javafee.elibrary.hibernate.dto.common.SystemProperties systemProperties = Common
					//FIXME: null check
					.checkAndGetSystemProperties(LogInEvent.getUserData() != null ? LogInEvent.getUserData().getUserAccount().getIdUserAccount()
							: Constants.DATA_BASE_ADMIN_ID);

			HibernateUtil.beginTransaction();
			if (!systemPropertiesAlreadyExists) {
				systemProperties.setFontName(fontName);
				systemProperties.setFontSize(fontSize);
				LogInEvent.getUserData().getUserAccount().setSystemProperties(systemProperties);
				HibernateUtil.getSession().update(UserData.class.getName(), LogInEvent.getUserData());
			} else {
				LogInEvent.getUserData().getUserAccount().getSystemProperties().setFontName(fontName);
				LogInEvent.getUserData().getUserAccount().getSystemProperties().setFontSize(fontSize);
				HibernateUtil.getSession().update(SystemProperties.class.getName(), LogInEvent.getUserData().getUserAccount().getSystemProperties());
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

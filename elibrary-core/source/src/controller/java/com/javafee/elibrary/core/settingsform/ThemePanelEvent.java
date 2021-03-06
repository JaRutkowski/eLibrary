package com.javafee.elibrary.core.settingsform;

import java.awt.*;

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

public class ThemePanelEvent implements IActionForm {
	@Setter
	private SettingsForm settingsForm;

	protected static ThemePanelEvent themePanelEvent = null;

	private Color color = null;

	private ThemePanelEvent(SettingsForm settingsForm) {
		this.control(settingsForm);
	}

	public static ThemePanelEvent getInstance(SettingsForm settingsForm) {
		if (themePanelEvent == null)
			themePanelEvent = new ThemePanelEvent(settingsForm);

		return themePanelEvent;
	}

	public void control(SettingsForm settingsForm) {
		setSettingsForm(settingsForm);
		initializeForm();

		settingsForm.getSettingsPanel().getThemePanel().getColorChooser().getSelectionModel().addChangeListener(e -> onChangeColor());
		settingsForm.getSettingsPanel().getThemePanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
	}

	@Override
	public void initializeForm() {

	}

	private void onChangeColor() {
		this.color = settingsForm.getSettingsPanel().getThemePanel().getColorChooser().getColor();
	}

	private void onClickBtnAccept() {
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.applicationThemeChange"),
				"") == JOptionPane.YES_OPTION) {
			StringBuilder applicationColor = new StringBuilder().append(color.getRed()).append(",")
					.append(color.getGreen()).append(",").append(color.getBlue());
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getUserAccount().getSystemProperties() != null;

			com.javafee.elibrary.hibernate.dto.common.SystemProperties systemProperties = Common
					//FIXME: null check
					.checkAndGetSystemProperties(LogInEvent.getUserData() != null ? LogInEvent.getUserData().getUserAccount().getIdUserAccount()
							: Constants.DATA_BASE_ADMIN_ID);

			HibernateUtil.beginTransaction();
			if (!systemPropertiesAlreadyExists) {
				systemProperties.setColor(applicationColor.toString());
				LogInEvent.getUserData().getUserAccount().setSystemProperties(systemProperties);
				HibernateUtil.getSession().update(UserData.class.getName(), LogInEvent.getUserData());
			} else {
				LogInEvent.getUserData().getUserAccount().getSystemProperties().setColor(applicationColor.toString());
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

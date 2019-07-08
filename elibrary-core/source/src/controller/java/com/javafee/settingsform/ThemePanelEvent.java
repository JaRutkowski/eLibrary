package com.javafee.settingsform;

import java.awt.*;

import javax.swing.JOptionPane;

import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.startform.LogInEvent;
import com.javafee.tabbedform.TabbedForm;

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

			com.javafee.hibernate.dto.common.SystemProperties systemProperties;

			if(LogInEvent.getUserData().getSystemProperties() != null) {
				LogInEvent.getUserData().getSystemProperties().setColor(applicationColor.toString());
				systemProperties = LogInEvent.getUserData().getSystemProperties();
			} else {
				systemProperties = new com.javafee.hibernate.dto.common.SystemProperties();
				systemProperties.setUserData(LogInEvent.getUserData());
				systemProperties.setColor(applicationColor.toString());
			}

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().saveOrUpdate(systemProperties);
			HibernateUtil.commitTransaction();

			onClickBtnLogOut();
		}
	}

	private void onClickBtnLogOut() {
		LogInEvent.clearLogInData();
		settingsForm.getFrame().dispose();
		settingsForm = null;
		((TabbedForm) Params.getInstance().get("TABBED_FORM")).getFrame().dispose();
		Params.getInstance().remove("TABBED_FORM");
		openStartForm();
	}

	private void openStartForm() {
		com.javafee.startform.Actions actions = new com.javafee.startform.Actions();
		actions.control();
	}
}

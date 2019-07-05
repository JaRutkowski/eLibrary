package com.javafee.emailform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.javafee.common.Constants;
import com.javafee.common.Constants.Tab_Email;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.Utils;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dto.common.SystemProperties;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.startform.LogInEvent;

public class Actions implements IActionForm {
	private EmailForm emailForm;

	public void control() {
		openEmailForm();
		setVisibleControls();
		initializeForm();

		emailForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());
		emailForm.getMenuSaveAsTemplate().addActionListener(e -> onClickMenuSaveAsTemplate());
		emailForm.getMenuLoadTemplate().addActionListener(e -> onClickMenuLoadTemplate());

		emailForm.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				emailForm.getFrame().dispose();
				emailForm = null;
				clearEvent();
			}
		});
	}

	private void openEmailForm() {
		if (emailForm == null || (emailForm != null && !emailForm.getFrame().isDisplayable())) {
			emailForm = new EmailForm();
			emailForm.getFrame().setVisible(true);
		} else {
			emailForm.getFrame().toFront();
		}
	}

	@Override
	public void initializeForm() {
		initializeTabbedPane();
		reloadTabbedPane();
	}

	private void initializeTabbedPane() {
		emailForm.getTabbedPane().addTab(
				com.javafee.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabComposePageTitle"), null,
				emailForm.getPanelComposePage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabOutboxPageTitle"), null,
				emailForm.getPanelOutboxPage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabWorkingCopyPageTitle"), null,
				emailForm.getPanelDraftPage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabTemplatePageTitle"), null,
				emailForm.getPanelTemplatePage(), null);
	}

	private void reloadTabbedPane() {
		switch (Tab_Email.getByNumber(emailForm.getTabbedPane().getSelectedIndex())) {
			case TAB_CREATE_PAGE:
				TabComposePageEvent.getInstance(emailForm);
				break;
			case TAB_SENDED_PAGE:
				TabOutboxPageEvent.getInstance(emailForm);
				break;
			case TAB_DRAFT_PAGE:
				TabDraftPageEvent.getInstance(emailForm);
				break;
			case TAB_TEMPLATE_PAGE:
				TabTemplatePageEvent.getInstance(emailForm);
				break;
			default:
				break;
		}

		setEnableMenu(Tab_Email.getByNumber(emailForm.getTabbedPane().getSelectedIndex()));
	}

	private void setEnableMenu(Tab_Email tabEmail) {
		emailForm.getMenuBar().setVisible(tabEmail == Tab_Email.TAB_CREATE_PAGE);
	}

	private void onChangeTabbedPane() {
		reloadTabbedPane();
	}

	private void onClickMenuSaveAsTemplate() {
		if (validate()) {
			SystemProperties systemProperties = Common
					.checkAndGetSystemProperties(LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
							: Constants.DATA_BASE_ADMIN_ID);

			if (systemProperties.getTemplateDirectory() == null) {
				if (Utils.displayConfirmDialog(com.javafee.common.SystemProperties.getInstance().getResourceBundle()
						.getString("confirmDialog.initialTemplateLibrary"), "") == JOptionPane.YES_OPTION) {
					File result = Utils.displaySaveDialogAndGetFile(null);
					if (result != null) {
						try {
							Files.write(Paths.get(result.getPath()),
									Arrays.asList(emailForm.getPanelComposePage().getEditorPaneContent().getText()),
									Charset.forName(Constants.APPLICATION_TEMPLATE_ENCODING));

							systemProperties.setTemplateDirectory(result.getParent());
							LogInEvent.getWorker().setSystemProperties(systemProperties);

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().update(UserData.class.getName(), LogInEvent.getWorker());
							HibernateUtil.commitTransaction();

							Utils.displayOptionPane(
									com.javafee.common.SystemProperties.getInstance().getResourceBundle()
											.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccess"),
									com.javafee.common.SystemProperties.getInstance().getResourceBundle()
											.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				File result = Utils.displaySaveDialogAndGetFile(systemProperties.getTemplateDirectory());
				if (result != null) {
					try {
						Files.write(Paths.get(result.getPath()),
								Arrays.asList(emailForm.getPanelComposePage().getEditorPaneContent().getText()),
								Charset.forName(Constants.APPLICATION_TEMPLATE_ENCODING));

						Utils.displayOptionPane(
								com.javafee.common.SystemProperties.getInstance().getResourceBundle()
										.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccess"),
								com.javafee.common.SystemProperties.getInstance().getResourceBundle()
										.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			Utils.displayOptionPane(
					com.javafee.common.SystemProperties.getInstance().getResourceBundle()
							.getString("tabTemplatePage.validateSaveTemplateToLibraryError"),
					com.javafee.common.SystemProperties.getInstance().getResourceBundle().getString(
							"tabTemplatePage.validateSaveTemplateToLibraryErrorTitle"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onClickMenuLoadTemplate() {
		SystemProperties systemProperties = Common
				.checkAndGetSystemProperties(LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
						: Constants.DATA_BASE_ADMIN_ID);

		if (systemProperties.getTemplateDirectory() == null) {
			if (Utils.displayConfirmDialog(com.javafee.common.SystemProperties.getInstance().getResourceBundle()
					.getString("confirmDialog.loadFromTemplateLibraryNoDirectory"), "") == JOptionPane.YES_OPTION) {
				File result = Utils.displayOpenDialogAndGetFile(null);

				systemProperties.setTemplateDirectory(result.getParent());

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(SystemProperties.class.getName(), systemProperties);
				HibernateUtil.commitTransaction();

				fillEditorPaneContentWithFile(result);
			}
		} else {
			File result = Utils.displayOpenDialogAndGetFile(systemProperties.getTemplateDirectory());
			fillEditorPaneContentWithFile(result);
		}
	}

	private void fillEditorPaneContentWithFile(File file) {
		try {
			emailForm.getPanelComposePage().getEditorPaneContent()
					.setText(file != null && Files.lines(Paths.get(file.getPath())) != null
							? Files.lines(Paths.get(file.getPath())).collect(Collectors.joining("\n"))
							: "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setVisibleControls() {
		if (Params.getInstance().get("NO_INTERNET_CONNVECTIVITY") != null
				&& !(boolean) Params.getInstance().get("NO_INTERNET_CONNVECTIVITY")) {
			setNetworkControls(false);
			Params.getInstance().remove("NO_INTERNET_CONNVECTIVITY");
		}
	}

	private void setNetworkControls(boolean enable) {
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSend().setEnabled(enable);
		emailForm.getPanelOutboxPage().getOutboxNavigationPanel().getBtnSendAgain().setEnabled(enable);
		emailForm.getPanelDraftPage().getDraftNavigationPanel().getBtnSend().setEnabled(enable);
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnParse().setEnabled(enable);
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnValidate().setEnabled(enable);
	}

	private void clearEvent() {
		TabComposePageEvent.composePageEvent = null;
		TabOutboxPageEvent.outboxPageEvent = null;
		TabDraftPageEvent.draftPageEvent = null;
		TabTemplatePageEvent.templatePageEvent = null;
	}

	private boolean validate() {
		return !"".equals(emailForm.getPanelComposePage().getEditorPaneContent().getText());
	}
}

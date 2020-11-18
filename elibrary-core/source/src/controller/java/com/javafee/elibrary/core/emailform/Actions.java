package com.javafee.elibrary.core.emailform;

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

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Tab_Email;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties;
import com.javafee.elibrary.hibernate.dto.common.UserData;

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
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabComposePageTitle"), null,
				emailForm.getPanelComposePage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabOutboxPageTitle"), null,
				emailForm.getPanelOutboxPage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabWorkingCopyPageTitle"), null,
				emailForm.getPanelDraftPage(), null);
		emailForm.getTabbedPane().addTab(
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("emailForm.tabTemplatePageTitle"), null,
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
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getUserAccount().getSystemProperties() != null;
			SystemProperties systemProperties = Common
					//FIXME: null check
					.checkAndGetSystemProperties(LogInEvent.getWorker() != null ? LogInEvent.getWorker().getUserAccount().getIdUserAccount()
							: Constants.DATA_BASE_ADMIN_ID);

			if (systemProperties.getTemplateDirectory() == null) {
				if (Utils.displayConfirmDialog(com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
						.getString("confirmDialog.initialTemplateLibrary"), "") == JOptionPane.YES_OPTION) {
					File result = Utils.displaySaveDialogAndGetTemplateFile(null);
					if (result != null) {
						try {
							Files.write(Paths.get(result.getPath()),
									Arrays.asList(emailForm.getPanelComposePage().getEditorPaneContent().getText()),
									Charset.forName(Constants.APPLICATION_TEMPLATE_ENCODING));

							if (!systemPropertiesAlreadyExists) {
								systemProperties.setTemplateDirectory(result.getParent());
								LogInEvent.getUserData().getUserAccount().setSystemProperties(systemProperties);

								HibernateUtil.beginTransaction();
								HibernateUtil.getSession().update(UserData.class.getName(), LogInEvent.getUserData());
								HibernateUtil.commitTransaction();
							} else {
								HibernateUtil.beginTransaction();
								LogInEvent.getUserData().getUserAccount().getSystemProperties().setTemplateDirectory(result.getParent());
								HibernateUtil.getSession().update(SystemProperties.class.getName(), LogInEvent.getUserData().getUserAccount().getSystemProperties());
								HibernateUtil.commitTransaction();
							}

							Utils.displayOptionPane(
									com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
											.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccess"),
									com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
											.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccessTitle"),
									JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				File result = Utils.displaySaveDialogAndGetTemplateFile(systemProperties.getTemplateDirectory());
				if (result != null) {
					try {
						Files.write(Paths.get(result.getPath()),
								Arrays.asList(emailForm.getPanelComposePage().getEditorPaneContent().getText()),
								Charset.forName(Constants.APPLICATION_TEMPLATE_ENCODING));

						Utils.displayOptionPane(
								com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
										.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccess"),
								com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
										.getString("tabTemplatePageEvent.savingTemplateIntoLibrarySuccessTitle"),
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			Utils.displayOptionPane(
					com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
							.getString("tabTemplatePage.validateSaveTemplateToLibraryError"),
					com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString(
							"tabTemplatePage.validateSaveTemplateToLibraryErrorTitle"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onClickMenuLoadTemplate() {
		SystemProperties systemProperties = Common
				//FIXME: null check
				.checkAndGetSystemProperties(LogInEvent.getWorker() != null ? LogInEvent.getWorker().getUserAccount().getIdUserAccount()
						: Constants.DATA_BASE_ADMIN_ID);

		if (systemProperties.getTemplateDirectory() == null) {
			if (Utils.displayConfirmDialog(com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
					.getString("confirmDialog.loadFromTemplateLibraryNoDirectory"), "") == JOptionPane.YES_OPTION) {
				File result = Utils.displayOpenDialogAndGetTemplateFile(null);

				systemProperties.setTemplateDirectory(result.getParent());

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().update(SystemProperties.class.getName(), systemProperties);
				HibernateUtil.commitTransaction();

				fillEditorPaneContentWithFile(result);
			}
		} else {
			File result = Utils.displayOpenDialogAndGetTemplateFile(systemProperties.getTemplateDirectory());
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
		boolean internetConnectivity = !(Params.getInstance().get("NO_INTERNET_CONNECTIVITY") != null
				&& !(boolean) Params.getInstance().get("NO_INTERNET_CONNECTIVITY")),
				emailServerConnectivity = !(Params.getInstance().get("NO_EMAIL_SERVER_CONNECTIVITY") != null
						&& !(boolean) Params.getInstance().get("NO_EMAIL_SERVER_CONNECTIVITY"));

		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSend().setEnabled(internetConnectivity && emailServerConnectivity);
		emailForm.getPanelOutboxPage().getOutboxNavigationPanel().getBtnSendAgain().setEnabled(internetConnectivity && emailServerConnectivity);
		emailForm.getPanelDraftPage().getDraftNavigationPanel().getBtnSend().setEnabled(internetConnectivity && emailServerConnectivity);
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnParse().setEnabled(internetConnectivity);
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnValidate().setEnabled(internetConnectivity);

		Params.getInstance().remove("NO_INTERNET_CONNECTIVITY");
		Params.getInstance().remove("NO_EMAIL_SERVER_CONNECTIVITY");
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

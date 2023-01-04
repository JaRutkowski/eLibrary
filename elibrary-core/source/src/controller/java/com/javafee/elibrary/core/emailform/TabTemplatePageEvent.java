package com.javafee.elibrary.core.emailform;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.HTMLProcessor;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties;
import com.javafee.elibrary.hibernate.dto.common.UserData;

import lombok.Setter;

public class TabTemplatePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	protected static TabTemplatePageEvent templatePageEvent = null;

	private HTMLProcessor validator = null;

	private TabTemplatePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabTemplatePageEvent getInstance(EmailForm emailForm) {
		if (templatePageEvent == null)
			templatePageEvent = new TabTemplatePageEvent(emailForm);

		return templatePageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();

		emailForm.getPanelTemplatePage().getTemplateManagementPanel().getComboBoxLibraryTemplate()
				.addActionListener(e -> onChangeComboBoxLibraryTemplate());
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnParse().addActionListener(e -> onClickBtnParse());
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getBtnValidate()
				.addActionListener(e -> onClickBtnValidate());
		emailForm.getPanelTemplatePage().getTemplateManagementPanel().getBtnSaveTemplateToLibrary()
				.addActionListener(e -> onClickBtnSaveTemplateToLibrary());
		emailForm.getPanelTemplatePage().getTemplateManagementPanel().getBtnPreviewTemplateLibrary()
				.addActionListener(e -> onClickBtnPreviewTemplateLibrary());
		emailForm.getPanelTemplatePage().getRootPane().setDefaultButton(emailForm.getPanelTemplatePage()
				.getHtmlEditorPanel().getBtnParse());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxLibraryTemplate();
		registerWatchServiceListener();
	}

	private void reloadComboBoxLibraryTemplate() {
		DefaultComboBoxModel<String> comboBoxLibraryTemplateModel = new DefaultComboBoxModel<String>();
		Optional<SystemProperties> systemProperties = Common.findSystemPropertiesByUserAccountId(
				LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData() : Constants.DATA_BASE_ADMIN_ID);
		if (systemProperties.isPresent() && !Strings.isEmpty(systemProperties.get().getTemplateDirectory())) {
			File[] files = new File(systemProperties.get().getTemplateDirectory()).listFiles();
			List<String> names = Arrays.asList(files).parallelStream().map(file -> file.getName())
					.collect(Collectors.toList());
			com.javafee.elibrary.core.common.Common.prepareBlankComboBoxElement(names);
			names.sort(Comparator.nullsFirst(String::compareToIgnoreCase));
			names.forEach(ud -> comboBoxLibraryTemplateModel.addElement(ud));
			emailForm.getPanelTemplatePage().getTemplateManagementPanel().getComboBoxLibraryTemplate()
					.setModel(comboBoxLibraryTemplateModel);
		}
	}

	private void reloadListStatus(String[] messages) {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getListStatus().setListData(messages);
	}

	private void registerWatchServiceListener() {
		Optional<SystemProperties> systemProperties = Common.findSystemPropertiesByUserAccountId(
				LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData() : Constants.DATA_BASE_ADMIN_ID);
		if (systemProperties.isPresent() && !Strings.isEmpty(systemProperties.get().getTemplateDirectory())) {
			com.javafee.elibrary.core.common.Common.registerWatchServiceListener(this, c -> this.reloadComboBoxLibraryTemplate());
		}
	}

	private void onClickBtnParse() {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getEditorPanePreview()
				.setText(emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().getText());
		String htmlText = emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().getText();

		if (validator == null)
			validator = new HTMLProcessor(htmlText);
		else
			validator.setHtmlString(htmlText);

		String[] messages = validator.getMessagesList(true).toArray(new String[validator.getMessagesList(true).size()]);
		reloadListStatus(messages);

		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor()
				.setText(validator.getHtmlString());
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getEditorPanePreview()
				.setText(validator.getHtmlString());

	}

	private void onClickBtnValidate() {
		String htmlText = emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().getText();

		if (validator == null)
			validator = new HTMLProcessor(htmlText);
		else
			validator.setHtmlString(htmlText);

		String[] messages = validator.getMessagesList(true).toArray(new String[validator.getMessagesList(true).size()]);
		reloadListStatus(messages);
	}

	private void onClickBtnSaveTemplateToLibrary() {
		if (validate()) {
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getUserAccount().getSystemProperties() != null;
			SystemProperties systemProperties = Common
					//FIXME: null check
					.checkAndGetSystemProperties(LogInEvent.getWorker() != null ? LogInEvent.getWorker().getUserAccount().getIdUserAccount()
							: Constants.DATA_BASE_ADMIN_ID);

			if (Strings.isEmpty(systemProperties.getTemplateDirectory())) {
				if (Utils.displayConfirmDialog(com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle()
						.getString("confirmDialog.initialTemplateLibrary"), "") == JOptionPane.YES_OPTION) {
					File result = Utils.displaySaveDialogAndGetTemplateFile(null);
					if (result != null) {
						try {
							Files.write(Paths.get(result.getPath()),
									Arrays.asList(emailForm.getPanelTemplatePage().getHtmlEditorPanel()
											.getTextAreaHTMLeditor().getText()),
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
								Arrays.asList(emailForm.getPanelTemplatePage().getHtmlEditorPanel()
										.getTextAreaHTMLeditor().getText()),
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

		reloadComboBoxLibraryTemplate();
		registerWatchServiceListener();
	}

	private void onClickBtnPreviewTemplateLibrary() {
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

				fillTextAreaHTMLeditorWithFile(result);
			}
		} else {
			File result = Utils.displayOpenDialogAndGetTemplateFile(systemProperties.getTemplateDirectory());
			fillTextAreaHTMLeditorWithFile(result);
		}
	}

	private void onChangeComboBoxLibraryTemplate() {
		String fileName = (String) emailForm.getPanelTemplatePage().getTemplateManagementPanel()
				.getComboBoxLibraryTemplate().getSelectedItem();

		if (fileName != null) {
			Optional<SystemProperties> systemProperties = Common.findSystemPropertiesByUserAccountId(
					LogInEvent.getWorker() != null ? LogInEvent.getWorker().getIdUserData()
							: Constants.DATA_BASE_ADMIN_ID);
			if (systemProperties.isPresent()) {
				File file = new File(systemProperties.get().getTemplateDirectory() + File.separator + fileName);
				clearTextAreaHTMLEditor();
				fillTextAreaHTMLeditorWithFile(file);
			}
		} else {
			clearTextAreaHTMLEditor();
		}
	}

	private void fillTextAreaHTMLeditorWithFile(File file) {
		try {
			emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor()
					.setText(file != null && Files.lines(Paths.get(file.getPath())) != null
							? Files.lines(Paths.get(file.getPath())).collect(Collectors.joining("\n"))
							: "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearTextAreaHTMLEditor() {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().setText("");
	}

	private boolean validate() {
		return !"".equals(emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().getText());
	}
}

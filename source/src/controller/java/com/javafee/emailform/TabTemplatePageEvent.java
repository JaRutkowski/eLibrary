package com.javafee.emailform;

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

import com.javafee.common.Constans;
import com.javafee.common.HTMLProcessor;
import com.javafee.common.IActionForm;
import com.javafee.common.Utils;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dto.common.SystemProperties;
import com.javafee.startform.LogInEvent;

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

	}

	@Override
	public void initializeForm() {
		reloadComboBoxLibraryTemplate();
	}

	private void reloadComboBoxLibraryTemplate() {
		DefaultComboBoxModel<String> comboBoxLibraryTemplateModel = new DefaultComboBoxModel<String>();
		Optional<SystemProperties> systemProperties = (Optional<SystemProperties>) Common
				.findSystemPropertiesByUserDataId(LogInEvent.getWorker().getIdUserData());
		if (systemProperties.isPresent()) {
			File[] files = new File(systemProperties.get().getTemplateDirectory()).listFiles();
			List<String> names = Arrays.asList(files).parallelStream().map(file -> file.getName())
					.collect(Collectors.toList());
			com.javafee.common.Common.prepareBlankComboBoxElement(names);
			names.sort(Comparator.nullsFirst(String::compareToIgnoreCase));
			names.forEach(ud -> comboBoxLibraryTemplateModel.addElement(ud));
			emailForm.getPanelTemplatePage().getTemplateManagementPanel().getComboBoxLibraryTemplate()
					.setModel(comboBoxLibraryTemplateModel);
		}
	}

	private void reloadListStatus(String[] messages) {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getListStatus().setListData(messages);
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
				.setText(validator.getHtmlString().toString());
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getEditorPanePreview()
				.setText(validator.getHtmlString().toString());

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
			SystemProperties systemProperties = Common
					.checkAndGetSystemProperties(LogInEvent.getWorker().getIdUserData());

			if (systemProperties.getTemplateDirectory() == null) {
				if (Utils.displayConfirmDialog(com.javafee.common.SystemProperties.getInstance().getResourceBundle()
						.getString("confirmDialog.initialTemplateLibrary"), "") == JOptionPane.YES_OPTION) {
					File result = ((File) Utils.displayJFileChooserAndGetFile(null));
					if (result != null) {
						try {
							Files.write(Paths.get(result.getPath()),
									Arrays.asList(emailForm.getPanelTemplatePage().getHtmlEditorPanel()
											.getTextAreaHTMLeditor().getText()),
									Charset.forName(Constans.APPLICATION_TEMPLATE_ENCODING));

							systemProperties.setTemplateDirectory(result.getParent());

							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().update(SystemProperties.class.getName(), systemProperties);
							HibernateUtil.commitTransaction();

							reloadComboBoxLibraryTemplate();

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
				File result = ((File) Utils.displayJFileChooserAndGetFile(systemProperties.getTemplateDirectory()));
				if (result != null) {
					try {
						Files.write(Paths.get(result.getPath()),
								Arrays.asList(emailForm.getPanelTemplatePage().getHtmlEditorPanel()
										.getTextAreaHTMLeditor().getText()),
								Charset.forName(Constans.APPLICATION_TEMPLATE_ENCODING));

						reloadComboBoxLibraryTemplate();

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

	private void onClickBtnPreviewTemplateLibrary() {

	}

	private void onChangeComboBoxLibraryTemplate() {
		String fileName = (String) emailForm.getPanelTemplatePage().getTemplateManagementPanel()
				.getComboBoxLibraryTemplate().getSelectedItem();

		if (fileName != null) {
			Optional<SystemProperties> systemProperties = (Optional<SystemProperties>) Common
					.findSystemPropertiesByUserDataId(LogInEvent.getWorker().getIdUserData());
			if (systemProperties.isPresent()) {
				File file = new File(systemProperties.get().getTemplateDirectory() + File.separator + fileName);
				try {
					clearTextAreaHTMLEditor();
					Files.lines(file.toPath()).forEach(e -> {
						emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().append(e + "\n");
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			clearTextAreaHTMLEditor();
		}
	}

	private void clearTextAreaHTMLEditor() {
		emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().setText("");
	}

	private boolean validate() {
		return !"".equals(emailForm.getPanelTemplatePage().getHtmlEditorPanel().getTextAreaHTMLeditor().getText());
	}
}

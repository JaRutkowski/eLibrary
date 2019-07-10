package com.javafee.tabbedform;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.javafee.common.Common;
import com.javafee.common.Constants;
import com.javafee.common.Constants.Role;
import com.javafee.common.Constants.Tab_Accountant;
import com.javafee.common.Constants.Tab_Client;
import com.javafee.common.Constants.Tab_Worker;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.LogInEvent;

public class Actions implements IActionForm {
	private TabbedForm tabbedForm = new TabbedForm();

	private com.javafee.settingsform.Actions actionSettings = null;

	public void control() {
		tabbedForm.getFrame().setVisible(true);
		initializeForm();

		tabbedForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());
		tabbedForm.getBtnSettings().addActionListener(e -> onClickBtnSettings());
		tabbedForm.getBtnLogOut().addActionListener(e -> onClickBtnLogOut());
		tabbedForm.getComboBoxLanguage().addActionListener(e -> onChangeComboBoxLanguage());
	}

	private void onChangeComboBoxLanguage() {
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.languageChange"),
				"") == JOptionPane.YES_OPTION) {
			Constants.APPLICATION_LANGUAGE = (String) tabbedForm.getComboBoxLanguage().getSelectedItem();
			SystemProperties.getInstance()
					.setResourceBundleLanguage((String) tabbedForm.getComboBoxLanguage().getSelectedItem());
			onClickBtnLogOut();
		}
	}

	@Override
	public void initializeForm() {
		tabbedForm.getComboBoxLanguage().addItem(Constants.APPLICATION_LANGUAGE_PL);
		tabbedForm.getComboBoxLanguage().addItem(Constants.APPLICATION_LANGUAGE_EN);
		Common.registerTimerServiceListenerSingleton(tabbedForm.getLblTime());
		reloadLblLogInInformationDynamic();
		registerNetworkServiceListener();
		initializeTabbedPane();
		reloadTabbedPane();
	}

	private void initializeTabbedPane() {
		if (LogInEvent.getRole() == Role.CLIENT) {
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLibraryTitle"), null,
					tabbedForm.getPanelLibrary(), null);
			tabbedForm.pack();
		}

		if (LogInEvent.getRole() == Role.WORKER_LIBRARIAN) {
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabClientTitle"), null,
					tabbedForm.getPanelClient(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLibraryTitle"), null,
					tabbedForm.getPanelLibrary(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabBookTitle"), null,
					tabbedForm.getPanelBook(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLoanServiceTitle"),
					null, tabbedForm.getPanelLoanService(), null);
			tabbedForm.pack();
		}

		if (LogInEvent.getRole() == Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Role.ADMIN) {
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabClientTitle"), null,
					tabbedForm.getPanelClient(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLibraryTitle"), null,
					tabbedForm.getPanelLibrary(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabBookTitle"), null,
					tabbedForm.getPanelBook(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLoanServiceTitle"),
					null, tabbedForm.getPanelLoanService(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabAdmDictionaryTitle"),
					null, tabbedForm.getPanelAdmDictionary(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabWorkerTitle"), null,
					tabbedForm.getPanelWorker(), null);
			tabbedForm.pack();
		}
	}

	private void reloadLblLogInInformationDynamic() {
		StringBuilder logInInformation = new StringBuilder(tabbedForm.getLblLogInInformation().getText());

		Role role = LogInEvent.getRole();
		String stringRole = null;
		if (role == Role.CLIENT)
			stringRole = Constants.CLIENT;
		else if (role == Role.ADMIN)
			stringRole = Constants.ROLE_ADMIN;
		else if (role == Role.WORKER_ACCOUNTANT)
			stringRole = Constants.WORKER_ACCOUNTANT;
		else if (role == Role.WORKER_LIBRARIAN)
			stringRole = Constants.WORKER_LIBRARIAN;

		if (LogInEvent.getWorker() != null)
			logInInformation.append(
					LogInEvent.getWorker().getName() + " " + LogInEvent.getWorker().getSurname() + ", " + stringRole
							+ " [" + Constants.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate()) + "]");
		else if (LogInEvent.getClient() != null)
			logInInformation.append(
					LogInEvent.getClient().getName() + " " + LogInEvent.getClient().getSurname() + ", " + stringRole
							+ " [" + Constants.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate()) + "]");

		if (LogInEvent.getIsAdmin())
			logInInformation.append(stringRole);
		tabbedForm.getLblLogInInformation().setText(logInInformation.toString());
	}

	public void reloadLblInternetConnectivityStatus(Icon icon) {
		tabbedForm.getLblInternetConnectivityStatus().setIcon(icon);
	}

	private void reloadTabbedPane() {
		if (LogInEvent.getRole() == Role.CLIENT)
			switch (Tab_Client.getByNumber(tabbedForm.getTabbedPane().getSelectedIndex())) {
				case TAB_LIBRARY:
					TabLibraryEvent.getInstance(tabbedForm);
					break;
				default:
					break;
			}

		if (LogInEvent.getRole() == Role.WORKER_LIBRARIAN)
			switch (Tab_Worker.getByNumber(tabbedForm.getTabbedPane().getSelectedIndex())) {
				case TAB_CLIENT:
					TabClientEvent.getInstance(tabbedForm);
					break;
				case TAB_LIBRARY:
					TabLibraryEvent.getInstance(tabbedForm);
					break;
				case TAB_BOOK:
					TabBookEvent.getInstance(tabbedForm);
					break;
				case TAB_LOAN_SERVICE:
					TabLoanServiceEvent.getInstance(tabbedForm);
					break;
				default:
					break;
			}

		if (LogInEvent.getRole() == Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Role.ADMIN)
			switch (Tab_Accountant.getByNumber(tabbedForm.getTabbedPane().getSelectedIndex())) {
				case TAB_CLIENT:
					TabClientEvent.getInstance(tabbedForm);
					break;
				case TAB_LIBRARY:
					TabLibraryEvent.getInstance(tabbedForm);
					break;
				case TAB_BOOK:
					TabBookEvent.getInstance(tabbedForm);
					break;
				case TAB_ADM_DICTIONARY:
					TabAdmDictionaryEvent.getInstance(tabbedForm);
					break;
				case TAB_LOAN_SERVICE:
					TabLoanServiceEvent.getInstance(tabbedForm);
					break;
				case TAB_ADM_WORKER:
					TabWorkerEvent.getInstance(tabbedForm);
					break;
				default:
					break;
			}
	}

	private void registerNetworkServiceListener() {
		Common.registerNetworkServiceListener(this);
	}

	public void onClickBtnLogOut() {
		LogInEvent.clearLogInData();
		Common.unregisterTimerServiceListenerSingleton();
		tabbedForm.getFrame().dispose();
		tabbedForm = null;
		clearEvents();
		openStartForm();
	}

	private void onClickBtnSettings() {
		openSettingsForm();
	}

	private void onChangeTabbedPane() {
		reloadTabbedPane();
	}

	private void openSettingsForm() {
		if(!Params.getInstance().contains("TABBED_FORM_ACTIONS"))
			Params.getInstance().add("TABBED_FORM_ACTIONS", this);
		if (actionSettings == null)
			actionSettings = new com.javafee.settingsform.Actions();
		actionSettings.control();
	}

	private void openStartForm() {
		com.javafee.startform.Actions actions = new com.javafee.startform.Actions();
		actions.control();
	}

	private void clearEvents() {
		TabClientEvent.clientEvent = null;
		TabLibraryEvent.libraryEvent = null;
		TabBookEvent.bookEvent = null;
		TabLoanServiceEvent.loadServiceEvent = null;
		TabAdmDictionaryEvent.admDictionaryEvent = null;
		TabWorkerEvent.workerEvent = null;
		//actionSettings = null;
	}
}

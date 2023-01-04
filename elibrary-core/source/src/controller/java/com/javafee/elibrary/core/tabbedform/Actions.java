package com.javafee.elibrary.core.tabbedform;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.*;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Constants.Tab_Accountant;
import com.javafee.elibrary.core.common.Constants.Tab_Client;
import com.javafee.elibrary.core.common.Constants.Tab_Worker;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Book;

import java.util.List;

public class Actions implements IActionForm {
	private TabbedForm tabbedForm = new TabbedForm();

	private com.javafee.elibrary.core.settingsform.Actions actionSettings = null;
	private com.javafee.elibrary.core.aboutform.Actions actionAbout = null;

	public void control() {
		tabbedForm.getFrame().setVisible(true);
		initializeForm();

		tabbedForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());
		tabbedForm.getBtnInformation().addActionListener(e -> onClickBtnAbout());
		tabbedForm.getBtnSettings().addActionListener(e -> onClickBtnSettings());
		tabbedForm.getBtnLogOut().addActionListener(e -> onClickBtnLogOut());
		tabbedForm.getComboBoxLanguage().addActionListener(e -> onChangeComboBoxLanguage());
		tabbedForm.getFrame().getRootPane().setDefaultButton(tabbedForm.getBtnSettings());
	}

	private void onChangeComboBoxLanguage() {
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.languageChange"),
				"") == JOptionPane.YES_OPTION) {
			boolean systemPropertiesAlreadyExists = LogInEvent.getUserData().getUserAccount().getSystemProperties() != null;
			List<com.javafee.elibrary.hibernate.dto.common.SystemProperties> somePieceOfSheet = HibernateUtil.getSession().createQuery(Query.ActionsTabbedForm.SYSTEM_PROPERTIES_BY_USER_ACCOUNT_ID.getValue()). //
					setParameter("idUserAccount", LogInEvent.getWorker() != null ? LogInEvent.getWorker().getUserAccount().getIdUserAccount()
					: Constants.DATA_BASE_ADMIN_ID).list();
			com.javafee.elibrary.hibernate.dto.common.SystemProperties systemProperties = somePieceOfSheet.get(0);
			HibernateUtil.beginTransaction();
			if (!systemPropertiesAlreadyExists) {
				systemProperties.setLanguage(com.javafee.elibrary.hibernate.dao.common.Common.findLanguageByName((String) tabbedForm.getComboBoxLanguage().getSelectedItem()).get());
				LogInEvent.getUserData().getUserAccount().setSystemProperties(systemProperties);

				HibernateUtil.getSession().evict(LogInEvent.getUserData());
				HibernateUtil.getSession().saveOrUpdate(UserData.class.getName(), LogInEvent.getUserData());
			} else {
				systemProperties.setLanguage(com.javafee.elibrary.hibernate.dao.common.Common.findLanguageByName((String) tabbedForm.getComboBoxLanguage().getSelectedItem()).get());
				HibernateUtil.getSession().evict(systemProperties);
				HibernateUtil.getSession().saveOrUpdate(com.javafee.elibrary.hibernate.dto.common.SystemProperties.class.getName(), systemProperties);
			}
			HibernateUtil.commitTransaction();

			onClickBtnLogOut();
		}
	}

	@Override
	public void initializeForm() {
		reloadComboBoxLanguage();
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
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabClientLoanTitle"), null,
					tabbedForm.getPanelClientLoans(), null);
			tabbedForm.getTabbedPane().addTab(
					SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabClientReservationTitle"), null,
					tabbedForm.getPanelClientReservations(), null);
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

	private void reloadComboBoxLanguage() {
		tabbedForm.getComboBoxLanguage().addItem(Constants.APPLICATION_LANGUAGE_PL);
		tabbedForm.getComboBoxLanguage().addItem(Constants.APPLICATION_LANGUAGE_EN);
		if (LogInEvent.getUserData().getUserAccount().getSystemProperties() != null
				&& LogInEvent.getUserData().getUserAccount().getSystemProperties().getLanguage() != null)
			tabbedForm.getComboBoxLanguage().setSelectedItem(
					LogInEvent.getUserData().getUserAccount().getSystemProperties().getLanguage().getName());
		else
			tabbedForm.getComboBoxLanguage().setSelectedItem(Constants.APPLICATION_LANGUAGE);
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
				case TAB_CLIENT_LOAN:
					TabClientLoanEvent.getInstance(tabbedForm);
					break;
				case TAB_CLIENT_RESERVATION:
					TabClientReservationEvent.getInstance(tabbedForm);
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

	private void onClickBtnAbout() {
		openAboutForm();
	}

	private void onClickBtnSettings() {
		openSettingsForm();
	}

	private void onChangeTabbedPane() {
		reloadTabbedPane();
	}

	private void openAboutForm() {
		if (actionAbout == null)
			actionAbout = new com.javafee.elibrary.core.aboutform.Actions();
		actionAbout.control();
	}

	private void openSettingsForm() {
		tabbedForm.getFrame().getRootPane().setDefaultButton(tabbedForm.getBtnSettings());
		if (!Params.getInstance().contains("TABBED_FORM_ACTIONS"))
			Params.getInstance().add("TABBED_FORM_ACTIONS", this);
		if (LogInEvent.getRole() != Role.CLIENT
				&& !Params.getInstance().contains("TABBED_FORM_TABLE_MODEL")
				&& tabbedForm.getTabbedPane().getSelectedIndex() == Tab_Accountant.TAB_ADM_WORKER.getValue())
			Params.getInstance().add("TABBED_FORM_TABLE_MODEL", tabbedForm.getPanelWorker().getWorkerTable().getModel());

		if (actionSettings == null)
			actionSettings = new com.javafee.elibrary.core.settingsform.Actions();
		actionSettings.control();
	}

	private void openStartForm() {
		com.javafee.elibrary.core.startform.Actions actions = new com.javafee.elibrary.core.startform.Actions();
		actions.control();
	}

	private void clearEvents() {
		TabClientEvent.clientEvent = null;
		TabLibraryEvent.libraryEvent = null;
		TabClientReservationEvent.clientReservationEvent = null;
		TabClientReservationEvent.clearDependentEvents();
		TabBookEvent.bookEvent = null;
		TabLoanServiceEvent.loadServiceEvent = null;
		TabAdmDictionaryEvent.admDictionaryEvent = null;
		TabWorkerEvent.workerEvent = null;
		//actionSettings = null;
	}
}

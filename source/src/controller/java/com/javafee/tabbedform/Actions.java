package com.javafee.tabbedform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.Constans.Tab_Accountant;
import com.javafee.common.Constans.Tab_Client;
import com.javafee.common.Constans.Tab_Worker;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.LogInEvent;

public class Actions implements IActionForm {
	private TabbedForm tabbedForm = new TabbedForm();

	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private int currentSecond;
	private Calendar calendar;

	private void reset() {
		calendar = Calendar.getInstance();
		currentSecond = calendar.get(Calendar.SECOND);
	}

	public void start() {
		reset();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (currentSecond == 60) {
					reset();
				}
				tabbedForm.getLblTime()
						.setText(String.format("%s:%02d ", sdf.format(calendar.getTime()), currentSecond));
				currentSecond++;
			}
		}, 0, 1000);
	}

	public void control() {
		tabbedForm.getFrame().setVisible(true);
		initializeForm();

		tabbedForm.getBtnInformation().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		tabbedForm.getBtnLogOut().addActionListener(e -> onClickBtnLogOut());
		tabbedForm.getTabbedPane().addChangeListener(e -> onChangeTabbedPane());

		tabbedForm.getComboBoxLanguage().addActionListener(e -> onChangeComboBoxLanguage());

	}

	private void onChangeComboBoxLanguage() {
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.languageChange"),
				"") == JOptionPane.YES_OPTION) {
			Constans.APPLICATION_LANGUAGE = (String) tabbedForm.getComboBoxLanguage().getSelectedItem();
			SystemProperties.getInstance()
					.setResourceBundleLanguage((String) tabbedForm.getComboBoxLanguage().getSelectedItem());
			onClickBtnLogOut();
		}
	}

	@Override
	public void initializeForm() {
		tabbedForm.getComboBoxLanguage().addItem(Constans.APPLICATION_LANGUAGE_PL);
		tabbedForm.getComboBoxLanguage().addItem(Constans.APPLICATION_LANGUAGE_EN);
		start();
		reloadLblLogInInformationDynamic();
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
					null, tabbedForm.getLoanServicePanel_new(), null);
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
					null, tabbedForm.getLoanServicePanel_new(), null);
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
			stringRole = Constans.CLIENT;
		else if (role == Role.ADMIN)
			stringRole = Constans.ROLE_ADMIN;
		else if (role == Role.WORKER_ACCOUNTANT)
			stringRole = Constans.WORKER_ACCOUNTANT;
		else if (role == Role.WORKER_LIBRARIAN)
			stringRole = Constans.WORKER_LIBRARIAN;

		if (LogInEvent.getWorker() != null)
			logInInformation.append(
					LogInEvent.getWorker().getName() + " " + LogInEvent.getWorker().getSurname() + ", " + stringRole
							+ " [" + Constans.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate()) + "]");
		else if (LogInEvent.getClient() != null)
			logInInformation.append(
					LogInEvent.getClient().getName() + " " + LogInEvent.getClient().getSurname() + ", " + stringRole
							+ " [" + Constans.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate()) + "]");

		if (LogInEvent.getIsAdmin())
			logInInformation.append(stringRole);
		tabbedForm.getLblLogInInformation().setText(logInInformation.toString());
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

	private void onClickBtnLogOut() {
		LogInEvent.clearLogInData();
		tabbedForm.getFrame().dispose();
		tabbedForm = null;
		clearEvents();
		openStartForm();
	}

	private void onChangeTabbedPane() {
		reloadTabbedPane();
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
	}
}

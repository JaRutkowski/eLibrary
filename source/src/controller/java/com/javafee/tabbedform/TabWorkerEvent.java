package com.javafee.tabbedform;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.IRegistrationForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedClientsEventLoadingException;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.exception.RefusedWorkerEventLoadingException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.LibraryWorker;
import com.javafee.hibernate.dto.library.Worker;
import com.javafee.model.ClientTableModel;
import com.javafee.model.WorkerTableModel;
import com.javafee.startform.LogInEvent;
import com.javafee.startform.RegistrationEvent;
import com.javafee.tabbedform.TabbedForm;

public final class TabWorkerEvent implements IActionForm {
	private TabbedForm tabbedForm;

	private static TabWorkerEvent workerEvent = null;
	private RegistrationEvent registrationEvent;
	private WorkerAddModEvent workerAddModEvent;

	private TabWorkerEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabWorkerEvent getInstance(TabbedForm tabbedForm) {
		if (workerEvent == null) {
			workerEvent = new TabWorkerEvent(tabbedForm);
		} else
			new RefusedWorkerEventLoadingException("Cannot client event loading");
		return workerEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getPanelWorker().getCockpitEditionPanel().getBtnAdd().addActionListener(e -> onClickBtnAdd());
		tabbedForm.getPanelWorker().getCockpitEditionPanel().getBtnModify().addActionListener(e -> onClickBtnModify());
		tabbedForm.getPanelWorker().getCockpitEditionPanel().getBtnDelete().addActionListener(e -> onClickBtnDelete());
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().getDecisionPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept());
		tabbedForm.getPanelWorker().getAdmIsAccountantPanel().getDecisionPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAcceptAccountant());
		tabbedForm.getPanelWorker().getWorkerTable().getModel().addTableModelListener(e -> reloadClientTable());
		tabbedForm.getPanelWorker().getWorkerTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onClientTableListSelectionChange();
		});
	}

	@Override
	public void initializeForm() {
		// reloadRegistrationPanel();
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.ADMIN || LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
	}

	public void setTabbedForm(TabbedForm tabbedForm) {
		this.tabbedForm = tabbedForm;
	}

	// @Override
	// public void reloadRegistrationPanel() {
	// reloadComboBoxCity();
	// }

	// private void reloadComboBoxCity() {
	// DefaultComboBoxModel<City> comboBoxCity = new DefaultComboBoxModel<City>();
	// HibernateDao<City, Integer> city = new HibernateDao<City,
	// Integer>(City.class);
	// List<City> cityListToSort = city.findAll();
	// cityListToSort.sort(Comparator.comparing(City::getName,
	// Comparator.nullsFirst(Comparator.naturalOrder())));
	// cityListToSort.forEach(c -> comboBoxCity.addElement(c));
	//
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getComboBoxCity().setModel(comboBoxCity);
	// }

	private void reloadChckbxIsRegistered(boolean isRegistered) {
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().getChckbxIsRegistered().setSelected(isRegistered);
	}

	private void reloadChckbxIsAccountant(boolean isAccountant) {
		tabbedForm.getPanelWorker().getAdmIsAccountantPanel().getChckbxIsAccountant().setSelected(isAccountant);
	}

	private LibraryWorker checkIfHired(Worker worker) {
		LibraryWorker libraryWorker = (LibraryWorker) HibernateUtil.getSession()
				.getNamedQuery("LibraryWorker.checkIfLibraryWorkerHiredExist")
				.setParameter("idWorker", worker.getIdUserData()).uniqueResult();
		return libraryWorker;
	}

	private void reloadClientTable() {
		tabbedForm.getPanelWorker().getWorkerTable().repaint();
	}

	private void onClickBtnAdd() {
		// if (validateRegistration())
		// onClickBtnRegisterNow();

		if (workerAddModEvent == null)
			workerAddModEvent = new WorkerAddModEvent();
		workerAddModEvent.control(Constans.Context.ADDITION,
				(WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel());

	}

	private void onClickBtnModify() {
		if (tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Worker selectedWorker = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
						.getWorker(selectedRowIndex);
				Worker workerShallowClone = (Worker) selectedWorker.clone();

				Params.getInstance().add("selectedWorkerShallowClone", workerShallowClone);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (workerAddModEvent == null)
					workerAddModEvent = new WorkerAddModEvent();
				workerAddModEvent.control(Constans.Context.MODIFICATION,
						(WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel());

				// Comment because of new addition and modification mechanism
				// clientShallowClone.setPeselNumber(
				// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldPeselNumber().getText());
				// clientShallowClone.setDocumentNumber(
				// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldDocumentNumber().getText());
				// clientShallowClone.setName(tabbedForm.getPanelClient().getClientDataPanel().getTextFieldName().getText());
				// clientShallowClone
				// .setSurname(tabbedForm.getPanelClient().getClientDataPanel().getTextFieldSurname().getText());
				// clientShallowClone
				// .setAddress(tabbedForm.getPanelClient().getClientDataPanel().getTextFieldAddress().getText());
				// clientShallowClone.setCity(
				// (City)
				// tabbedForm.getPanelClient().getClientDataPanel().getComboBoxCity().getSelectedItem());
				// clientShallowClone.setSex(tabbedForm.getPanelClient().getClientDataPanel().getRadioButtonMale().isSelected()
				// ? Constans.DATA_BASE_MALE_SIGN
				// : Constans.DATA_BASE_FEMALE_SIGN);
				// clientShallowClone.setBirthDate(
				// tabbedForm.getPanelClient().getClientDataPanel().getDateChooserBirthDate().getDate()
				// != null
				// ?
				// tabbedForm.getPanelClient().getClientDataPanel().getDateChooserBirthDate().getDate()
				// : null);
				// clientShallowClone.setLogin(tabbedForm.getPanelClient().getClientDataPanel().getTextFieldLogin().getText());

				// if (!validatePasswordFieldIsEmpty())
				// Utils.displayOptionPane(
				// SystemProperties.getInstance().getResourceBundle()
				// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2"),
				// SystemProperties.getInstance().getResourceBundle()
				// .getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2Title"),
				// JOptionPane.WARNING_MESSAGE);
				// if (Validator.validateClientUpdate(clientShallowClone)) {
				// HibernateUtil.beginTransaction();
				// HibernateUtil.getSession().evict(selectedClient);
				// HibernateUtil.getSession().update(Client.class.getName(),
				// clientShallowClone);
				// HibernateUtil.commitTransaction();
				//
				// ((ClientTableModel)
				// tabbedForm.getPanelClient().getClientTable().getModel()).setClient(selectedRowIndex,
				// clientShallowClone);
				// reloadClientTable();
				//
				// } else {
				// LogGuiException.logWarning(
				// SystemProperties.getInstance().getResourceBundle()
				// .getString("clientTableModel.constraintViolationErrorTitle"),
				// SystemProperties.getInstance().getResourceBundle()
				// .getString("clientTableModel.constraintViolationError"));
				// }
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}

		// int selectedRowIndex =
		// tabbedForm.getPanelWorker().getWorkerTable().convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());
		//
		// if (selectedRowIndex != -1) {
		// Worker selectedClient = ((WorkerTableModel)
		// tabbedForm.getPanelWorker().getWorkerTable().getModel()).getWorker(selectedRowIndex);
		// Worker clientShallowClone = (Worker) selectedClient.clone();
		//
		// clientShallowClone.setPeselNumber(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldPeselNumber().getText());
		// clientShallowClone.setDocumentNumber(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldDocumentNumber().getText());
		// clientShallowClone.setName(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldName().getText());
		// clientShallowClone.setSurname(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldSurname().getText());
		// clientShallowClone.setAddress(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldAddress().getText());
		// clientShallowClone.setCity((City)
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getComboBoxCity().getSelectedItem());
		// clientShallowClone.setSex(tabbedForm.getPanelWorker().getWorkerDataPanel().getRadioButtonMale().isSelected()
		// ? Constans.DATA_BASE_MALE_SIGN : Constans.DATA_BASE_FEMALE_SIGN);
		// clientShallowClone.setBirthDate(
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getDateChooserBirthDate().getDate()
		// != null ?
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getDateChooserBirthDate().getDate()
		// : null);
		// clientShallowClone.setLogin(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldLogin().getText());
		//
		// if (!validatePasswordFieldIsEmpty())
		// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2"),
		// SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.validatePasswordFieldIsEmptyWarning2Title"),
		// JOptionPane.WARNING_MESSAGE);
		// if (Validator.validateClientUpdate(clientShallowClone)) {
		// HibernateUtil.beginTransaction();
		// HibernateUtil.getSession().evict(selectedClient);
		// HibernateUtil.getSession().update(Worker.class.getName(),
		// clientShallowClone);
		// HibernateUtil.commitTransaction();
		//
		// ((WorkerTableModel)
		// tabbedForm.getPanelWorker().getWorkerTable().getModel()).setWorker(selectedRowIndex,
		// clientShallowClone);
		// reloadClientTable();
		//
		// } else {
		// LogGuiException.logWarning(SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.constraintViolationErrorTitle"),
		// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.constraintViolationError"));
		// }
		// }
		//
		// this.reloadClientTable();
	}

	private void onClickBtnDelete() {
		if (tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());

			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Worker selectedWorker = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
							.getWorker(selectedRowIndex);

					HibernateUtil.beginTransaction();
					HibernateUtil.getSession().delete(selectedWorker);
					HibernateUtil.commitTransaction();
					((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel()).remove(selectedWorker);

				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}
	}

	// @Override
	// public void onClickBtnRegisterNow() {
	// try {
	// Character sex =
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getGroupRadioButtonSex().getSelection()
	// != null
	// ?
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getGroupRadioButtonSex().getSelection().getActionCommand().charAt(0)
	// : null;
	// Date birthDate =
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getDateChooserBirthDate().getDate()
	// != null
	// ?
	// Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(tabbedForm.getPanelWorker().getWorkerDataPanel().getDateChooserBirthDate().getDate()))
	// : null;
	//
	// RegistrationEvent.forceClearRegistrationEvenet();
	// if (birthDate != null && birthDate.before(new Date()) &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldSurname().getText()
	// != null &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldName().getText()
	// != null
	// &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldPeselNumber().getText()
	// != null) {
	//
	// registrationEvent =
	// RegistrationEvent.getInstance(tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldPeselNumber().getText(),
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldDocumentNumber().getText(),
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldName().getText(),
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldSurname().getText(),
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldAddress().getText(),
	// (City)
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getComboBoxCity().getSelectedItem(),
	// sex, birthDate,
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldLogin().getText(),
	// String.valueOf(tabbedForm.getPanelWorker().getWorkerDataPanel().getPasswordField().getPassword()),
	// Role.WORKER_LIBRARIAN);
	//
	// WorkerTableModel ctm = (WorkerTableModel)
	// tabbedForm.getPanelWorker().getWorkerTable().getModel();
	// ctm.add((Worker) RegistrationEvent.userData);
	// } else {
	// Utils.displayOptionPane("Podana data urodzenia nie jest wczeœniejszza od
	// be¿¹cej, wype³nione muszê byæ dane nazwiska, imienia, numeru pesel.",
	// "Z³a data", JOptionPane.INFORMATION_MESSAGE);}
	// } catch (RefusedRegistrationException e) {
	// StringBuilder errorBuilder = new StringBuilder();
	//
	// if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
	// errorBuilder.append(SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError5"));
	// Params.getInstance().remove("ALREADY_REGISTERED");
	// }
	// if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
	// errorBuilder.append(SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError6"));
	// }
	// if (Params.getInstance().get("WEAK_PASSWORD") != null) {
	// errorBuilder.append(SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError7"));
	// }
	//
	// LogGuiException.logError(SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
	// errorBuilder.toString(), e);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// if (registrationEvent != null)
	// Utils.displayOptionPane(SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// }

	private void onClickBtnAccept() {
		if (validateClientTableSelection(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow())) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());
			Worker selectedClient = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
					.getWorker(selectedRowIndex);
			Worker clientShallowClone = (Worker) selectedClient.clone();

			clientShallowClone.setRegistered(
					tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().getChckbxIsRegistered().isSelected());

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().evict(selectedClient);
			HibernateUtil.getSession().update(Worker.class.getName(), clientShallowClone);
			HibernateUtil.commitTransaction();

			((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel()).setWorker(selectedRowIndex,
					clientShallowClone);
			reloadClientTable();
		} else {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.validateClientTableSelectionWarning1"),
					SystemProperties.getInstance().getResourceBundle().getString(
							"tabClientEvent.validateClientTableSelectionWarning1Title"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onClickBtnAcceptAccountant() {
		if (validateClientTableSelection(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow())) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());
			Worker selectedWorker = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
					.getWorker(selectedRowIndex);
			Worker workerShallowClone = (Worker) selectedWorker.clone();

			LibraryWorker libraryWorker = checkIfHired(workerShallowClone);

			if (libraryWorker != null) {
				workerShallowClone.getLibraryWorker().stream().collect(Collectors.toList()).get(0).setIsAccountant(
						tabbedForm.getPanelWorker().getAdmIsAccountantPanel().getChckbxIsAccountant().isSelected());

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().evict(selectedWorker);
				HibernateUtil.getSession().update(Worker.class.getName(), workerShallowClone);
				HibernateUtil.commitTransaction();

				((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel()).setWorker(selectedRowIndex,
						workerShallowClone);
				reloadClientTable();
			}
		} else {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.validateClientTableSelectionWarning1"),
					SystemProperties.getInstance().getResourceBundle().getString(
							"tabClientEvent.validateClientTableSelectionWarning1Title"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onClientTableListSelectionChange() {
		if (tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow() != -1
				&& tabbedForm.getPanelWorker().getWorkerTable()
						.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow()) != -1) {
			reloadChckbxIsRegistered(
					(SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal"))
							.equals(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(
									tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow(),
									Constans.ClientTableColumn.COL_REGISTERED.getValue())) ? true : false);
			LibraryWorker libraryWorker = checkIfHired(
					((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
							.getWorker(tabbedForm.getPanelWorker().getWorkerTable().convertRowIndexToModel(
									tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow())));
			reloadChckbxIsAccountant(libraryWorker != null ? libraryWorker.getIsAccountant() : false);
		}
	}

	private void fillRegistrationPanel(int selectedRow) {
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldPeselNumber()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_PESEL_NUMBER.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_PESEL_NUMBER.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldDocumentNumber()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_DOCUMENT_NUMBER.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_DOCUMENT_NUMBER.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldLogin()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_LOGIN.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_LOGIN.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldName()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_NAME.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_NAME.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldSurname()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SURNAME.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SURNAME.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldAddress()
		// .setText(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_ADDRESS.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_ADDRESS.getValue()).toString()
		// : "");
		//
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getComboBoxCity()
		// .setSelectedItem(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_CITY.getValue()) != null
		// ?
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_CITY.getValue())
		// : null);
		//
		// if
		// (tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SEX.getValue()) != null
		// &&
		// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredMaleVal")
		// .equals(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SEX.getValue()).toString()))
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getGroupRadioButtonSex().setSelected(tabbedForm.getPanelWorker().getWorkerDataPanel().getRadioButtonMale().getModel(),
		// true);
		// else if
		// (tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SEX.getValue()) != null
		// &&
		// SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredFemaleVal")
		// .equals(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_SEX.getValue()).toString()))
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getGroupRadioButtonSex().setSelected(tabbedForm.getPanelWorker().getWorkerDataPanel().getRadioButtonFemale().getModel(),
		// true);
		//
		// try {
		// tabbedForm.getPanelWorker().getWorkerDataPanel().getDateChooserBirthDate().setDate(
		// tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_BIRTH_DATE.getValue()) != null ?
		// Constans.APPLICATION_DATE_FORMAT
		// .parse(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
		// Constans.ClientTableColumn.COL_BIRTH_DATE.getValue()).toString()) : null);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		reloadChckbxIsRegistered(
				(SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal"))
						.equals(tabbedForm.getPanelWorker().getWorkerTable().getModel().getValueAt(selectedRow,
								Constans.ClientTableColumn.COL_REGISTERED.getValue())) ? true : false);
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
	}

	// @Override
	// public boolean validateRegistration() {
	// boolean result = false;
	//
	// if
	// (tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldLogin().getText().isEmpty()
	// ||
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getPasswordField().getPassword().length
	// == 0)
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.validateRegistrationError8"),
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.validateRegistrationError8Title"),
	// JOptionPane.ERROR_MESSAGE);
	// else
	// result = true;
	//
	// return result;
	// }

	public boolean validateClientTableSelection(int index) {
		return index > -1 ? true : false;
	}

	public boolean validatePasswordFieldIsEmpty() {
		return tabbedForm.getPanelWorker().getWorkerDataPanel().getPasswordField().getPassword() != null ? false : true;
	}
}

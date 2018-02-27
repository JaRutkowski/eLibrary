package com.javafee.tabbedform;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.helpers.OnlyOnceErrorHandler;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedClientsEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.Lend;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.model.BookTableModel;
import com.javafee.model.ClientTableModel;
import com.javafee.startform.LogInEvent;
import com.javafee.startform.RegistrationEvent;

public final class TabClientEvent implements IActionForm {
	private TabbedForm tabbedForm;

	private static TabClientEvent clientEvent = null;
	private ClientAddModEvent clientAddModEvent;

	private TabClientEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabClientEvent getInstance(TabbedForm tabbedForm) {
		View.forceRefresh(tabbedForm, LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
		if (clientEvent == null) {
			clientEvent = new TabClientEvent(tabbedForm);
		} else
			new RefusedClientsEventLoadingException("Cannot client event loading");
		return clientEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();
		
		tabbedForm.getPanelClient().getCockpitEditionPanel().getBtnAdd().addActionListener(e -> onClickBtnAdd());
		tabbedForm.getPanelClient().getCockpitEditionPanel().getBtnModify().addActionListener(e -> onClickBtnModify());
		tabbedForm.getPanelClient().getCockpitEditionPanel().getBtnDelete().addActionListener(e -> onClickBtnDelete());
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().getDecisionPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept());
		tabbedForm.getPanelClient().getClientTable().getModel().addTableModelListener(e -> reloadClientTable());
		tabbedForm.getPanelClient().getClientTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onClientTableListSelectionChange();
		});
	}

	@Override
	public void initializeForm() {
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
//		switchPerspectiveToAdm(LogInEvent.getRole() == Role.ADMIN || LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
	}

	public void setTabbedForm(TabbedForm tabbedForm) {
		this.tabbedForm = tabbedForm;
	}

	private void reloadChckbxIsRegistered(boolean isRegistered) {
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().getChckbxIsRegistered().setSelected(isRegistered);
	}

	private void reloadClientTable() {
		tabbedForm.getPanelClient().getClientTable().repaint();
	}

	private void onClickBtnAdd() {
		if (clientAddModEvent == null)
			clientAddModEvent = new ClientAddModEvent();
		clientAddModEvent.control(Constans.Context.ADDITION,
				(ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel());
	}

	private void onClickBtnModify() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
						.getClient(selectedRowIndex);
				Client clientShallowClone = (Client) selectedClient.clone();

				Params.getInstance().add("selectedClientShallowClone", clientShallowClone);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (clientAddModEvent == null)
					clientAddModEvent = new ClientAddModEvent();
				clientAddModEvent.control(Constans.Context.MODIFICATION,
						(ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel());

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

		// this.reloadClientTable();
	}

	private void onClickBtnDelete() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());

			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
							.getClient(selectedRowIndex);

					
					@SuppressWarnings("unchecked")
					List<Lend> lends = HibernateUtil.getSession()
							.createQuery("from Lend as len join fetch len.client").list();
					boolean lendClientExist = false;
					for (Lend l : lends) {
						if (l.getClient().getIdUserData() == selectedClient.getIdUserData())
							lendClientExist = true;
					}

					if (lendClientExist) {
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabClientEvent.existingClientLendErrorTitle"),
								SystemProperties.getInstance().getResourceBundle()
										.getString("tabClientEvent.existingClientLendError"));
					} else {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedClient);
						HibernateUtil.commitTransaction();
						((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel()).remove(selectedClient);
					}
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

	// Comment because of new addition and modification mechanism
	// @Override
	// public void onClickBtnRegisterNow() {
	// try {
	// Character sex =
	// tabbedForm.getPanelClient().getClientDataPanel().getGroupRadioButtonSex()
	// .getSelection() != null
	// ?
	// tabbedForm.getPanelClient().getClientDataPanel().getGroupRadioButtonSex().getSelection()
	// .getActionCommand().charAt(0)
	// : null;
	// Date birthDate =
	// tabbedForm.getPanelClient().getClientDataPanel().getDateChooserBirthDate()
	// .getDate() != null
	// ?
	// Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(tabbedForm
	// .getPanelClient().getClientDataPanel().getDateChooserBirthDate().getDate()))
	// : null;
	//
	// RegistrationEvent.forceClearRegistrationEvenet();
	// if (birthDate != null && birthDate.before(new Date())
	// &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldSurname().getText()
	// != null
	// &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldName().getText()
	// != null
	// &&
	// tabbedForm.getPanelWorker().getWorkerDataPanel().getTextFieldPeselNumber().getText()
	// != null) {
	//
	// registrationEvent = RegistrationEvent.getInstance(
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldPeselNumber().getText(),
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldDocumentNumber().getText(),
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldName().getText(),
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldSurname().getText(),
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldAddress().getText(),
	// (City)
	// tabbedForm.getPanelClient().getClientDataPanel().getComboBoxCity().getSelectedItem(),
	// sex, birthDate,
	// tabbedForm.getPanelClient().getClientDataPanel().getTextFieldLogin().getText(),
	// String.valueOf(
	// tabbedForm.getPanelClient().getClientDataPanel().getPasswordField().getPassword()),
	// Role.CLIENT);
	//
	// ClientTableModel ctm = (ClientTableModel)
	// tabbedForm.getPanelClient().getClientTable().getModel();
	// ctm.add((Client) RegistrationEvent.userData);
	// } else {
	// Utils.displayOptionPane(
	// "Podana data urodzenia nie jest wczeœniejszza od be¿¹cej, wype³nione muszê
	// byæ dane nazwiska, imienia, numeru pesel.",
	// "Z³a data", JOptionPane.INFORMATION_MESSAGE);
	// }
	// } catch (RefusedRegistrationException e) {
	// StringBuilder errorBuilder = new StringBuilder();
	//
	// if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError5"));
	// Params.getInstance().remove("ALREADY_REGISTERED");
	// }
	// if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError6"));
	// }
	// if (Params.getInstance().get("WEAK_PASSWORD") != null) {
	// errorBuilder.append(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError7"));
	// }
	//
	// LogGuiException.logError(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
	// errorBuilder.toString(), e);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// if (registrationEvent != null)
	// Utils.displayOptionPane(
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
	// SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2Title"),
	// JOptionPane.INFORMATION_MESSAGE);
	// }

	private void onClickBtnAccept() {
		if (validateClientTableSelection(tabbedForm.getPanelClient().getClientTable().getSelectedRow())) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());
			Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
					.getClient(selectedRowIndex);
			Client clientShallowClone = (Client) selectedClient.clone();

			clientShallowClone.setRegistered(
					tabbedForm.getPanelClient().getAdmIsRegisteredPanel().getChckbxIsRegistered().isSelected());

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().evict(selectedClient);
			HibernateUtil.getSession().update(Client.class.getName(), clientShallowClone);
			HibernateUtil.commitTransaction();

			((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel()).setClient(selectedRowIndex,
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

	private void onClientTableListSelectionChange() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1
				&& tabbedForm.getPanelClient().getClientTable()
						.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow()) != -1)
			reloadChckbxIsRegistered(
					(SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal"))
							.equals(tabbedForm.getPanelClient().getClientTable().getModel().getValueAt(
									tabbedForm.getPanelClient().getClientTable().convertRowIndexToModel(
											tabbedForm.getPanelClient().getClientTable().getSelectedRow()),
									Constans.ClientTableColumn.COL_REGISTERED.getValue())) ? true : false);
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
	}
	
//	private static void reloadPerspective(boolean isAdminOrAccountant) {
//		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
//		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
//	}

	// Comment because of new addition and modification mechanism
	// @Override
	// public boolean validateRegistration() {
	// boolean result = false;
	//
	// if
	// (tabbedForm.getPanelClient().getClientDataPanel().getTextFieldLogin().getText().isEmpty()
	// ||
	// tabbedForm.getPanelClient().getClientDataPanel().getPasswordField().getPassword().length
	// == 0)
	// JOptionPane.showMessageDialog(tabbedForm.getFrame(),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("startForm.validateRegistrationError8"),
	// SystemProperties.getInstance().getResourceBundle()
	// .getString("startForm.validateRegistrationError8Title"),
	// JOptionPane.ERROR_MESSAGE);
	// else
	// result = true;
	//
	// return result;
	// }

	public boolean validateClientTableSelection(int index) {
		return index > -1 ? true : false;
	}

	// Comment because of new addition and modification mechanism
	// public boolean validatePasswordFieldIsEmpty() {
	// return
	// tabbedForm.getPanelClient().getClientDataPanel().getPasswordField().getPassword()
	// != null ? false : true;
	// }

	public void addNow() {
		ClientTableModel ctm = (ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel();
		ctm.add((Client) RegistrationEvent.userData);
	}
	
	private static class View{
		private static void forceRefresh(TabbedForm tabbedForm, Boolean isAdminOrAccountant) {
			tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
			tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
		}
	}
}

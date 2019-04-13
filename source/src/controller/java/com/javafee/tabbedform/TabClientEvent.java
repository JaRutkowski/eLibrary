package com.javafee.tabbedform;

import javax.swing.JOptionPane;

import com.javafee.common.Common;
import com.javafee.common.Constants;
import com.javafee.common.Constants.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.emailform.Actions;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedClientsEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.model.ClientTableModel;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public final class TabClientEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabClientEvent clientEvent = null;
	private ClientAddModEvent clientAddModEvent;

	private com.javafee.emailform.Actions action = null;

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

		tabbedForm.getPanelClient().getMessageAndAlertPanel().getBtnContact()
				.addActionListener(e -> onClickBtnContact());
	}

	private void onClickBtnContact() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
						.getClient(selectedRowIndex);

				Params.getInstance().add("selectedClient", selectedClient);
			}
		}

		if (action == null)
			action = new Actions();

		boolean internetConnectivity = Common.checkInternetConnectivity();
		if (!internetConnectivity) {
			Params.getInstance().add("NO_INTERNET_CONNVECTIVITY", internetConnectivity);
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.noInternetConnectionWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.noInternetConnectionWarning"));
		}

		action.control();
	}

	@Override
	public void initializeForm() {
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
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
		clientAddModEvent.control(Constants.Context.ADDITION,
				(ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel());
	}

	private void onClickBtnModify() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
						.getClient(selectedRowIndex);

				Params.getInstance().add("selectedClient", selectedClient);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (clientAddModEvent == null)
					clientAddModEvent = new ClientAddModEvent();
				clientAddModEvent.control(Constants.Context.MODIFICATION,
						(ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel());
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}
	}

	private void onClickBtnDelete() {
		if (tabbedForm.getPanelClient().getClientTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelClient().getClientTable()
					.convertRowIndexToModel(tabbedForm.getPanelClient().getClientTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Client selectedClient = ((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
						.getClient(selectedRowIndex);
				if (Validator.validateIfClientLendsExists(selectedClient.getIdUserData())) {
					LogGuiException.logWarning(
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabClientEvent.existingClientLendErrorTitle"),
							SystemProperties.getInstance().getResourceBundle()
									.getString("tabClientEvent.existingClientLendError"));
				} else {
					if (Utils.displayConfirmDialog(
							SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
							"") == JOptionPane.YES_OPTION) {
						if (!Validator.validateIfUserCorrespondenceExists(selectedClient.getIdUserData())) {
							HibernateUtil.beginTransaction();
							HibernateUtil.getSession().delete(selectedClient);
							HibernateUtil.commitTransaction();
							((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
									.remove(selectedClient);
						} else {
							if (Utils.displayConfirmDialog(
									SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.clearClientCorrespondenceData"),
									"") == JOptionPane.YES_OPTION) {
								if (Common.clearMessagesRecipientData(selectedClient.getIdUserData()) > 0) {
									HibernateUtil.beginTransaction();
									HibernateUtil.getSession().delete(selectedClient);
									HibernateUtil.commitTransaction();
									((ClientTableModel) tabbedForm.getPanelClient().getClientTable().getModel())
											.remove(selectedClient);
									JOptionPane.showMessageDialog(tabbedForm.getFrame(),
											SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.deleteClientSuccess"),
											SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.deleteClientSuccessTitle"),
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
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
									Constants.ClientTableColumn.COL_REGISTERED.getValue())));
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
		tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
	}

	public boolean validateClientTableSelection(int index) {
		return index > -1;
	}

	private static class View {
		private static void forceRefresh(TabbedForm tabbedForm, Boolean isAdminOrAccountant) {
			tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
			tabbedForm.getPanelClient().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
		}
	}
}

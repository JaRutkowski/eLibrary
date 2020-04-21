package com.javafee.elibrary.core.tabbedform;

import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.IActionForm;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.emailform.Actions;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedClientsEventLoadingException;
import com.javafee.elibrary.core.mail.MailSender;
import com.javafee.elibrary.core.model.ClientTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Client;

import lombok.Setter;

public final class TabClientEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabClientEvent clientEvent = null;
	private ClientAddModEvent clientAddModEvent;

	private com.javafee.elibrary.core.emailform.Actions action = null;

	private TabClientEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabClientEvent getInstance(TabbedForm tabbedForm) {
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

	@Override
	public void initializeForm() {
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
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

		boolean internetConnectivity = Common.checkInternetConnectivity(),
				emailServerConnectivity = checkEmailServerConnectivity();
		if (!internetConnectivity) Params.getInstance().add("NO_INTERNET_CONNECTIVITY", internetConnectivity);
		if (!emailServerConnectivity) Params.getInstance().add("NO_EMAIL_SERVER_CONNECTIVITY", emailServerConnectivity);

		if (!internetConnectivity || !emailServerConnectivity)
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.noConnectionWarningTitle"),
					MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.noConnectionWarning"),
							!internetConnectivity ? SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.noInternetConnectionWarning")
									: SystemProperties.getInstance().getResourceBundle().getString("tabClientEvent.noEmailServerConnectionWarning")));

		action.control();
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

			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.updateAdministratorPrivilegesClientSuccess"),
					SystemProperties.getInstance().getResourceBundle().getString(
							"tabClientEvent.updateClientSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);

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

	private boolean checkEmailServerConnectivity() {
		boolean result = true;
		MailSender mailSender = new MailSender();
		try {
			mailSender.validateConnection();
		} catch (MessagingException e) {
			result = false;
		}
		return result;
	}
}

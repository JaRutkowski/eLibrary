package com.javafee.tabbedform;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.javafee.common.Common;
import com.javafee.common.Constants;
import com.javafee.common.Constants.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedWorkerEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.LibraryWorker;
import com.javafee.hibernate.dto.library.Worker;
import com.javafee.model.WorkerTableModel;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public final class TabWorkerEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabWorkerEvent workerEvent = null;
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
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.ADMIN || LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
	}

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
		if (workerAddModEvent == null)
			workerAddModEvent = new WorkerAddModEvent();
		workerAddModEvent.control(Constants.Context.ADDITION,
				(WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel());

	}

	private void onClickBtnModify() {
		if (tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());

			if (selectedRowIndex != -1) {
				Worker selectedWorker = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
						.getWorker(selectedRowIndex);

				Params.getInstance().add("selectedWorker", selectedWorker);
				Params.getInstance().add("selectedRowIndex", selectedRowIndex);

				if (workerAddModEvent == null)
					workerAddModEvent = new WorkerAddModEvent();
				workerAddModEvent.control(Constants.Context.MODIFICATION,
						(WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel());
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
		if (tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow() != -1) {
			int selectedRowIndex = tabbedForm.getPanelWorker().getWorkerTable()
					.convertRowIndexToModel(tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow());
			if (selectedRowIndex != -1) {
				Worker selectedWorker = ((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
						.getWorker(selectedRowIndex);
				if (Utils.displayConfirmDialog(
						SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
						"") == JOptionPane.YES_OPTION) {
					if (!Validator.validateIfUserCorrespondenceExists(selectedWorker.getIdUserData())) {
						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedWorker);
						HibernateUtil.commitTransaction();
						((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel()).remove(((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
								.getWorker(selectedRowIndex));
					} else {
						if (Utils.displayConfirmDialog(
								SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.clearWorkerCorrespondenceData"),
								"") == JOptionPane.YES_OPTION) {
							if (Common.clearMessagesSenderData(selectedWorker.getIdUserData()) > 0) {
								HibernateUtil.beginTransaction();
								HibernateUtil.getSession().delete(selectedWorker);
								HibernateUtil.commitTransaction();
								((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel()).remove(((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
										.getWorker(selectedRowIndex));
								JOptionPane.showMessageDialog(tabbedForm.getFrame(),
										SystemProperties.getInstance().getResourceBundle().getString("tabWorkerEvent.deleteWorkerSuccess"),
										SystemProperties.getInstance().getResourceBundle().getString("tabWorkerEvent.deleteWorkerSuccessTitle"),
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabWorkerEvent.notSelectedWorkerWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabWorkerEvent.notSelectedWorkerWarning"));
		}
	}

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

			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabWorkerEvent.updateRegistrationStatusWorkerSuccess"),
					SystemProperties.getInstance().getResourceBundle().getString(
							"tabWorkerEvent.updateWorkerSuccessTitle"),
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

				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabWorkerEvent.updateAdministratorPrivilegesWorkerSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabWorkerEvent.updateWorkerSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
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
									Constants.ClientTableColumn.COL_REGISTERED.getValue())));
			LibraryWorker libraryWorker = checkIfHired(
					((WorkerTableModel) tabbedForm.getPanelWorker().getWorkerTable().getModel())
							.getWorker(tabbedForm.getPanelWorker().getWorkerTable().convertRowIndexToModel(
									tabbedForm.getPanelWorker().getWorkerTable().getSelectedRow())));
			reloadChckbxIsAccountant(libraryWorker != null ? libraryWorker.getIsAccountant() : false);
		}
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().setEnabled(isAdminOrAccountant);
		tabbedForm.getPanelWorker().getAdmIsRegisteredPanel().setVisible(isAdminOrAccountant);
	}

	public boolean validateClientTableSelection(int index) {
		return index > -1;
	}

	public boolean validatePasswordFieldIsEmpty() {
		return tabbedForm.getPanelWorker().getWorkerDataPanel().getPasswordField().getPassword() == null;
	}
}

package com.javafee.elibrary.core.emailform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Constants.Tab_Email;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.Query;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.action.IMessageForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.model.DraftTableModel;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.common.message.Message;

import lombok.Setter;

public class TabDraftPageEvent implements IMessageForm {
	@Setter
	private EmailForm emailForm;

	protected static TabDraftPageEvent draftPageEvent = null;

	private TabDraftPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabDraftPageEvent getInstance(EmailForm emailForm) {
		if (draftPageEvent == null)
			draftPageEvent = new TabDraftPageEvent(emailForm);

		return draftPageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();

		emailForm.getPanelDraftPage().addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				onTabDraftOpen();
			}

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});

		emailForm.getPanelDraftPage().getComboBoxRecipient().addActionListener(e -> onChangeComboBoxRecipient());
		emailForm.getPanelDraftPage().getCheckShowOnlySystemCorrespondence()
				.addActionListener(e -> onChangeChckShowOnlySystemCorrespondence());
		emailForm.getPanelDraftPage().getDraftNavigationPanel().getBtnModify()
				.addActionListener(e -> onClickBtnModify());
		emailForm.getPanelDraftPage().getDraftNavigationPanel().getBtnDelete()
				.addActionListener(e -> onClickBtnDelete());
		emailForm.getPanelDraftPage().getDraftNavigationPanel().getBtnSend().addActionListener(e -> onClickBtnSend());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxRecipient();
		reloadDraftTable();
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Role.ADMIN);
		addReloadsMethodsToParams();
	}

	public void onTabDraftOpen() {
		reloadDraftTable();
		reloadComboBoxRecipient();
	}

	@SuppressWarnings("unchecked")
	private void reloadComboBoxRecipient() {
		DefaultComboBoxModel<UserData> comboBoxRecipientModel = new DefaultComboBoxModel<>();
		List<UserData> userDataListToSort;

		userDataListToSort = LogInEvent.getWorker() != null ?
				(List<UserData>) HibernateUtil.getSession().createQuery(Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN.getValue()). //
						setParameter("login", LogInEvent.getWorker().getUserAccount().getLogin()).list()
				: (List<UserData>) HibernateUtil.getSession().createQuery(Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_RECIPIENT_ALL.getValue()).list();

		com.javafee.elibrary.core.common.Common.prepareBlankComboBoxElement(userDataListToSort);
		userDataListToSort.sort(Comparator.nullsFirst(Comparator.comparing(UserData::getSurname)));
		userDataListToSort.forEach(ud -> comboBoxRecipientModel.addElement(ud));
		emailForm.getPanelDraftPage().getComboBoxRecipient().setModel(comboBoxRecipientModel);
	}

	private void reloadDraftTable() {
		if (LogInEvent.getWorker() != null) {
			List<Object> parameters = new ArrayList<>();
			parameters.add(LogInEvent.getWorker().getUserAccount().getLogin());
			((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
					.reloadData(Query.TabOutboxPageEventQuery.DRAFT_MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
		} else {
			((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
					.reloadData();
		}
	}

	private void onClickBtnModify() {
		if (emailForm.getPanelDraftPage().getDraftTable().getSelectedRow() != -1) {
			int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
					.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());
			Message selectedMessage = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
					.getMessage(selectedRowIndex);

			Params.getInstance().add("DRAFT_TO_MODIFY", selectedMessage);

			if (!selectedMessage.getRecipient().stream().filter(recipient -> Optional.ofNullable(recipient).isEmpty()
					|| Optional.ofNullable(recipient.getUserData()).isEmpty()).findAny().isEmpty())
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabOutboxPageEvent.removedRecipientWarningTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabOutboxPageEvent.removedRecipientWarning"));

			emailForm.getTabbedPane().setSelectedIndex(Tab_Email.TAB_CREATE_PAGE.getValue());
		} else
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabOutboxPageEvent.notSelectedMessageWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabOutboxPageEvent.notSelectedMessageWarning"));
	}

	private void onChangeChckShowOnlySystemCorrespondence() {
		if (emailForm.getPanelDraftPage().getCheckShowOnlySystemCorrespondence().isSelected()) {
			List<Object> parameters = new ArrayList<>();
			MessageType messageType = Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE).get();
			parameters.add(messageType);
			((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
					.reloadData(Query.TabOutboxPageEventQuery.DRAFT_MESSAGE_BY_MESSAGE_TYPE.getValue(), parameters);
		} else {
			onChangeComboBoxRecipient();
		}
	}

	private void onClickBtnDelete() {
		if (emailForm.getPanelDraftPage().getDraftTable().getSelectedRow() != -1) {
			int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
					.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());

			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Message selectedMessage = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable()
							.getModel()).getMessage(selectedRowIndex);

					HibernateUtil.beginTransaction();
					HibernateUtil.getSession().delete(selectedMessage);
					HibernateUtil.commitTransaction();

					reloadDraftTable();
					reloadComboBoxRecipient();
				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabOutboxPageEvent.notSelectedMessageWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabOutboxPageEvent.notSelectedMessageWarning"));
		}
	}

	@Override
	public void onClickBtnSend() {
		int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
				.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());
		Message selectedMessage = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
				.getMessage(selectedRowIndex);
		if (selectedMessage.getRecipient().stream().filter(recipient -> Optional.ofNullable(recipient).isEmpty()
				|| Optional.ofNullable(recipient.getUserData()).isEmpty()).findAny().isEmpty()
				&& Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.sendAgainMessage"),
				"") == JOptionPane.YES_OPTION) {
			if (new MailSenderEvent().control(selectedMessage.getRecipient(), selectedMessage.getTitle(),
					selectedMessage.getContent())) {
				updateDraft();
				reloadDraftTable();
			} else
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"));
		} else
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("errorDialog.title"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabOutboxPageEvent.removedRecipientError"));
	}

	private void onChangeComboBoxRecipient() {
		UserData recipientUserData = (UserData) emailForm.getPanelDraftPage().getComboBoxRecipient().getSelectedItem();
		List<Object> parameters = new ArrayList<>();
		if (recipientUserData != null) {
			parameters.add(recipientUserData);
			if (LogInEvent.getWorker() != null) {
				parameters.add(LogInEvent.getWorker().getUserAccount().getLogin());
				((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
						.reloadData(
								Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN
										.getValue(),
								parameters);
			} else {
				((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
						.reloadData(
								Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA.getValue(),
								parameters);
			}
		} else {
			if (LogInEvent.getWorker() != null) {
				parameters.add(LogInEvent.getWorker().getUserAccount().getLogin());
				((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
						.reloadData(Query.TabOutboxPageEventQuery.DRAFT_MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
			} else {
				((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
						.reloadData();
			}
		}
	}

	private void addReloadsMethodsToParams() {
		Map<String, Consumer> emailEventsMethods = com.javafee.elibrary.core.common.Common.getEmailModuleEventsMethodsMapParam();
		emailEventsMethods.put(com.javafee.elibrary.core.common.Common.getMethodReference("reloadDraftTable"), c -> this.reloadDraftTable());
		emailEventsMethods.put(com.javafee.elibrary.core.common.Common.getMethodReference("reloadComboBoxRecipient"), c -> this.reloadComboBoxRecipient());
		Params.getInstance().add("EMAIL_MODULE_EVENTS_METHODS", emailEventsMethods);
	}

	private void updateDraft() {
		int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
				.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());
		Message messageShallowClone = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
				.getMessage(selectedRowIndex);

		messageShallowClone.setIsDraft(false);

		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().evict(((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
				.getMessage(selectedRowIndex));
		HibernateUtil.getSession().update(Message.class.getName(), messageShallowClone);
		HibernateUtil.commitTransaction();

		((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()).setMessage(selectedRowIndex,
				messageShallowClone);

		JOptionPane.showMessageDialog(emailForm.getFrame(),
				SystemProperties.getInstance().getResourceBundle().getString("tabCreatePageEvent.emailSendSuccess"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("tabCreatePageEvent.emailSendSuccessTitle"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		emailForm.getPanelDraftPage().getCheckShowOnlySystemCorrespondence().setVisible(isAdminOrAccountant);
		if (isAdminOrAccountant)
			onChangeChckShowOnlySystemCorrespondence();
	}
}

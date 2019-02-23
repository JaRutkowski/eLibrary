package com.javafee.emailform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Common;
import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.Constans.Tab_Email;
import com.javafee.common.IMessageForm;
import com.javafee.common.Params;
import com.javafee.common.Query;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.MessageType;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.common.message.Message;
import com.javafee.model.DraftTableModel;
import com.javafee.startform.LogInEvent;

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
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT);
	}

	public void onTabDraftOpen() {
		reloadDraftTable();
		reloadComboBoxRecipient();
	}

	private void reloadComboBoxRecipient() {
		DefaultComboBoxModel<UserData> comboBoxRecipientModel = new DefaultComboBoxModel<UserData>();

		@SuppressWarnings("unchecked")
		List<UserData> userDataListToSort = (List<UserData>) HibernateUtil.getSession()
				.createQuery(Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN.getValue()). //
				setParameter("login", LogInEvent.getWorker().getLogin()).list();
		Common.prepareBlankComboBoxElement(userDataListToSort);
		userDataListToSort.sort(Comparator.nullsFirst(Comparator.comparing(UserData::getSurname)));
		userDataListToSort.forEach(ud -> comboBoxRecipientModel.addElement(ud));
		emailForm.getPanelDraftPage().getComboBoxRecipient().setModel(comboBoxRecipientModel);
	}

	private void reloadDraftTable() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(LogInEvent.getWorker().getLogin());
		((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
				.reloadData(Query.TabOutboxPageEventQuery.DRAFT_MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
	}

	private void onClickBtnModify() {
		int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
				.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());
		Message selectedMessage = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
				.getMessage(selectedRowIndex);

		Params.getInstance().add("DRAFT_TO_MODIFY", selectedMessage);
		emailForm.getTabbedPane().setSelectedIndex(Tab_Email.TAB_CREATE_PAGE.getValue());
	}

	private void onChangeChckShowOnlySystemCorrespondence() {
		if (emailForm.getPanelDraftPage().getCheckShowOnlySystemCorrespondence().isSelected()) {
			List<Object> parameters = new ArrayList<Object>();
			MessageType messageType = com.javafee.hibernate.dao.common.Common
					.findMessageTypeByName(Constans.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE).get();
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
		if (Utils.displayConfirmDialog(
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.sendAgainMessage"),
				"") == JOptionPane.YES_OPTION) {
			int selectedRowIndex = emailForm.getPanelDraftPage().getDraftTable()
					.convertRowIndexToModel(emailForm.getPanelDraftPage().getDraftTable().getSelectedRow());
			Message selectedMessage = ((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel())
					.getMessage(selectedRowIndex);

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
		}
	}

	private void onChangeComboBoxRecipient() {
		UserData recipientUserData = (UserData) emailForm.getPanelDraftPage().getComboBoxRecipient().getSelectedItem();
		List<Object> parameters = new ArrayList<Object>();
		if (recipientUserData != null) {
			parameters.add(recipientUserData);
			parameters.add(LogInEvent.getWorker().getLogin());
			((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
					.reloadData(
							Query.TabOutboxPageEventQuery.DISTINCT_DRAFT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN
									.getValue(),
							parameters);
		} else {
			parameters.add(LogInEvent.getWorker().getLogin());
			((DraftTableModel) emailForm.getPanelDraftPage().getDraftTable().getModel()) //
					.reloadData(Query.TabOutboxPageEventQuery.DRAFT_MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
		}
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

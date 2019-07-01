package com.javafee.emailform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Common;
import com.javafee.common.Constants;
import com.javafee.common.Constants.Role;
import com.javafee.common.Constants.Tab_Email;
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
import com.javafee.hibernate.dto.common.message.Recipient;
import com.javafee.model.OutboxTableModel;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class TabOutboxPageEvent implements IMessageForm {
	@Setter
	private EmailForm emailForm;

	protected static TabOutboxPageEvent outboxPageEvent = null;

	protected TabOutboxPageEvent() {
	}

	private TabOutboxPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabOutboxPageEvent getInstance(EmailForm emailForm) {
		if (outboxPageEvent == null)
			outboxPageEvent = new TabOutboxPageEvent(emailForm);

		return outboxPageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();

		emailForm.getPanelOutboxPage().addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				onTabOutboxOpen();
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

		emailForm.getPanelOutboxPage().getComboBoxRecipient().addActionListener(e -> onChangeComboBoxRecipient());
		emailForm.getPanelOutboxPage().getCheckShowOnlySystemCorrespondence()
				.addActionListener(e -> onChangeChckShowOnlySystemCorrespondence());
		emailForm.getPanelOutboxPage().getOutboxNavigationPanel().getBtnPreview()
				.addActionListener(e -> onClickBtnPreview());
		emailForm.getPanelOutboxPage().getOutboxNavigationPanel().getBtnDelete()
				.addActionListener(e -> onClickBtnDelete());
		emailForm.getPanelOutboxPage().getOutboxNavigationPanel().getBtnSendAgain()
				.addActionListener(e -> onClickBtnSend());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxRecipient();
		reloadOutboxTable();
		switchPerspectiveToAdm(LogInEvent.getRole() == Role.WORKER_ACCOUNTANT || LogInEvent.getRole() == Role.ADMIN);
	}

	public void onTabOutboxOpen() {
		reloadOutboxTable();
		reloadComboBoxRecipient();
	}

	@SuppressWarnings("unchecked")
	private void reloadComboBoxRecipient() {
		DefaultComboBoxModel<UserData> comboBoxRecipientModel = new DefaultComboBoxModel<UserData>();

		List<UserData> userDataListToSort = null;

		if (LogInEvent.getWorker() != null)
			userDataListToSort = (List<UserData>) HibernateUtil.getSession()
					.createQuery(Query.TabOutboxPageEventQuery.DISTINCT_MESSAGE_RECIPIENT_BY_SENDER_LOGIN.getValue()). //
					setParameter("login", LogInEvent.getWorker().getLogin()).list();
		else
			userDataListToSort = (List<UserData>) HibernateUtil.getSession()
					.createQuery(Query.TabOutboxPageEventQuery.DISTINCT_MESSAGE_RECIPIENT_ALL.getValue()). //
					list();

		Common.prepareBlankComboBoxElement(userDataListToSort);
		userDataListToSort.sort(Comparator.nullsFirst(Comparator.comparing(UserData::getSurname)));
		userDataListToSort.forEach(ud -> comboBoxRecipientModel.addElement(ud));
		emailForm.getPanelOutboxPage().getComboBoxRecipient().setModel(comboBoxRecipientModel);
	}

	private void reloadOutboxTable() {
		if (LogInEvent.getWorker() != null) {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(LogInEvent.getWorker().getLogin());
			((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
					.reloadData(Query.TabOutboxPageEventQuery.MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
		} else {
			((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
					.reloadData();
		}
	}

	private void onClickBtnPreview() {
		int selectedRowIndex = emailForm.getPanelOutboxPage().getOutboxTable()
				.convertRowIndexToModel(emailForm.getPanelOutboxPage().getOutboxTable().getSelectedRow());
		Message selectedMessage = ((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel())
				.getMessage(selectedRowIndex);

		Params.getInstance().add("MESSAGE_TO_PREVIEW", selectedMessage);
		emailForm.getTabbedPane().setSelectedIndex(Tab_Email.TAB_CREATE_PAGE.getValue());
	}

	private void onChangeChckShowOnlySystemCorrespondence() {
		if (emailForm.getPanelOutboxPage().getCheckShowOnlySystemCorrespondence().isSelected()) {
			List<Object> parameters = new ArrayList<Object>();
			MessageType messageType = com.javafee.hibernate.dao.common.Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE).get();
			parameters.add(messageType);
			((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
					.reloadData(Query.TabOutboxPageEventQuery.MESSAGE_BY_MESSAGE_TYPE.getValue(), parameters);
		} else {
			onChangeComboBoxRecipient();
		}
	}

	private void onClickBtnDelete() {
		if (emailForm.getPanelOutboxPage().getOutboxTable().getSelectedRow() != -1) {
			int selectedRowIndex = emailForm.getPanelOutboxPage().getOutboxTable()
					.convertRowIndexToModel(emailForm.getPanelOutboxPage().getOutboxTable().getSelectedRow());

			if (Utils.displayConfirmDialog(
					SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
					"") == JOptionPane.YES_OPTION) {
				if (selectedRowIndex != -1) {
					Message selectedMessage = ((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable()
							.getModel()).getMessage(selectedRowIndex);

					HibernateUtil.beginTransaction();
					HibernateUtil.getSession().delete(selectedMessage);
					HibernateUtil.commitTransaction();

					reloadOutboxTable();
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
			int selectedRowIndex = emailForm.getPanelOutboxPage().getOutboxTable()
					.convertRowIndexToModel(emailForm.getPanelOutboxPage().getOutboxTable().getSelectedRow());
			Message selectedMessage = ((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel())
					.getMessage(selectedRowIndex);

			if (new MailSenderEvent().control(selectedMessage.getRecipient(), selectedMessage.getTitle(),
					selectedMessage.getContent())) {
				createEmail(selectedMessage.getRecipient(), selectedMessage.getTitle(), selectedMessage.getContent());
				reloadOutboxTable();
			} else
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"));
		}
	}

	private void onChangeComboBoxRecipient() {
		UserData recipientUserData = (UserData) emailForm.getPanelOutboxPage().getComboBoxRecipient().getSelectedItem();
		List<Object> parameters = new ArrayList<Object>();
		if (recipientUserData != null) {
			parameters.add(recipientUserData);
			if (LogInEvent.getWorker() != null) {
				parameters.add(LogInEvent.getWorker().getLogin());
				((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
						.reloadData(
								Query.TabOutboxPageEventQuery.DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA_AND_SENDER_LOGIN
										.getValue(),
								parameters);
			} else {
				((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
						.reloadData(Query.TabOutboxPageEventQuery.DISTINCT_MESSAGE_BY_RECIPIENT_USER_DATA.getValue(),
								parameters);
			}
		} else {
			if (LogInEvent.getWorker() != null) {
				parameters.add(LogInEvent.getWorker().getLogin());
				((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
						.reloadData(Query.TabOutboxPageEventQuery.MESSAGE_BY_SENDER_LOGIN.getValue(), parameters);
			} else
				((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
						.reloadData();
		}
	}

	private void createEmail(Set<Recipient> recipients, String subject, String text) {
		try {
			MessageType messageType = com.javafee.hibernate.dao.common.Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE).get();

			HibernateUtil.beginTransaction();
			com.javafee.hibernate.dto.common.message.Message message = new com.javafee.hibernate.dto.common.message.Message();

			message.setSender(LogInEvent.getWorker());
			message.setMessageType(messageType);
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getUserData());
				newRecipient.setMessage(message);
				if (recipient.getIsCC() != null && recipient.getIsCC()) {
					newRecipient.setIsCC(true);
				}
				if (recipient.getIsBCC() != null && recipient.getIsBCC()) {
					newRecipient.setIsBCC(true);
				}
				message.getRecipient().add(newRecipient);
			});
			message.setTitle(subject);
			message.setContent(text);
			message.setSendDate(
					Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT.format(new Date())));

			HibernateUtil.getSession().save(message);
			HibernateUtil.commitTransaction();

			JOptionPane.showMessageDialog(emailForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("tabCreatePageEvent.emailSendSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabCreatePageEvent.emailSendSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void switchPerspectiveToAdm(boolean isAdminOrAccountant) {
		emailForm.getPanelOutboxPage().getCheckShowOnlySystemCorrespondence().setVisible(isAdminOrAccountant);
		if (isAdminOrAccountant)
			onChangeChckShowOnlySystemCorrespondence();
	}

}

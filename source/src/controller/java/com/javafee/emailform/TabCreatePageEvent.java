package com.javafee.emailform;

import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedTabCreatePageEventLoadingException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.common.message.Recipient;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class TabCreatePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabCreatePageEvent createPageEvent = null;

	private TabCreatePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabCreatePageEvent getInstance(EmailForm emailForm) {
		if (createPageEvent == null) {
			createPageEvent = new TabCreatePageEvent(emailForm);
		} else
			new RefusedTabCreatePageEventLoadingException("Cannot tab create page loading");
		return createPageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		setVisibleControls();
		initializeForm();

		emailForm.getPanelComposePage().getChckbxCC().addActionListener(e -> onChangeChckbxCC());
		emailForm.getPanelComposePage().getChckbxBCC().addActionListener(e -> onChangeChckbxBCC());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSend()
				.addActionListener(e -> onClickBtnSend());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxes();
	}

	private void reloadComboBoxes() {
		DefaultComboBoxModel<Client> comboBoxTo = new DefaultComboBoxModel<Client>();
		DefaultComboBoxModel<Client> comboBoxCC = new DefaultComboBoxModel<Client>();
		DefaultComboBoxModel<Client> comboBoxBCC = new DefaultComboBoxModel<Client>();

		HibernateDao<Client, Integer> client = new HibernateDao<Client, Integer>(Client.class);
		List<Client> clientListToSort = client.findAll().stream(). //
				filter(c -> c.getEMail() != null && !"".equals(c.getEMail())). //
				collect(Collectors.toList());
		clientListToSort
				.sort(Comparator.comparing(Client::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));

		clientListToSort.forEach(c -> comboBoxTo.addElement(c));
		clientListToSort.forEach(c -> comboBoxCC.addElement(c));
		clientListToSort.forEach(c -> comboBoxBCC.addElement(c));

		emailForm.getPanelComposePage().getComboBoxTo().setModel(comboBoxTo);
		emailForm.getPanelComposePage().getComboBoxCC().setModel(comboBoxCC);
		emailForm.getPanelComposePage().getComboBoxBCC().setModel(comboBoxBCC);
	}

	private void onClickBtnSend() {
		if (validateSend()) {
			List<SimpleEntry<Message.RecipientType, UserData>> recipients = new ArrayList<SimpleEntry<Message.RecipientType, UserData>>();
			recipients.addAll(getRecipientTo(Message.RecipientType.TO));
			if (emailForm.getPanelComposePage().getChckbxCC().isSelected())
				recipients.addAll(getRecipientTo(Message.RecipientType.CC));
			if (emailForm.getPanelComposePage().getChckbxBCC().isSelected())
				recipients.addAll(getRecipientTo(Message.RecipientType.BCC));
			String subject = emailForm.getPanelComposePage().getTextFieldTopic().getText();
			String text = emailForm.getPanelComposePage().getTextAreaContent().getText();

			if (new MailSenderEvent().control(recipients, subject, text))
				createEmail(recipients, subject, text);
			else
				LogGuiException.logWarning(
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendErrorTitle"));
		}
	}

	private void onChangeChckbxCC() {
		setVisibleCCControls(!emailForm.getPanelComposePage().getComboBoxCC().isVisible());
	}

	private void onChangeChckbxBCC() {
		setVisibleBCCControls(!emailForm.getPanelComposePage().getComboBoxBCC().isVisible());
	}

	private void setVisibleControls() {
		setVisibleCCControls(false);
		setVisibleBCCControls(false);
	}

	private void setVisibleCCControls(boolean visible) {
		emailForm.getPanelComposePage().getLblCC().setVisible(visible);
		emailForm.getPanelComposePage().getComboBoxCC().setVisible(visible);
	}

	private void setVisibleBCCControls(boolean visible) {
		emailForm.getPanelComposePage().getLblBCC().setVisible(visible);
		emailForm.getPanelComposePage().getComboBoxBCC().setVisible(visible);
	}

	private void createEmail(List<SimpleEntry<Message.RecipientType, UserData>> recipients, String subject,
			String text) {
		try {
			HibernateUtil.beginTransaction();
			com.javafee.hibernate.dto.common.message.Message message = new com.javafee.hibernate.dto.common.message.Message();

			message.setSender(LogInEvent.getWorker());
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getValue());
				newRecipient.setMessage(message);
				if (Message.RecipientType.CC.equals(recipient.getKey())) {
					newRecipient.setIsCC(true);
				}
				if (Message.RecipientType.BCC.equals(recipient.getKey())) {
					newRecipient.setIsBCC(true);
				}
				message.getRecipient().add(newRecipient);
			});
			message.setTitle(subject);
			message.setContent(text);

			message.setSendDate(
					Constans.APPLICATION_DATE_FORMAT.parse(Constans.APPLICATION_DATE_FORMAT.format(new Date())));
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

	private List<SimpleEntry<Message.RecipientType, UserData>> getRecipientTo(Message.RecipientType recipientType) {
		List<SimpleEntry<Message.RecipientType, UserData>> result = new ArrayList<SimpleEntry<Message.RecipientType, UserData>>();
		if (Message.RecipientType.TO.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxTo().getSelectedItem();
			result.add(new SimpleEntry<Message.RecipientType, UserData>(Message.RecipientType.TO, selectedUser));
		}
		if (Message.RecipientType.CC.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxCC().getSelectedItem();
			result.add(new SimpleEntry<Message.RecipientType, UserData>(Message.RecipientType.CC, selectedUser));
		}
		if (Message.RecipientType.BCC.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxBCC().getSelectedItem();
			result.add(new SimpleEntry<Message.RecipientType, UserData>(Message.RecipientType.BCC, selectedUser));
		}
		return result;
	}

	private boolean validateSend() {
		boolean result = true;// false;
		// if (startForm.getLogInPanel().getTextFieldLogin().getText().isEmpty()
		// || startForm.getLogInPanel().getPasswordField().getPassword().length == 0)
		// JOptionPane.showMessageDialog(startForm.getFrame(),
		// SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1"),
		// SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1Title"),
		// JOptionPane.ERROR_MESSAGE);
		// else
		// result = true;

		return result;
	}
}

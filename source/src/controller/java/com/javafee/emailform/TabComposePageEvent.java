package com.javafee.emailform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.common.message.Recipient;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class TabComposePageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	protected static TabComposePageEvent composePageEvent = null;

	private TabComposePageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}

	public static TabComposePageEvent getInstance(EmailForm emailForm) {
		if (composePageEvent == null)
			composePageEvent = new TabComposePageEvent(emailForm);

		return composePageEvent;
	}

	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		setVisibleControls();
		initializeForm();

		emailForm.getPanelComposePage().addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				onTabComposeOpen();
			}

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				onTabComposeClose();
			}
		});

		emailForm.getPanelComposePage().getChckbxCC().addActionListener(e -> onChangeChckbxCC());
		emailForm.getPanelComposePage().getChckbxBCC().addActionListener(e -> onChangeChckbxBCC());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSend()
				.addActionListener(e -> onClickBtnSend());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnClear()
				.addActionListener(e -> onClickBtnClear());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxes();
	}

	private void onTabComposeOpen() {
		if (Params.getInstance().get("MESSAGE_TO_PREVIEW") != null) {
			com.javafee.hibernate.dto.common.message.Message messageToPreview = ((com.javafee.hibernate.dto.common.message.Message) //
			Params.getInstance().get("MESSAGE_TO_PREVIEW"));
			fillComposePagePanel(messageToPreview);
			Params.getInstance().remove("MESSAGE_TO_PREVIEW");
		}
	}

	private void onTabComposeClose() {
		resetComponents();
	}

	private void fillComposePagePanel(com.javafee.hibernate.dto.common.message.Message messageToPreview) {
		emailForm.getPanelComposePage().getTextFieldTopic().setText(messageToPreview.getTitle());
		emailForm.getPanelComposePage().getTextAreaContent().setText(messageToPreview.getContent());

		messageToPreview.getRecipient().forEach(recipient -> {
			Client client = null;
			if (recipient.getIsCC() != null && recipient.getIsCC()) {
				emailForm.getPanelComposePage().getLblCC().setVisible(true);
				emailForm.getPanelComposePage().getChckbxCC().setSelected(true);
				emailForm.getPanelComposePage().getComboBoxCC().setVisible(true);
				client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
						.setParameter("login", recipient.getUserData().getLogin()).uniqueResult();
				emailForm.getPanelComposePage().getComboBoxCC().setSelectedItem(client);
			} else if (recipient.getIsBCC() != null && recipient.getIsBCC()) {
				emailForm.getPanelComposePage().getLblBCC().setVisible(true);
				emailForm.getPanelComposePage().getChckbxBCC().setSelected(true);
				emailForm.getPanelComposePage().getComboBoxBCC().setVisible(true);
				client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
						.setParameter("login", recipient.getUserData().getLogin()).uniqueResult();
				emailForm.getPanelComposePage().getComboBoxBCC().setSelectedItem(client);
			} else {
				client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
						.setParameter("login", recipient.getUserData().getLogin()).uniqueResult();
				emailForm.getPanelComposePage().getComboBoxTo().setSelectedItem(client);
			}
		});
	}

	private void reloadComboBoxes() {
		DefaultComboBoxModel<Client> comboBoxToModel = new DefaultComboBoxModel<Client>();
		DefaultComboBoxModel<Client> comboBoxCCModel = new DefaultComboBoxModel<Client>();
		DefaultComboBoxModel<Client> comboBoxBCCModel = new DefaultComboBoxModel<Client>();

		HibernateDao<Client, Integer> client = new HibernateDao<Client, Integer>(Client.class);
		List<Client> clientListToSort = client.findAll().stream(). //
				filter(c -> c.getEMail() != null && !"".equals(c.getEMail())). //
				collect(Collectors.toList());
		clientListToSort
				.sort(Comparator.comparing(Client::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));

		clientListToSort.forEach(c -> comboBoxToModel.addElement(c));
		clientListToSort.forEach(c -> comboBoxCCModel.addElement(c));
		clientListToSort.forEach(c -> comboBoxBCCModel.addElement(c));

		emailForm.getPanelComposePage().getComboBoxTo().setModel(comboBoxToModel);
		emailForm.getPanelComposePage().getComboBoxCC().setModel(comboBoxCCModel);
		emailForm.getPanelComposePage().getComboBoxBCC().setModel(comboBoxBCCModel);

		if (Params.getInstance().get("selectedClient") != null && //
				((Client) Params.getInstance().get("selectedClient")).getEMail() != null)
			emailForm.getPanelComposePage().getComboBoxTo()
					.setSelectedItem((Client) Params.getInstance().get("selectedClient"));
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

	private void onClickBtnClear() {
		resetComponents();
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

	private void resetComponents() {
		reloadComboBoxes();
		setVisibleControls();
		clearComponentsContent();
	}

	private void clearComponentsContent() {
		emailForm.getPanelComposePage().getChckbxCC().setSelected(false);
		emailForm.getPanelComposePage().getChckbxBCC().setSelected(false);
		emailForm.getPanelComposePage().getTextFieldTopic().setText(null);
		emailForm.getPanelComposePage().getTextAreaContent().setText(null);
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

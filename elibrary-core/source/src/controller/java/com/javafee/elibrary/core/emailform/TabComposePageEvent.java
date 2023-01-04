package com.javafee.elibrary.core.emailform;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.common.message.Message;
import com.javafee.elibrary.hibernate.dto.common.message.Recipient;
import com.javafee.elibrary.hibernate.dto.library.Client;

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
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSaveAsDraft()
				.addActionListener(e -> onClickBtnSaveAsDraft());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnAddAttachment()
				.addActionListener(e -> onClickBtnAddAttachment());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnSend()
				.addActionListener(e -> onClickBtnSend());
		emailForm.getPanelComposePage().getComposeNavigationEmailPanel().getBtnClear()
				.addActionListener(e -> onClickBtnClear());
		emailForm.getPanelComposePage().getRootPane().setDefaultButton(emailForm.getPanelComposePage()
				.getComposeNavigationEmailPanel().getBtnSend());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxes();
		addReloadComboBoxesMethodToParams();
	}

	private void onTabComposeOpen() {
		if (Params.getInstance().contains("MESSAGE_TO_PREVIEW")) {
			Message messageToPreview = ((Message) //
					Params.getInstance().get("MESSAGE_TO_PREVIEW"));
			fillComposePagePanel(messageToPreview);
			Params.getInstance().remove("MESSAGE_TO_PREVIEW");
		}
		if (Params.getInstance().contains("DRAFT_TO_MODIFY")) {
			Message messageToPreview = ((Message) //
					Params.getInstance().get("DRAFT_TO_MODIFY"));
			fillComposePagePanel(messageToPreview);
		}
	}

	private void onTabComposeClose() {
		resetComponents();
		Params.getInstance().remove("DRAFT_TO_MODIFY");
	}

	private void fillComposePagePanel(Message messageToPreview) {
		emailForm.getPanelComposePage().getTextFieldTopic().setText(messageToPreview.getTitle());
		emailForm.getPanelComposePage().getEditorPaneContent().setText(messageToPreview.getContent());

		messageToPreview.getRecipient().forEach(recipient -> {
			if (Optional.ofNullable(recipient.getUserData()).isPresent()) {
				Client client;
				if (recipient.getIsCC() != null && recipient.getIsCC()) {
					emailForm.getPanelComposePage().getLblCC().setVisible(true);
					emailForm.getPanelComposePage().getChckbxCC().setSelected(true);
					emailForm.getPanelComposePage().getComboBoxCC().setVisible(true);
					client = (Client) ((Object[]) HibernateUtil.getSession().getNamedQuery("Client.checkIfUserDataLoginExist")
							.setParameter("login", recipient.getUserData().getUserAccount().getLogin()).uniqueResult())[0];
					emailForm.getPanelComposePage().getComboBoxCC().setSelectedItem(client);
				} else if (recipient.getIsBCC() != null && recipient.getIsBCC()) {
					emailForm.getPanelComposePage().getLblBCC().setVisible(true);
					emailForm.getPanelComposePage().getChckbxBCC().setSelected(true);
					emailForm.getPanelComposePage().getComboBoxBCC().setVisible(true);
					client = (Client) ((Object[]) HibernateUtil.getSession().getNamedQuery("Client.checkIfUserDataLoginExist")
							.setParameter("login", recipient.getUserData().getUserAccount().getLogin()).uniqueResult())[0];
					emailForm.getPanelComposePage().getComboBoxBCC().setSelectedItem(client);
				} else {
					client = (Client) ((Object[]) HibernateUtil.getSession().getNamedQuery("Client.checkIfUserDataLoginExist")
							.setParameter("login", recipient.getUserData().getUserAccount().getLogin()).uniqueResult())[0];
					emailForm.getPanelComposePage().getComboBoxTo().setSelectedItem(client);
				}
			}
		});
	}

	private void reloadComboBoxes() {
		DefaultComboBoxModel<Client> comboBoxToModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<Client> comboBoxCCModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<Client> comboBoxBCCModel = new DefaultComboBoxModel<>();

		HibernateDao<Client, Integer> client = new HibernateDao<>(Client.class);
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
					.setSelectedItem(Params.getInstance().get("selectedClient"));
	}

	private void onClickBtnAddAttachment() {

	}

	private void onClickBtnSaveAsDraft() {
		List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients = new ArrayList<SimpleEntry<javax.mail.Message.RecipientType, UserData>>();
		recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.TO));
		if (emailForm.getPanelComposePage().getChckbxCC().isSelected())
			recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.CC));
		if (emailForm.getPanelComposePage().getChckbxBCC().isSelected())
			recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.BCC));
		String subject = emailForm.getPanelComposePage().getTextFieldTopic().getText();
		String text = emailForm.getPanelComposePage().getEditorPaneContent().getText();

		if (!Params.getInstance().contains("DRAFT_TO_MODIFY"))
			createDraft(recipients, subject, text);
		else
			updateDraft(recipients, subject, text, false);
	}

	private void onClickBtnSend() {
		if (validateSend()) {
			List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients = new ArrayList<SimpleEntry<javax.mail.Message.RecipientType, UserData>>();
			recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.TO));
			if (emailForm.getPanelComposePage().getChckbxCC().isSelected())
				recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.CC));
			if (emailForm.getPanelComposePage().getChckbxBCC().isSelected())
				recipients.addAll(getRecipientTo(javax.mail.Message.RecipientType.BCC));
			String subject = emailForm.getPanelComposePage().getTextFieldTopic().getText();
			String text = emailForm.getPanelComposePage().getEditorPaneContent().getText();

			if (new MailSenderEvent().control(recipients, subject, text)) {
				if (!Params.getInstance().contains("DRAFT_TO_MODIFY"))
					createEmail(recipients, subject, text);
				else
					updateDraft(recipients, subject, text, true);
			} else
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

	private void addReloadComboBoxesMethodToParams() {
		Map<String, Consumer> emailEventsMethods = com.javafee.elibrary.core.common.Common.getEmailModuleEventsMethodsMapParam();
		emailEventsMethods.put(com.javafee.elibrary.core.common.Common.getMethodReference("reloadComboBoxes"), c -> this.reloadComboBoxes());
		Params.getInstance().add("EMAIL_MODULE_EVENTS_METHODS", emailEventsMethods);
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
		emailForm.getPanelComposePage().getEditorPaneContent().setText(null);
	}

	private void createEmail(List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients, String subject,
	                         String text) {
		try {
			MessageType messageType = Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE).get();

			HibernateUtil.beginTransaction();
			Message message = new Message();

			message.setSender(LogInEvent.getWorker());
			message.setMessageType(messageType);
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getValue());
				newRecipient.setMessage(message);
				if (javax.mail.Message.RecipientType.CC.equals(recipient.getKey())) {
					newRecipient.setIsCC(true);
				}
				if (javax.mail.Message.RecipientType.BCC.equals(recipient.getKey())) {
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

	private void updateDraft(List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients, String subject, String text,
	                         boolean isSendContext) {
		try {
			Message messageShallowClone = (Message) Params
					.getInstance().get("DRAFT_TO_MODIFY");

			messageShallowClone.getRecipient().clear();

			messageShallowClone.setIsDraft(!isSendContext);
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getValue());
				newRecipient.setMessage(messageShallowClone);
				if (javax.mail.Message.RecipientType.CC.equals(recipient.getKey())) {
					newRecipient.setIsCC(true);
				}
				if (javax.mail.Message.RecipientType.BCC.equals(recipient.getKey())) {
					newRecipient.setIsBCC(true);
				}
				messageShallowClone.getRecipient().add(newRecipient);
			});

			messageShallowClone.setTitle(subject);
			messageShallowClone.setContent(text);
			messageShallowClone.setSendDate(
					Constants.APPLICATION_DATE_FORMAT.parse(Constants.APPLICATION_DATE_FORMAT.format(new Date())));

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().update(Message.class.getName(), messageShallowClone);
			HibernateUtil.commitTransaction();

			if (isSendContext) {
				JOptionPane.showMessageDialog(emailForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.emailSendSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabCreatePageEvent.emailSendSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(emailForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle()
								.getString("tabCreatePageEvent.draftUpdateSuccess"),
						SystemProperties.getInstance().getResourceBundle().getString(
								"tabCreatePageEvent.draftUpdateSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void createDraft(List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> recipients, String subject,
	                         String text) {
		try {
			MessageType messageType = Common
					.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE).get();

			HibernateUtil.beginTransaction();
			Message message = new Message();

			message.setSender(LogInEvent.getWorker());
			message.setMessageType(messageType);
			message.setIsDraft(true);
			recipients.forEach(recipient -> {
				Recipient newRecipient = new Recipient();
				newRecipient.setUserData(recipient.getValue());
				newRecipient.setMessage(message);
				if (javax.mail.Message.RecipientType.CC.equals(recipient.getKey())) {
					newRecipient.setIsCC(true);
				}
				if (javax.mail.Message.RecipientType.BCC.equals(recipient.getKey())) {
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
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabCreatePageEvent.draftCreateSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabCreatePageEvent.draftCreateSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> getRecipientTo(javax.mail.Message.RecipientType recipientType) {
		List<SimpleEntry<javax.mail.Message.RecipientType, UserData>> result = new ArrayList<>();
		if (javax.mail.Message.RecipientType.TO.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxTo().getSelectedItem();
			result.add(new SimpleEntry<>(javax.mail.Message.RecipientType.TO, selectedUser));
		}
		if (javax.mail.Message.RecipientType.CC.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxCC().getSelectedItem();
			result.add(new SimpleEntry<>(javax.mail.Message.RecipientType.CC, selectedUser));
		}
		if (javax.mail.Message.RecipientType.BCC.equals(recipientType)) {
			UserData selectedUser = (UserData) emailForm.getPanelComposePage().getComboBoxBCC().getSelectedItem();
			result.add(new SimpleEntry<>(javax.mail.Message.RecipientType.BCC, selectedUser));
		}
		return result;
	}

	private boolean validateSend() {
		// TODO Recipients check

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

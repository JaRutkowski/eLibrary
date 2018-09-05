package com.javafee.emailform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.javafee.common.IActionForm;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.model.OutboxTableModel;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class TabOutboxPageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	protected static TabOutboxPageEvent outboxPageEvent = null;

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

		emailForm.getPanelOutboxPage().getComboBoxRecipient().addActionListener(e -> onChangeComboBoxRecipient());
	}

	@Override
	public void initializeForm() {
		reloadComboBoxRecipient();
	}

	private void reloadComboBoxRecipient() {
		// Admin (centralny) KZB (brak możliwśoci zmiany), KZI, KP, WB, WI
		// --Admin (lokalny) KZB, KZI, KP, WB, WI
		// Accountant KZB, KP, WB
		// Worker KZB, KP, WB

		// KZB Komunikaty zmian użytkownik bieżący
		// KZI Komunikaty zmian inni
		// KP Komunikaty pozostały
		// WB Wiadomości użytkownik bieżący
		// WI Wiadomości użytkownik inni

		DefaultComboBoxModel<UserData> comboBoxRecipientModel = new DefaultComboBoxModel<UserData>();

		// this.volumes = HibernateUtil.getSession().createQuery("from Volume as vol
		// join fetch vol.book").list();
		// this.volumes = volumes.stream().filter(vol ->
		// !vol.getIsLended()).collect(Collectors.toList());
		// this.volumes = volumes.stream().filter(vol ->
		// !vol.getIsReadingRoom()).collect(Collectors.toList());

		switch (LogInEvent.getRole()) {
		case ADMIN:

			break;

		case WORKER_ACCOUNTANT:
			@SuppressWarnings("unchecked")
			List<UserData> userDataListToSort = (List<UserData>) HibernateUtil.getSession().createQuery(
					"select distinct rec.userData from Recipient rec where rec.message.sender.login = :login"). //
					setParameter("login", LogInEvent.getWorker().getLogin()).list();
			userDataListToSort
					.sort(Comparator.comparing(UserData::getSurname, Comparator.nullsFirst(Comparator.naturalOrder())));
			userDataListToSort.forEach(ud -> comboBoxRecipientModel.addElement(ud));

			emailForm.getPanelOutboxPage().getComboBoxRecipient().setModel(comboBoxRecipientModel);
			break;
		case WORKER_LIBRARIAN:

			break;

		default:
			break;
		}
	}

	private void onChangeComboBoxRecipient() {
		UserData recipientUserData = (UserData) emailForm.getPanelOutboxPage().getComboBoxRecipient().getSelectedItem();
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(recipientUserData);
		((OutboxTableModel) emailForm.getPanelOutboxPage().getOutboxTable().getModel()) //
				.reloadData("from Message mes join fetch mes.recipient r where r.userData = ?", parameters);
	}
}

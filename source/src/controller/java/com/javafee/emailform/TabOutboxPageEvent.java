package com.javafee.emailform;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;

import com.javafee.common.IActionForm;
import com.javafee.common.Constans.Role;
import com.javafee.exception.RefusedTabSendedPageEventLoadingException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.startform.LogInEvent;

import lombok.Setter;

public class TabOutboxPageEvent implements IActionForm {
	@Setter
	private EmailForm emailForm;

	private static TabOutboxPageEvent workingCopyPageEvent = null;

	private TabOutboxPageEvent(EmailForm emailForm) {
		this.control(emailForm);
	}
	
	public static TabOutboxPageEvent getInstance(EmailForm emailForm) {
		if (workingCopyPageEvent == null) {
			workingCopyPageEvent = new TabOutboxPageEvent(emailForm);
		} else
			new RefusedTabSendedPageEventLoadingException("Cannot tab sended page event loading");
		return workingCopyPageEvent;
	}
	
	public void control(EmailForm emailForm) {
		setEmailForm(emailForm);
		initializeForm();
		
	}

	@Override
	public void initializeForm() {
		reloadComboBoxRecipient();
	}
	
	private void reloadComboBoxRecipient() {
		//Admin (centralny)					 					KZB (brak możliwśoci zmiany), KZI, KP, WB, WI
		//--Admin (lokalny)										KZB, KZI, KP, WB, WI
		//Accountant											KZB, KP, WB
		//Worker												KZB, KP, WB
		
		//KZB 	Komunikaty zmian użytkownik bieżący
		//KZI 	Komunikaty zmian inni
		//KP 	Komunikaty pozostały
		//WB 	Wiadomości użytkownik bieżący
		//WI 	Wiadomości użytkownik inni
		
		//DefaultComboBoxModel<Client> comboBoxRecipient = new DefaultComboBoxModel<Client>();
		
		//this.volumes = HibernateUtil.getSession().createQuery("from Volume as vol join fetch vol.book").list();
		//this.volumes = volumes.stream().filter(vol -> !vol.getIsLended()).collect(Collectors.toList());
		//this.volumes = volumes.stream().filter(vol -> !vol.getIsReadingRoom()).collect(Collectors.toList());
		
		switch (LogInEvent.getRole()) {
		case ADMIN:
			
			break;

		case WORKER_ACCOUNTANT:

			break;
		case WORKER_LIBRARIAN:
			
			break;
			
		default:
			break;
		}
	}
}

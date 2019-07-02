package com.javafee.common;






import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.common.message.Message;
import com.javafee.hibernate.dto.common.message.Recipient;
import com.javafee.hibernate.dto.library.*;

import java.util.List;

public final class Validator {
	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Client client) {
		Client existingPeselClient = null;
		boolean result;

		Client existingLoginClient = (Client) HibernateUtil.getSession()
				.getNamedQuery("Client.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult();
		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for (UserData u : ud) {
			if (u.getLogin().equals(client.getLogin()) && u.getIdUserData() != client.getIdUserData())
				return false;
		}

		if (existingLoginClient == null) {
			if (!"".equals(client.getPeselNumber())) {
				existingPeselClient = (Client) HibernateUtil.getSession()
						.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
						.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
						.uniqueResult();
				result = client.getPeselNumber() == null || (existingLoginClient == null && existingPeselClient == null);
			} else
				result = existingLoginClient == null;
		} else {
			result = false;
		}

		return result;
	}

	public static boolean validateClientPesel(String pesel) {
		boolean result = true;
		if (!"".equals(pesel)) {
			Client existingPeselClient = (Client) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselClient == null;
		}
		return result;
	}

	public static boolean validateWorkerPesel(String pesel) {
		boolean result = true;
		if (!"".equals(pesel)) {
			Worker existingPeselClient = (Worker) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselClient == null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Worker client) {
		Worker existingPeselClient = null;
		boolean result;

		Worker existingLoginClient = (Worker) HibernateUtil.getSession()
				.getNamedQuery("Worker.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult();

		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for (UserData u : ud) {
			if (u.getLogin().equals(client.getLogin()) && u.getIdUserData() != client.getIdUserData())
				return false;
		}

		if (existingLoginClient == null) {
			if (!"".equals(client.getPeselNumber())) {
				existingPeselClient = (Worker) HibernateUtil.getSession()
						.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
						.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
						.uniqueResult();
				result = client.getPeselNumber() == null || (existingLoginClient == null && existingPeselClient == null);
			} else
				result = existingLoginClient == null;
		} else {
			result = false;
		}

		return result;
	}

	public static boolean validateBookFilter(Author author, Category category, PublishingHouse publishingHouse) {
		return author != null || category != null || publishingHouse != null;
	}

	public static boolean validateInventoryNumberExist(String inventoryNumber) {
		Volume existingInventoryNumberVolume = (Volume) HibernateUtil.getSession()
				.getNamedQuery("Volume.checkIfInventoryNumberExist").setParameter("inventoryNumber", inventoryNumber)
				.uniqueResult();
		return existingInventoryNumberVolume != null;
	}

	public static boolean validateIsbnNumberExist(Integer idBook, String isbnNumber) {
		Book existingIsbnNumber = null;
		if (idBook != null)
			existingIsbnNumber = (Book) HibernateUtil.getSession()
					.getNamedQuery("Book.checkWithComparingIdIfIsbnNumberExist").setParameter("idBook", idBook)
					.setParameter("isbnNumber", isbnNumber).uniqueResult();
		else
			existingIsbnNumber = (Book) HibernateUtil.getSession().getNamedQuery("Book.checkIfIsbnNumberExist")
					.setParameter("isbnNumber", isbnNumber).uniqueResult();
		return existingIsbnNumber != null;
	}

	public static boolean validateIfClientLendsExists(Integer idUserData) {
		List<Lend> lends = HibernateUtil.getSession().createQuery("from Lend as len join fetch len.client")
				.list();

		boolean lendClientExist = false;
		for (Lend l : lends)
			if (l.getClient().getIdUserData() == idUserData)
				lendClientExist = true;
		return lendClientExist;
	}

	public static boolean validateIfUserCorrespondenceExists(Integer idUserData) {
		List<Recipient> recipients = HibernateUtil.getSession().createQuery("from Recipient as rec where userData.idUserData = ?0")
				.setParameter(0, idUserData).list();
		List<Message> sender = HibernateUtil.getSession().createQuery("from Message as mes where sender.idUserData = ?0")
				.setParameter(0, idUserData).list();
		return !recipients.isEmpty() || !sender.isEmpty();
	}
}

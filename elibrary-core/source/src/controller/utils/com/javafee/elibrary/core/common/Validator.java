package com.javafee.elibrary.core.common;


import java.util.List;

import com.google.common.base.Strings;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.common.message.Message;
import com.javafee.elibrary.hibernate.dto.common.message.Recipient;
import com.javafee.elibrary.hibernate.dto.library.Author;
import com.javafee.elibrary.hibernate.dto.library.Book;
import com.javafee.elibrary.hibernate.dto.library.Category;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.Lend;
import com.javafee.elibrary.hibernate.dto.library.PublishingHouse;
import com.javafee.elibrary.hibernate.dto.library.Volume;
import com.javafee.elibrary.hibernate.dto.library.Worker;

public final class Validator {
	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Client client) {
		UserData existingPeselClient = null;
		boolean result;

		Client existingLoginClient = HibernateUtil.getSession()
				.getNamedQuery("Client.checkWithComparingIdIfUserDataLoginExist").setParameter("login", client.getUserAccount().getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult() != null ?
				(Client) ((Object[]) HibernateUtil.getSession()
						.getNamedQuery("Client.checkWithComparingIdIfUserDataLoginExist").setParameter("login", client.getUserAccount().getLogin())
						.setParameter("id", client.getIdUserData()).uniqueResult())[0] : null;
		//		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		//		for (var u : ud) {
		//			if (u.getUserAccount().getLogin().equals(client.getUserAccount().getLogin()) && u.getIdUserData() != client.getIdUserData())
		//				return false;
		//		}

		if (existingLoginClient == null) {
			if (!"".equals(client.getPeselNumber())) {
				existingPeselClient = client != null && HibernateUtil.getSession()
						.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
						.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
						.uniqueResult() != null ? (UserData) HibernateUtil.getSession()
						.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
						.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
						.uniqueResult() : null;
				result = client.getPeselNumber() == null || (existingLoginClient == null && existingPeselClient == null);
			} else
				result = existingLoginClient == null;
		} else {
			result = false;
		}

		return result;
	}

	public static boolean validateUserDataPesel(String pesel) {
		boolean result = true;
		if (!Strings.isNullOrEmpty(pesel)) {
			UserData existingPeselUserData = (UserData) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselUserData == null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Worker client) {
		Worker existingPeselClient = null;
		boolean result;

		Worker existingLoginClient = HibernateUtil.getSession()
				.getNamedQuery("Worker.checkWithComparingIdIfUserDataLoginExist").setParameter("login", client.getUserAccount().getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult() != null ?
				(Worker) ((Object[]) HibernateUtil.getSession()
						.getNamedQuery("Worker.checkWithComparingIdIfUserDataLoginExist").setParameter("login", client.getUserAccount().getLogin())
						.setParameter("id", client.getIdUserData()).uniqueResult())[0] : null;

		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for (var u : ud) {
			if (u.getUserAccount().getLogin().equals(client.getUserAccount().getLogin()) && u.getIdUserData() != client.getIdUserData())
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
		for (var l : lends)
			if (l.getClient().getIdUserData() == idUserData)
				lendClientExist = true;
		return lendClientExist;
	}

	public static boolean validateIfVolumeActiveReservationExists(Integer idVolume) {
		return HibernateUtil.getSession()
				.getNamedQuery("Reservation.checkIfVolumeActiveReservationExists")
				.setParameter("idVolume", idVolume)
				.uniqueResult() != null;
	}

	public static boolean validateIfReservationsLimitExceeded(Integer idUserData, Long additionalReservations) {
		long activeClientReservationCount = ((Long) HibernateUtil.getSession()
				.getNamedQuery("Reservation.countActiveClientReservations")
				.setParameter("idClient", idUserData)
				.getSingleResult()).intValue();
		return activeClientReservationCount + additionalReservations
				> Integer.valueOf(SystemProperties.getInstance().getSystemParameters()
				.get(Constants.APPLICATION_RESERVATIONS_LIMIT).getValue());
	}

	public static boolean validateIfUserCorrespondenceExists(Integer idUserData) {
		List<Recipient> recipients = HibernateUtil.getSession().createQuery("from Recipient as rec where userData.idUserData = ?0")
				.setParameter(0, idUserData).list();
		List<Message> sender = HibernateUtil.getSession().createQuery("from Message as mes where sender.idUserData = ?0")
				.setParameter(0, idUserData).list();
		return !recipients.isEmpty() || !sender.isEmpty();
	}
}

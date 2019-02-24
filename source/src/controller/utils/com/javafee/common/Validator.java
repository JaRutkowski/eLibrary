package com.javafee.common;

import java.util.List;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Book;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Volume;
import com.javafee.hibernate.dto.library.Worker;

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
				result = client.getPeselNumber() != null && (existingLoginClient != null || existingPeselClient != null)
						? false
						: true;
			} else
				result = existingLoginClient != null ? false : true;
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
			result = existingPeselClient != null ? false : true;
		}
		return result;
	}

	public static boolean validateWorkerPesel(String pesel) {
		boolean result = true;
		if (!"".equals(pesel)) {
			Worker existingPeselClient = (Worker) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselClient != null ? false : true;
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
				result = client.getPeselNumber() != null && (existingLoginClient != null || existingPeselClient != null)
						? false
						: true;
			} else
				result = existingLoginClient != null ? false : true;
		} else {
			result = false;
		}

		return result;
	}

	public static boolean validateBookFilter(Author author, Category category, PublishingHouse publishingHouse) {
		return author == null && category == null && publishingHouse == null ? false : true;
	}

	public static boolean validateInventoryNumberExist(String inventoryNumber) {
		Volume existingInventoryNumberVolume = (Volume) HibernateUtil.getSession()
				.getNamedQuery("Volume.checkIfInventoryNumberExist").setParameter("inventoryNumber", inventoryNumber)
				.uniqueResult();
		return existingInventoryNumberVolume != null ? true : false;
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
		return existingIsbnNumber != null ? true : false;
	}
}

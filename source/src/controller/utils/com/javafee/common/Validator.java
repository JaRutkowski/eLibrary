package com.javafee.common;

import java.util.List;
import java.util.stream.Collectors;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.library.Author;
import com.javafee.hibernate.dto.library.Category;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.PublishingHouse;
import com.javafee.hibernate.dto.library.Worker;

public final class Validator {
	public static boolean validateClientUpdate(Client client) {
		Client existingPeselClient = null;
		boolean result;

		Client existingLoginClient = (Client) HibernateUtil.getSession()
				.getNamedQuery("Client.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult();
		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for(UserData u : ud) {
			if(u.getLogin().equals(client.getLogin()) && u.getIdUserData() != client.getIdUserData())
					return false;
		}

		if(existingLoginClient == null ) {
		if (!"".equals(client.getPeselNumber())) {

			Client test = (Client) HibernateUtil.getSession().getNamedQuery("UserData.checkIfUserDataPeselExist")
					.setParameter("peselNumber", client.getPeselNumber()).uniqueResult();

			existingPeselClient = (Client) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
					.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
					.uniqueResult();
			result = client.getPeselNumber() != null && (existingLoginClient != null || existingPeselClient != null)
					? false
					: true;
		} else
			result = existingLoginClient != null ? false : true;
		}else {
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

	// public static boolean validateWorkerPesel(String pesel) {
	// Worker existingPeselClient = (Worker) HibernateUtil.getSession()
	// .getNamedQuery("UserData.checkIfUserDataPeselExist")
	// .setParameter("peselNumber", pesel)
	// .uniqueResult();
	// return existingPeselClient != null ? false : true;
	// }

//	public static boolean validateClientUpdate(Worker client) {
//		boolean result = true;
//		if(!"".equals(client.getPeselNumber())) {
//		Worker existingLoginClient = (Worker) HibernateUtil.getSession()
//				.getNamedQuery("Worker.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
//				.setParameter("id", client.getIdUserData()).uniqueResult();
//		Worker existingPeselClient = (Worker) HibernateUtil.getSession()
//				.getNamedQuery("UserData.checkWithComparingIdIfUserDataPeselExist")
//				.setParameter("peselNumber", client.getPeselNumber()).setParameter("id", client.getIdUserData())
//				.uniqueResult();
//		result = client.getPeselNumber() != null && (existingLoginClient != null || existingPeselClient != null) ? false
//				: true;
//		} else {
//			Worker existingLoginClient = (Worker) HibernateUtil.getSession()
//					.getNamedQuery("UserData.checkIfUserDataLoginExist").setParameter("login", client.getLogin())
//					.setParameter("id", client.getIdUserData()).uniqueResult();
//			if(existingLoginClient != null)
//				result = false;
//		}
//		
//		return result;
//	}
	
	public static boolean validateClientUpdate(Worker client) {
		Worker existingPeselClient = null;
		boolean result;

		Worker existingLoginClient = (Worker) HibernateUtil.getSession()
				.getNamedQuery("Worker.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUserData()).uniqueResult();
		
		List<UserData> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for(UserData u : ud) {
			if(u.getLogin().equals(client.getLogin()) && u.getIdUserData() != client.getIdUserData())
					return false;
		}

		if(existingLoginClient == null ) {
		if (!"".equals(client.getPeselNumber())) {

			Worker test = (Worker) HibernateUtil.getSession().getNamedQuery("UserData.checkIfUserDataPeselExist")
					.setParameter("peselNumber", client.getPeselNumber()).uniqueResult();

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
}

package com.javafee.elibrary.core.startform;

import java.util.Date;
import java.util.List;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.exception.RefusedRegistrationException;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.City;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.LibraryData;
import com.javafee.elibrary.hibernate.dto.library.LibraryWorker;
import com.javafee.elibrary.hibernate.dto.library.Worker;

import lombok.Getter;

public class RegistrationEvent {
	@Getter
	private static RegistrationEvent registrationEvent = null;
	@Getter
	private static Date registrationDate;

	public static UserData userData = null;

	public enum RegistrationFailureCause {
		ALREADY_REGISTERED, PARAMETERS_ERROR, WEAK_PASSWORD, INCORRECT_BIRTH_DATE, UNIDENTIFIED
	}

	private RegistrationEvent() {
	}

	public static RegistrationEvent getInstance(String peselNumber, String documentNumber, String name, String surname,
	                                            String address, City city, Character sex, Date birthDate, String login, String eMail, String password,
	                                            Role role) throws RefusedRegistrationException {
		if (checkRegistration(login, password, peselNumber, role)) {
			registrationEvent = new RegistrationEvent();
			registrationDate = new Date();
			RegistrationEvent.userData = createUser(peselNumber, documentNumber, name, surname, address, city, sex,
					birthDate, login, eMail, password, role);
		} else
			throw new RefusedRegistrationException("Cannot register to the system");
		return registrationEvent;
	}

	public static void forceClearRegistrationEvenet() {
		registrationEvent = null;
	}

	private static boolean checkRegistration(String login, String password, String peselNumber, Role role) {
		boolean result = false;
		UserData userData = (UserData) HibernateUtil.getSession().getNamedQuery("UserData.checkIfUserDataLoginExist")
				.setParameter("login", login).uniqueResult();
		switch (role) {
			case WORKER_LIBRARIAN:
			case CLIENT:
				result = performCheck(userData, password);
			case ADMIN:
				break;
			case WORKER_ACCOUNTANT:
				break;
			default:
				break;
		}

		return result;
	}

	private static boolean performCheck(UserData userData, String password) {
		boolean result = false;
		if (userData != null)
			Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
		else if (Common.checkPasswordStrength(password))
			result = true;
		else
			Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
		return result;
	}

	private static UserData createUser(String peselNumber, String documentNumber, String name, String surname,
	                                   String address, City city, Character sex, Date birthDate, String login, String eMail, String password,
	                                   Role role) {
		HibernateUtil.beginTransaction();
		UserData resultUserData = null;

		switch (role) {
			case WORKER_LIBRARIAN:
				Worker worker = new Worker();
				worker.setPeselNumber(peselNumber);
				worker.setDocumentNumber(documentNumber);
				worker.setName(name);
				worker.setSurname(surname);
				worker.setAddress(address);
				worker.setCity(city);
				worker.setSex(sex);
				worker.setBirthDate(birthDate);
				worker.setLogin(login);
				worker.setEMail(eMail);
				worker.setPassword(Common.createMd5(password));
				worker.setRegistered(Constants.DATA_BASE_REGISTER_DEFAULT_FLAG);
				HibernateUtil.getSession().save(worker);
				HibernateUtil.commitTransaction();

				HibernateUtil.beginTransaction();
				LibraryWorker lWorker = new LibraryWorker();
				lWorker.setIsAccountant(false);
				lWorker.setWorker(worker);
				@SuppressWarnings("unchecked")
				List<LibraryData> libData = HibernateUtil.getSession()
						.createQuery("from LibraryData where idLibraryData = 1").list();
				if (libData.isEmpty()) {
					LibraryData libDataAdm = new LibraryData();
					libDataAdm.setName("Adm");

					HibernateUtil.getSession().save(libDataAdm);
					HibernateUtil.commitTransaction();

					HibernateUtil.beginTransaction();
					lWorker.setLibraryData(libDataAdm);
				} else
					lWorker.setLibraryData(libData.get(0));
				worker.getLibraryWorker().add(lWorker);
				HibernateUtil.getSession().save(lWorker);
				resultUserData = worker;
				break;
			case ADMIN:
				break;
			case CLIENT:
				Client client = new Client();
				client.setPeselNumber(peselNumber);
				client.setDocumentNumber(documentNumber);
				client.setName(name);
				client.setSurname(surname);
				client.setAddress(address);
				client.setCity(city);
				client.setSex(sex);
				client.setBirthDate(birthDate);
				client.setLogin(login);
				client.setEMail(eMail);
				client.setPassword(Common.createMd5(password));
				client.setRegistered(Constants.DATA_BASE_REGISTER_DEFAULT_FLAG);
				HibernateUtil.getSession().save(client);
				resultUserData = client;
				break;
			case WORKER_ACCOUNTANT:
				break;
			default:
				break;
		}

		HibernateUtil.commitTransaction();
		return resultUserData;
	}
}

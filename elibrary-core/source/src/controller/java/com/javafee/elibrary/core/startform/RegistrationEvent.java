package com.javafee.elibrary.core.startform;

import java.util.Date;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.exception.RefusedRegistrationException;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.common.UserAccount;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
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
	                                            String address, String city, Character sex, Date birthDate, String login, String eMail, String password,
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
		UserAccount userAccount = (UserAccount) HibernateUtil.getSession().getNamedQuery("UserAccount.checkIfUserDataLoginExist")
				.setParameter("login", login).uniqueResult();
		return performCheck(userAccount, password);
	}

	private static boolean performCheck(UserAccount userAccount, String password) {
		boolean result = false;
		if (userAccount != null)
			Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
		if (Common.checkPasswordStrength(password))
			result = true;
		else
			Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
		return result;
	}

	private static UserData createUser(String peselNumber, String documentNumber, String name, String surname,
	                                   String address, String city, Character sex, Date birthDate, String login, String eMail, String password,
	                                   Role role) {
		HibernateUtil.beginTransaction();
		UserData resultUserData = null;

		switch (role) {
			case WORKER_LIBRARIAN:
				UserAccount workerUserAccount = new UserAccount();
				workerUserAccount.setLogin(login);
				workerUserAccount.setPassword(Common.createMd5(password));
				workerUserAccount.setRegistered(Constants.DATA_BASE_REGISTER_DEFAULT_FLAG);
				workerUserAccount.setRegistrationDate(registrationDate);
				HibernateUtil.getSession().save(workerUserAccount);
				Worker worker = new Worker();
				worker.setPeselNumber(peselNumber);
				worker.setDocumentNumber(documentNumber);
				worker.setName(name);
				worker.setSurname(surname);
				worker.setAddress(address);
				worker.setCity(city);
				worker.setSex(sex);
				worker.setBirthDate(birthDate);
				worker.setEMail(eMail);
				worker.setUserAccount(workerUserAccount);
				HibernateUtil.getSession().save(worker);
				HibernateUtil.commitTransaction();

				HibernateUtil.beginTransaction();
				LibraryWorker lWorker = new LibraryWorker();
				lWorker.setIsAccountant(false);
				lWorker.setWorker(worker);
				lWorker.setLibraryData(com.javafee.elibrary.hibernate.dao.common.Common.findLibraryDataById(
						com.javafee.elibrary.hibernate.dao.common.Constants.DATA_BASE_LIBRARY_DATA_ID).get());
				worker.getLibraryWorker().add(lWorker);
				HibernateUtil.getSession().save(lWorker);
				resultUserData = worker;
				break;
			case ADMIN:
				break;
			case CLIENT:
				UserAccount clientUserAccount = new UserAccount();
				clientUserAccount.setLogin(login);
				clientUserAccount.setPassword(Common.createMd5(password));
				clientUserAccount.setRegistered(Constants.DATA_BASE_REGISTER_DEFAULT_FLAG);
				clientUserAccount.setRegistrationDate(registrationDate);
				HibernateUtil.getSession().save(clientUserAccount);
				Client client = new Client();
				client.setPeselNumber(peselNumber);
				client.setDocumentNumber(documentNumber);
				client.setName(name);
				client.setSurname(surname);
				client.setAddress(address);
				client.setCity(city);
				client.setSex(sex);
				client.setBirthDate(birthDate);
				client.setEMail(eMail);
				client.setUserAccount(clientUserAccount);
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

package com.javafee.startform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.javafee.common.Common;
import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.Params;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.hibernate.dto.library.Client;
import com.javafee.hibernate.dto.library.LibraryData;
import com.javafee.hibernate.dto.library.LibraryWorker;
import com.javafee.hibernate.dto.library.Worker;

public class RegistrationEvent {
	private static RegistrationEvent registrationEvent = null;
	private static Date registrationDate;

	public static UserData userData = null;

	public enum RegistrationFailureCause {
		ALREADY_REGISTERED, PARAMETERS_ERROR, WEAK_PASSWORD, INCORRECT_BIRTH_DATE, UNIDENTIFIED
	}

	private RegistrationEvent() {
	}

	public static RegistrationEvent getInstance(String peselNumber, String documentNumber, String name, String surname,
			String address, City city, Character sex, Date birthDate, String login, String eMail, String password, Role role) throws RefusedRegistrationException {
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
		switch (role) {
		case WORKER_LIBRARIAN:
			Worker worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
					.setParameter("login", login).uniqueResult();

			if (worker != null)
				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
			else {
				if (Common.checkPasswordStrenght(password))
					result = true;
				else
					Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
			}
		case ADMIN:
			break;
		case CLIENT:
			Client client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
					.setParameter("login", login).uniqueResult();
//			Client existingPeselClient = (Client) HibernateUtil.getSession().getNamedQuery("UserData.checkIfUserDataPeselExist")
//					.setParameter("peselNumber", peselNumber).uniqueResult();
			if(client != null)
				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
//			if (!"".equals(peselNumber) && (client != null || existingPeselClient != null))
//				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
			else {
				if (Common.checkPasswordStrenght(password))
					result = true;
				else
					Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
			}
			break;
		case WORKER_ACCOUNTANT:
			break;
		default:
			break;
		}

		return result;
	}

	private static UserData createUser(String peselNumber, String documentNumber, String name, String surname,
			String address, City city, Character sex, Date birthDate, String login, String eMail, String password, Role role) {
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
			worker.setRegistered(Constans.DATA_BASE_REGISTER_DEFAULT_FLAG);
			HibernateUtil.getSession().save(worker);
			HibernateUtil.commitTransaction();
			
			HibernateUtil.beginTransaction();
			LibraryWorker lWorker = new LibraryWorker();
			lWorker.setIsAccountant(false);
			lWorker.setWorker(worker);
			@SuppressWarnings("unchecked") List<LibraryData> libData = HibernateUtil.getSession().createQuery("from LibraryData where idLibraryData = 1").list();
			if(libData.isEmpty()) {
				LibraryData libDataAdm = new LibraryData();
				libDataAdm.setName("Adm");
				
				HibernateUtil.getSession().save(libDataAdm);
				HibernateUtil.commitTransaction();
				
				HibernateUtil.beginTransaction();
				lWorker.setLibData(libDataAdm);
			} else
				lWorker.setLibData(libData.get(0));
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
			client.setRegistered(Constans.DATA_BASE_REGISTER_DEFAULT_FLAG);
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

	public static Date getRegistrationDate() {
		return registrationDate;
	}

	@SuppressWarnings("unused")
	private static boolean checkParameters(String peselNumber) {
		boolean result = false;
		return result;
	}
}

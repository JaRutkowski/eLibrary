package com.javafee.elibrary.core.startform;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Optional;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.exception.RefusedLogInException;
import com.javafee.elibrary.core.process.ProcessFactory;
import com.javafee.elibrary.core.process.initializator.FeedAdministratorDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedLibraryDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedMessageTypesProcess;
import com.javafee.elibrary.core.process.initializator.FeedSystemDataProcess;
import com.javafee.elibrary.core.process.initializator.FeedSystemParametersProcess;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.common.UserAccount;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.LibraryWorker;
import com.javafee.elibrary.hibernate.dto.library.Worker;

import lombok.Getter;
import lombok.extern.java.Log;

@Log
public final class LogInEvent {
	@Getter
	private static LogInEvent logInEvent = null;
	@Getter
	private static Role role;
	@Getter
	private static Client client;
	@Getter
	private static Worker worker;
	@Getter
	private static LibraryWorker libraryWorker;
	@Getter
	public static UserData userData;
	@Getter
	private static Boolean isAdmin;
	@Getter
	private static Date logInDate;

	public enum LogInFailureCause {
		NOT_REGISTERED, NOT_HIRED, BAD_PASSWORD, NO_USER, BLOCKED, USER_ACCOUNT_NOT_EXISTS, UNIDENTIFIED
	}

	private LogInEvent() {
	}

	public static LogInEvent getInstance(String login, String password) throws RefusedLogInException {
		initializeSystem();
		if (checkLogAndRole(login, password)) {
			logInEvent = new LogInEvent();
			logInDate = new Date();
		} else
			throw new RefusedLogInException("Cannot log in to the system");
		return logInEvent;
	}

	private static void initializeSystem() {
		try {
			ProcessFactory.create(FeedLibraryDataProcess.class).execute();
			ProcessFactory.create(FeedSystemDataProcess.class).execute();
			ProcessFactory.create(FeedAdministratorDataProcess.class).execute();
			ProcessFactory.create(FeedMessageTypesProcess.class).execute();
			ProcessFactory.create(FeedSystemParametersProcess.class).execute();
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			log.severe(e.getMessage());
		}
	}

	private static boolean checkLogAndRole(String login, String password) {
		client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
				.setParameter("login", login).uniqueResult();
		worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
				.setParameter("login", login).uniqueResult();
		isAdmin = Common.isAdmin(login, password);
		boolean result = false, clientExists = client != null, workerExists = worker != null,
				clientNotBlocked = clientExists && !client.getUserAccount().getBlocked(),
				workerNotBlocked = workerExists && !worker.getUserAccount().getBlocked();

		if (clientNotBlocked) {
			if (isAdmin) {
				role = Role.ADMIN;
				userData = com.javafee.elibrary.hibernate.dao.common.Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).get();
			}
			if (checkLoginAndPassword(password)) {
				if (client.getRegistered()) {
					role = Role.CLIENT;
					userData = client;
					result = true;
				} else
					Params.getInstance().add("NOT_REGISTERED", LogInFailureCause.NOT_REGISTERED);
			}
		} else if (worker == null && isAdmin) {
			role = Role.ADMIN;
			userData = com.javafee.elibrary.hibernate.dao.common.Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).get();
			result = true;
		} else if (workerNotBlocked) {
			if (checkLoginAndPassword(password)) {
				if (worker.getRegistered()) {
					if (checkIfHired(worker)) {
						if (libraryWorker.getIsAccountant() != null)
							role = libraryWorker.getIsAccountant() ? Role.WORKER_ACCOUNTANT : Role.WORKER_LIBRARIAN;
						userData = worker;
						result = true;
					} else
						Params.getInstance().add("NOT_HIRED", LogInFailureCause.NOT_HIRED);
				} else
					Params.getInstance().add("NOT_REGISTERED", LogInFailureCause.NOT_REGISTERED);
			}
		} else {
			if (!clientNotBlocked || !workerNotBlocked)
				Params.getInstance().add("BLOCKED", LogInFailureCause.BLOCKED);
			else if (!clientExists || !workerExists)
				Params.getInstance().add("NO_USER", LogInFailureCause.NO_USER);
		}
		return result;
	}

	// TODO 08.07.2017 Method checks if worker is hired in one library, that's the
	// programmer assumption.
	private static boolean checkIfHired(Worker worker) {
		libraryWorker = (LibraryWorker) HibernateUtil.getSession()
				.getNamedQuery("LibraryWorker.checkIfLibraryWorkerHiredExist")
				.setParameter("idWorker", worker.getIdUserData()).uniqueResult();
		return libraryWorker != null;
	}

	private static boolean checkLoginAndPassword(String password) {
		boolean result = false;
		String md5 = Common.createMd5(password);

		if (client != null && md5.equals(client.getPassword()))
			result = true;
		else if (worker != null && md5.equals(worker.getPassword()))
			result = true;

		if ((!result && worker != null) || (!result && client != null))
			Params.getInstance().add("BAD_PASSWORD", LogInFailureCause.BAD_PASSWORD);

		if (!result)
			handleWrongPasswordAttempt(client != null ?
					client : (worker != null ? worker : (libraryWorker != null ? libraryWorker.getWorker() : null)));

		return result;
	}

	private static void handleWrongPasswordAttempt(UserData userData) {
		if (Optional.ofNullable(userData.getUserAccount()).isPresent()) {
			UserAccount userAccount = userData.getUserAccount();
			userAccount.setNumberOfFailedPasswordAttempts(userAccount.getNumberOfFailedPasswordAttempts() + 1);

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().update(userAccount);
			HibernateUtil.commitTransaction();

			if (userAccount.getNumberOfFailedPasswordAttempts()
					>= Integer.valueOf(SystemProperties.getSystemParameters().get(Constants.APPLICATION_NUMBER_OF_ATTEMPTS_LIMIT).getValue()))
				blockUserAccount(userData);
		} else
			Params.getInstance().add("USER_ACCOUNT_NOT_EXISTS", LogInFailureCause.USER_ACCOUNT_NOT_EXISTS);
	}

	private static void blockUserAccount(UserData userData) {
		UserAccount userAccount = userData.getUserAccount();
		userAccount.setBlocked(Boolean.TRUE);
		userAccount.setBlockDate(new Date());
		userAccount.setBlockReason(Constants.BlockReason.WRONG_PASSWORD.getValue());

		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(userAccount);
		HibernateUtil.commitTransaction();

		Params.getInstance().add("BLOCKED", LogInFailureCause.BLOCKED);
	}

	public static void clearLogInData() {
		LogInEvent.role = null;
		LogInEvent.logInDate = null;
	}
}
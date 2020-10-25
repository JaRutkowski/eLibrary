package com.javafee.elibrary.core.startform;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Optional;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.Role;
import com.javafee.elibrary.core.common.Params;
import com.javafee.elibrary.core.common.SystemProperties;
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
				workerNotBlocked = workerExists && !worker.getUserAccount().getBlocked(),
				isAdminLogin = Common.isAdminLogin(login);

		if (clientNotBlocked) {
			if (isAdmin) {
				role = Role.ADMIN;
				userData = com.javafee.elibrary.hibernate.dao.common.Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).get();
			}
			if (client.getRegistered()) {
				if (checkLoginAndPassword(password)) {
					role = Role.CLIENT;
					userData = client;
					result = true;
					if (result) clearNumberOfFailedPasswordAttempts(userData);
				}
			} else
				Params.getInstance().add("NOT_REGISTERED", Constants.LogInFailureCause.NOT_REGISTERED);
		} else if (worker == null && isAdmin) {
			role = Role.ADMIN;
			userData = com.javafee.elibrary.hibernate.dao.common.Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).get();
			result = true;
		} else if (workerNotBlocked) {
			if (worker.getRegistered()) {
				if (checkLoginAndPassword(password)) {
					if (checkIfHired(worker)) {
						if (libraryWorker.getIsAccountant() != null)
							role = libraryWorker.getIsAccountant() ? Role.WORKER_ACCOUNTANT : Role.WORKER_LIBRARIAN;
						userData = worker;
						result = true;
						if (result) clearNumberOfFailedPasswordAttempts(userData);
					} else
						Params.getInstance().add("NOT_HIRED", Constants.LogInFailureCause.NOT_HIRED);
				}
			} else
				Params.getInstance().add("NOT_REGISTERED", Constants.LogInFailureCause.NOT_REGISTERED);
		} else {
			if (((!clientNotBlocked || !workerNotBlocked) && (clientExists || workerExists)) && !isAdminLogin)
				Params.getInstance().add("BLOCKED", Constants.LogInFailureCause.BLOCKED);
			else if (!clientExists || !workerExists)
				Params.getInstance().add("NO_USER", Constants.LogInFailureCause.NO_USER);
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
			Params.getInstance().add("BAD_PASSWORD", Constants.LogInFailureCause.BAD_PASSWORD);

		if (!result && Boolean.valueOf(SystemProperties.getSystemParameters()
				.get(Constants.APPLICATION_BLOCK_ACCOUNT_FUNCTIONALITY).getValue()))
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
				Common.blockUserAccount(userData, true, new Date(), Constants.BlockReason.WRONG_PASSWORD.getValue());
		} else
			Params.getInstance().add("USER_ACCOUNT_NOT_EXISTS", Constants.LogInFailureCause.USER_ACCOUNT_NOT_EXISTS);
	}

	private static void clearNumberOfFailedPasswordAttempts(UserData userData) {
		if (Optional.ofNullable(userData.getUserAccount()).isPresent()) {
			UserAccount userAccount = userData.getUserAccount();
			userAccount.setNumberOfFailedPasswordAttempts(0);

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().update(userAccount);
			HibernateUtil.commitTransaction();
		} else
			Params.getInstance().add("USER_ACCOUNT_NOT_EXISTS", Constants.LogInFailureCause.USER_ACCOUNT_NOT_EXISTS);
	}

	public static void clearLogInData() {
		LogInEvent.role = null;
		LogInEvent.logInDate = null;
	}
}
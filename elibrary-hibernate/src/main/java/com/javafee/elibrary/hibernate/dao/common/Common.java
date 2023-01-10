package com.javafee.elibrary.hibernate.dao.common;

import java.util.Optional;

import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.Language;
import com.javafee.elibrary.hibernate.dto.association.Language_;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.association.MessageType_;
import com.javafee.elibrary.hibernate.dto.common.SystemData;
import com.javafee.elibrary.hibernate.dto.common.SystemParameter;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties_;
import com.javafee.elibrary.hibernate.dto.common.UserAccount;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;
import com.javafee.elibrary.hibernate.dto.library.LibraryData;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.java.Log;

@Log
public class Common {
	public static Optional<Client> findClientById(final int id) {
		Optional<Client> client = Optional.empty();

		try {
			client = Optional.ofNullable(HibernateUtil.getSession().get(Client.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return client;
	}

	public static Optional<SystemProperties> findSystemPropertiesById(final int id) {
		Optional<SystemProperties> systemProperties = Optional.empty();

		try {
			systemProperties = Optional.ofNullable(HibernateUtil.getSession().get(SystemProperties.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return systemProperties;
	}

	public static Optional<UserAccount> findUserAccountById(final int id) {
		Optional<UserAccount> userAccount = Optional.empty();

		try {
			userAccount = Optional.ofNullable(HibernateUtil.getSession().get(UserAccount.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return userAccount;
	}

	public static Optional<UserData> findUserDataById(final int id) {
		Optional<UserData> userData = Optional.empty();

		try {
			userData = Optional.ofNullable(HibernateUtil.getSession().get(UserData.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return userData;
	}

	public static Optional<LibraryData> findLibraryDataById(final int id) {
		Optional<LibraryData> libraryData = Optional.empty();

		try {
			libraryData = Optional.ofNullable(HibernateUtil.getSession().get(LibraryData.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return libraryData;
	}

	public static String findAdminDataByIdAndGetPresentationValue(final int id) {
		Optional<UserData> userData;
		StringBuilder result = null;
		try {
			userData = Optional.ofNullable(HibernateUtil.getSession().get(UserData.class, id));
			if (userData.isPresent()) {
				result = new StringBuilder();
				result.append("[").append(userData.get().getIdUserData()).append(",")
						.append(userData.get().getUserAccount().getLogin()).append(",")
						.append(userData.get().getUserAccount().getPassword())
						.append("]");
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return result != null ? result.toString() : null;
	}

	public static Optional<SystemProperties> findSystemPropertiesByUserAccountId(final int userAccountId) {
		Optional<SystemProperties> systemProperties = Optional.empty();

		try {
			CriteriaBuilder cb = HibernateUtil.createAndGetEntityManager().getCriteriaBuilder();
			CriteriaQuery<SystemProperties> criteria = cb.createQuery(SystemProperties.class);
			Root<SystemProperties> systemPropertiesRoot = criteria.from(SystemProperties.class);
			criteria.select(systemPropertiesRoot);
			criteria.where(cb.equal(systemPropertiesRoot.get(SystemProperties_.userAccount),
					Common.findUserAccountById(userAccountId).get()));
			systemProperties = Optional
					.ofNullable(!HibernateUtil.getEntityManager().createQuery(criteria).getResultList().isEmpty()
							? HibernateUtil.getEntityManager().createQuery(criteria).getResultList().get(0)
							: null);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return systemProperties;
	}

	public static String findSystemDataByIdAndGetPresentationValue(final int id) {
		Optional<SystemData> systemData;
		StringBuilder result = null;
		try {
			systemData = Optional.ofNullable(HibernateUtil.getSession().get(SystemData.class, id));
			if (systemData.isPresent()) {
				result = new StringBuilder();
				result.append("[").append(systemData.get().getVersion()).append(",")
						.append(systemData.get().getBuildNumber()).append(",")
						.append(systemData.get().getInstallationDate() != null ? Constants.APPLICATION_DATE_FORMAT.format(systemData.get().getInstallationDate()) : systemData.get().getInstallationDate()).append(",")
						.append(systemData.get().getSystemDataInitializationDate() != null ? Constants.APPLICATION_DATE_FORMAT.format(systemData.get().getSystemDataInitializationDate()) : systemData.get().getSystemDataInitializationDate()).append(",")
						.append(systemData.get().getNumberOfSystemParameters())
						.append("]");
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return result != null ? result.toString() : null;
	}

	public static String findLibraryDataByIdAndGetPresentationValue(final int id) {
		Optional<LibraryData> libraryData;
		StringBuilder result = null;
		try {
			libraryData = Optional.ofNullable(HibernateUtil.getSession().get(LibraryData.class, id));
			if (libraryData.isPresent()) {
				result = new StringBuilder();
				result.append("[").append(libraryData.get().getName()).append(",");
				if (!libraryData.get().getLibraryBranchData().isEmpty()) {
					int index = 0;
					for (var libraryBranchData : libraryData.get().getLibraryBranchData()) {
						result.append("[").append(libraryBranchData.getAddress()).append(",")
								.append(libraryBranchData.getName()).append(",")
								.append(libraryBranchData.getCity()).append(index != libraryData.get().getLibraryBranchData().size() - 1 ? "]," : "]");
						index++;
					}
				} else
					result.append("[]");
				result.append("]");
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return result != null ? result.toString() : null;
	}

	public static SystemProperties checkAndGetSystemProperties(final int userAccountId) {
		Optional<SystemProperties> systemProperties = Common.findSystemPropertiesByUserAccountId(userAccountId);
		SystemProperties result;

		if (!systemProperties.isPresent()) {
			UserAccount userAccount = Common.findUserAccountById(userAccountId).get();
			HibernateUtil.beginJpaTransaction();
			result = new SystemProperties();
			userAccount.setSystemProperties(result);

			HibernateUtil.getEntityManager().persist(result);
			HibernateUtil.commitJpaTransaction();
		} else
			result = Common.findSystemPropertiesByUserAccountId(userAccountId).get();

		return result;
	}

	public static boolean checkIfAnySystemParametersExists() {
		CriteriaQuery<SystemParameter> cq = HibernateUtil.getSession().getCriteriaBuilder().createQuery(SystemParameter.class);
		TypedQuery<SystemParameter> allQuery = HibernateUtil.getSession().createQuery(cq.select(cq.from(SystemParameter.class)));
		return !allQuery.getResultList().isEmpty();
	}

	public static boolean checkIfAnySystemDataExists() {
		CriteriaQuery<SystemData> cq = HibernateUtil.getSession().getCriteriaBuilder().createQuery(SystemData.class);
		TypedQuery<SystemData> allQuery = HibernateUtil.getSession().createQuery(cq.select(cq.from(SystemData.class)));
		return !allQuery.getResultList().isEmpty();
	}

	public static boolean checkIfAnyLanguageExists() {
		CriteriaQuery<Language> cq = HibernateUtil.getSession().getCriteriaBuilder().createQuery(Language.class);
		TypedQuery<Language> allQuery = HibernateUtil.getSession().createQuery(cq.select(cq.from(Language.class)));
		return !allQuery.getResultList().isEmpty();
	}

	public static Optional<MessageType> findMessageTypeByName(final String name) {
		Optional<MessageType> messageType = Optional.empty();

		try {
			CriteriaBuilder cb = HibernateUtil.createAndGetEntityManager().getCriteriaBuilder();
			CriteriaQuery<MessageType> criteria = cb.createQuery(MessageType.class);
			Root<MessageType> messageTypeRoot = criteria.from(MessageType.class);
			criteria.select(messageTypeRoot);
			criteria.where(cb.equal(messageTypeRoot.get(MessageType_.name), name));
			messageType = Optional
					.ofNullable(!HibernateUtil.getEntityManager().createQuery(criteria).getResultList().isEmpty()
							? HibernateUtil.getEntityManager().createQuery(criteria).getResultList().get(0)
							: null);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return messageType;
	}

	public static Optional<Language> findLanguageByName(final String name) {
		Optional<Language> language = Optional.empty();

		try {
			CriteriaBuilder cb = HibernateUtil.createAndGetEntityManager().getCriteriaBuilder();
			CriteriaQuery<Language> criteria = cb.createQuery(Language.class);
			Root<Language> messageTypeRoot = criteria.from(Language.class);
			criteria.select(messageTypeRoot);
			criteria.where(cb.equal(messageTypeRoot.get(Language_.name), name));
			language = Optional
					.ofNullable(!HibernateUtil.getEntityManager().createQuery(criteria).getResultList().isEmpty()
							? HibernateUtil.getEntityManager().createQuery(criteria).getResultList().get(0)
							: null);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return language;
	}
}

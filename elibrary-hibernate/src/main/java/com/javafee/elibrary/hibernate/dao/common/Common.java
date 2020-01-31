package com.javafee.elibrary.hibernate.dao.common;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.association.MessageType;
import com.javafee.elibrary.hibernate.dto.association.MessageType_;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties;
import com.javafee.elibrary.hibernate.dto.common.SystemProperties_;
import com.javafee.elibrary.hibernate.dto.common.UserData;
import com.javafee.elibrary.hibernate.dto.library.Client;

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

	public static Optional<UserData> findUserDataById(final int id) {
		Optional<UserData> userData = Optional.empty();

		try {
			userData = Optional.ofNullable(HibernateUtil.getSession().get(UserData.class, id));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return userData;
	}

	public static Optional<SystemProperties> findSystemPropertiesByUserDataId(final int userDataId) {
		Optional<SystemProperties> systemProperties = Optional.empty();

		try {
			CriteriaBuilder cb = HibernateUtil.createAndGetEntityManager().getCriteriaBuilder();
			CriteriaQuery<SystemProperties> criteria = cb.createQuery(SystemProperties.class);
			Root<SystemProperties> systemPropertiesRoot = criteria.from(SystemProperties.class);
			criteria.select(systemPropertiesRoot);
			criteria.where(cb.equal(systemPropertiesRoot.get(SystemProperties_.userData),
					Common.findUserDataById(userDataId).get()));
			systemProperties = Optional
					.ofNullable(!HibernateUtil.getEntityManager().createQuery(criteria).getResultList().isEmpty()
							? HibernateUtil.getEntityManager().createQuery(criteria).getResultList().get(0)
							: null);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return systemProperties;
	}

	public static SystemProperties checkAndGetSystemProperties(final int userDataId) {
		Optional<SystemProperties> systemProperties = Common.findSystemPropertiesByUserDataId(userDataId);
		SystemProperties result;

		if (!systemProperties.isPresent()) {
			UserData userData = Common.findUserDataById(userDataId).get();
			HibernateUtil.beginJpaTransaction();
			result = new SystemProperties();
			userData.setSystemProperties(result);

			HibernateUtil.getEntityManager().persist(result);
			HibernateUtil.commitJpaTransaction();
		} else
			result = Common.findSystemPropertiesByUserDataId(userDataId).get();

		return result;
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
}
package com.javafee.hibernate.dao.common;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.MessageType;
import com.javafee.hibernate.dto.association.MessageType_;
import com.javafee.hibernate.dto.library.Client;

public class Common {
	public static Optional<Client> findClientById(final int id) {
		Optional<Client> client = Optional.empty();

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			client = Optional.ofNullable(session.get(Client.class, id));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;
	}

	public static Optional<MessageType> findMessageTypeByName(final String name) {
		Optional<MessageType> messageType = Optional.empty();

		try {
			CriteriaBuilder cb = HibernateUtil.getEntityManager().getCriteriaBuilder();
			CriteriaQuery<MessageType> criteria = cb.createQuery(MessageType.class);
			Root<MessageType> messageTypeRoot = criteria.from(MessageType.class);
			criteria.select(messageTypeRoot);
			criteria.where(cb.equal(messageTypeRoot.get(MessageType_.name), name));
			messageType = Optional
					.ofNullable(!HibernateUtil.getEntityManager().createQuery(criteria).getResultList().isEmpty()
							? HibernateUtil.getEntityManager().createQuery(criteria).getResultList().get(0)
							: null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageType;
	}

}

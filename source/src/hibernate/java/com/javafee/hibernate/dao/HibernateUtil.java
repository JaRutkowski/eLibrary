package com.javafee.hibernate.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import lombok.Getter;

public class HibernateUtil {
	@Getter
	private static final SessionFactory sessionFactory;
	@Getter
	private static final Session session;
	@Getter
	private static final EntityManager entityManager;

	static {
		try {
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
					.configure("hibernate.cfg.xml").build();
			Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			session = sessionFactory.openSession();
			entityManager = sessionFactory.createEntityManager();
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void beginTransaction() {
		session.beginTransaction();
	}

	public static void commitTransaction() {
		session.getTransaction().commit();
	}

	public static void closeSession() throws HibernateException {
		session.close();
	}
}

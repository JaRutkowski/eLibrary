package com.javafee.hibernate.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	// private static final ServiceRegistry serviceRegistry;
	private static final Session session;

	static {
		//Hibernate 5
		try {
			// Configuration configuration = new Configuration().configure();
			// serviceRegistry = new
			// StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			// sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			// session = sessionFactory.openSession();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
					.configure("hibernate.cfg.xml").build();
			Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			session = sessionFactory.openSession();
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}

		// try {
		// sessionFactory = new Configuration().configure().buildSessionFactory();
		// sessionFactory = metadata.getSessionFactoryBuilder().build();
		// session = sessionFactory.openSession();
		// } catch (HibernateException e) {
		// Logger.getLogger("app").log(Level.WARNING, e.getMessage());
		// throw new ExceptionInInitializerError(e);
		// }
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		return session;
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

package com.javafee.hibernate.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;
	private static final Session session;

	static {
	       try
	        {
	    	    Configuration configuration = new Configuration().configure();
	            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	            session = sessionFactory.openSession();

	        }
	        catch (HibernateException he)
	        {
	            System.err.println("Error creating Session: " + he);
	            throw new ExceptionInInitializerError(he);
	        }
		// try {
		// sessionFactory = new Configuration().configure().buildSessionFactory();
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

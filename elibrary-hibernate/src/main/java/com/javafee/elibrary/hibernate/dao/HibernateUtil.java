package com.javafee.elibrary.hibernate.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.reflections.Reflections;

import com.javafee.elibrary.hibernate.dao.common.Constants;

import lombok.Getter;

public class HibernateUtil {
	@Getter
	private static final SessionFactory sessionFactory;
	@Getter
	private static final Session session;
	@Getter
	private static EntityManager entityManager;
	@Getter
	private static final StandardServiceRegistry registry;

	static {
		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

			Map<String, String> settings = new HashMap<>();
			settings.put(Environment.DRIVER, "org.postgresql.Driver");
			settings.put(Environment.URL, "jdbc:postgresql://" + Constants.DATA_BASE_URL);
			settings.put(Environment.USER, Constants.DATA_BASE_USER);
			settings.put(Environment.PASS, Constants.DATA_BASE_PASSWORD);
			settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
			settings.put(Environment.CACHE_PROVIDER_CONFIG, "org.hibernate.cache.internal.NoCacheProvider");
			settings.put(Environment.HBM2DDL_AUTO, "update");
			settings.put(Environment.NON_CONTEXTUAL_LOB_CREATION, "true");

			registryBuilder.applySettings(settings);
			registry = registryBuilder.build();

			Reflections reflections = new Reflections(Constants.DATA_BASE_PACKAGE_TO_SCAN);
			Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);

			MetadataSources sources = new MetadataSources(registry);
			classes.forEach(c -> sources.addAnnotatedClass(c));

			Metadata metadata = sources.getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			session = sessionFactory.openSession();
			entityManager = sessionFactory.createEntityManager();
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void beginTransaction() {
		if (!session.getTransaction().isActive())
			session.beginTransaction();
	}

	public static void commitTransaction() {
		session.getTransaction().commit();
	}

	public static void rollbackTransaction() {
		session.getTransaction().rollback();
	}

	public static void closeSession() {
		session.close();
	}

	public static EntityManager createAndGetEntityManager() {
		entityManager = getSessionFactory().createEntityManager();
		return entityManager;
	}

	public static void beginJpaTransaction() {
		createAndGetEntityManager();
		entityManager.getTransaction().begin();
	}

	public static void commitJpaTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}

package com.javafee.hibernate.dao;

public abstract class DaoFactory {
	@SuppressWarnings("rawtypes")
	public static final HibernateDao<Class, Integer> getHibernateDao(Class entity) {
		return new HibernateDao<Class, Integer>(Class.class);
	}
}

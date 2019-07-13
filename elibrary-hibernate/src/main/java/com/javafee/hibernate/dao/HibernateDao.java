package com.javafee.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Example;

public class HibernateDao<T, Id extends Serializable> implements GenericDao<T, Id> {

	private Class<T> persistentClass;

	public HibernateDao(Class<T> c) {
		persistentClass = c;
	}

	@Override
	public T findByPrimaryKey(Id id) {
		return HibernateUtil.getSession().load(persistentClass, id);
	}

	@Override
	@SuppressWarnings({"unchecked", "deprecation"})
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
		Example example = Example.create(exampleInstance);
		for (String eProperty : excludeProperty)
			example.excludeProperty(eProperty);
		crit.add(example);
		return crit.list();
	}

	@Override
	@SuppressWarnings({"unchecked", "deprecation"})
	public List<T> findAll(int startIndex, int fetchSize) {
		Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
		crit.setFirstResult(startIndex);
		crit.setFetchSize(fetchSize);
		return crit.list();
	}

	@Override
	@SuppressWarnings({"unchecked", "deprecation"})
	public List<T> findAll() {
		Criteria crit = HibernateUtil.getSession().createCriteria(persistentClass);
		return crit.list();
	}

	@Override
	public T save(T entity) {
		try {
			HibernateUtil.getSession().save(entity);
			return entity;
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public T saveOrUpdate(T entity) {
		try {
			HibernateUtil.getSession().saveOrUpdate(entity);
			return entity;
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public T update(T entity) {
		try {
			HibernateUtil.getSession().update(entity);
			return entity;
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public void delete(T entity) {
		try {
			HibernateUtil.getSession().delete(entity);
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public void delete(Id id) {
		try {
			T entity = HibernateUtil.getSession().get(persistentClass, id);
			if (entity != null)
				HibernateUtil.getSession().delete(entity);
			else
				throw new ExceptionInInitializerError();
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public void executeNamedQuery(String queryName) {
		try {
			HibernateUtil.getSession().getNamedQuery(queryName).executeUpdate();
		} catch (HibernateException e) {
			Logger.getLogger("app").log(Level.WARNING, e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public void beginTransaction() {
		HibernateUtil.beginTransaction();
	}

	@Override
	public void commitTransaction() {
		HibernateUtil.commitTransaction();
	}
}

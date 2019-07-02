package com.javafee.hibernate.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, Id extends Serializable> {
	T findByPrimaryKey(Id id);

	List<T> findAll(int startIndex, int fetchSize);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String[] excludeProperty);

	T save(T entity);

	T saveOrUpdate(T entity);

	T update(T entity);

	void delete(T entity);

	void delete(Id id);

	void executeNamedQuery(String queryName);

	void commitTransaction();

	void beginTransaction();

}

package com.javafee.elibrary.rest.api.repository.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.javafee.elibrary.rest.api.repository.dto.api.Authorization;

import lombok.extern.java.Log;

@Log
@ApplicationScoped
public class AuthorizationRepository {
	@PersistenceContext(name = "repository")
	private EntityManager manager;

	public Authorization create(Authorization entity) {
		manager.persist(entity);
		return entity;
	}

	public List<Authorization> findAll() {
		return manager.createQuery("select a from Authorization a").getResultList();
	}

	public Authorization findById(int id) {
		return manager.find(Authorization.class, id);
	}

	public String findPrivateKeyByLogin(String login) {
		List result = manager.createQuery("select a from Authorization a where login = :login")
				.setParameter("login", login).getResultList();
		return result.size() != 0 ? ((Authorization) result.get(0)).getPrivateKey() : null;
	}

	public boolean authenticate(String login, String password) {
		return manager.createQuery("select u from UserAccount u where login = :login and password = :password")
				.setParameter("login", login).setParameter("password", password).getResultList().size() != 0;
	}
}

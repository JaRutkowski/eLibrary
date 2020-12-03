package com.javafee.elibrary.core.model;


import java.util.stream.Collectors;

import com.javafee.elibrary.hibernate.dao.HibernateUtil;

public class RegisteredClientTableModel extends ClientTableModel {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareHibernateDao() {
		this.clients = HibernateUtil.getSession().createQuery("from Client as cl").list();
		this.clients = clients.stream().filter(vol -> vol.getUserAccount().getRegistered()).collect(Collectors.toList());
	}
}

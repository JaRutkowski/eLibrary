package com.javafee.model;



import com.javafee.hibernate.dao.HibernateUtil;

import java.util.stream.Collectors;

public class RegisteredClientTableModel extends ClientTableModel {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareHibernateDao() {
		this.clients = HibernateUtil.getSession().createQuery("from Client as cl").list();
		this.clients = clients.stream().filter(vol -> vol.getRegistered()).collect(Collectors.toList());
	}
}

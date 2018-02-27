package com.javafee.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constans;
import com.javafee.common.Constans.ClientTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.library.Client;

public class RegisteredClientTableModel extends ClientTableModel {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareHibernateDao() {
		this.clients = HibernateUtil.getSession().createQuery("from Client as cl").list();
		this.clients = clients.stream().filter(vol -> vol.getRegistered()).collect(Collectors.toList());
	}
}

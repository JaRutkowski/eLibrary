package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dao.common.Query;

import lombok.extern.java.Log;

@Log
public class FeedAdministratorDataProcess implements Process {
	@Override
	public void execute() {
		feedAdministratorData();
	}

	private void feedAdministratorData() {
		if (!Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).isPresent()) {
			HibernateUtil.beginJpaTransaction();
			HibernateUtil.getEntityManager().createNativeQuery(Query.ProcessQuery.FEED_ADMINISTRATOR_DATA.getValue())
					.setParameter(0, Constants.DATA_BASE_ADMIN_ID)
					.setParameter(1, Constants.DATA_BASE_ADMIN_LOGIN)
					.setParameter(2, Constants.DATA_BASE_ADMIN_PASSWORD)
					.executeUpdate();
			HibernateUtil.commitJpaTransaction();
		}
	}
}

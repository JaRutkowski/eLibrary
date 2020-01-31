package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dao.common.Query;

public class FeedMessageTypesProcess implements Process {
	@Override
	public void execute() {
		feedMessageTypes();
	}

	private void feedMessageTypes() {
		if (!Common.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE).isPresent()
				&& !Common.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE).isPresent()
				&& !Common.findMessageTypeByName(Constants.DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION).isPresent()) {
			HibernateUtil.beginJpaTransaction();
			HibernateUtil.getEntityManager().createNativeQuery(Query.ProcessQuery.FEED_MESSAGE_TYPES.getValue())
					.setParameter(0, Constants.DATA_BASE_MESSAGE_TYPE_USR_MESSAGE)
					.setParameter(1, Constants.DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE)
					.setParameter(2, Constants.DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION)
					.executeUpdate();
			HibernateUtil.commitJpaTransaction();
		}
	}
}

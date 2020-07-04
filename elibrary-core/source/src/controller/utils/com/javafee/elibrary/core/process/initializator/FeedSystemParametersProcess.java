package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dao.common.Query;
import com.javafee.elibrary.hibernate.dto.library.LibrarySystemData;

public class FeedSystemParametersProcess implements Process {
	@Override
	public void execute() {
		feedSystemParameters();
	}

	private void feedSystemParameters() {
		if (!Common.checkIfAnySystemParametersExists()) {
			HibernateUtil.beginJpaTransaction();
			HibernateUtil.getEntityManager().createNativeQuery(Query.ProcessQuery.FEED_SYSTEM_PARAMETERS_DATA.getValue())
					.setParameter(0, Constants.DATA_BASE_SYSTEM_PARAMETER_PENALTY_NAME)
					.setParameter(1, Constants.DATA_BASE_SYSTEM_PARAMETER_PENALTY_VALUE)
					.setParameter(2, Constants.DATA_BASE_SYSTEM_PARAMETER_RESERVATIONS_LIMIT_NAME)
					.setParameter(3, Constants.DATA_BASE_SYSTEM_PARAMETER_RESERVATIONS_LIMIT_VALUE)
					.setParameter(4, Constants.DATA_BASE_SYSTEM_PARAMETER_EMAIL_NAME)
					.setParameter(5, Constants.DATA_BASE_SYSTEM_PARAMETER_EMAIL_VALUE)
					.setParameter(6, Constants.DATA_BASE_SYSTEM_PARAMETER_EMAIL_PASSWORD_NAME)
					.setParameter(7, Constants.DATA_BASE_SYSTEM_PARAMETER_EMAIL_PASSWORD_VALUE)
					.setParameter(8, Constants.DATA_BASE_SYSTEM_PARAMETER_GENERATE_PASSWORD_LENGTH_NAME)
					.setParameter(9, Constants.DATA_BASE_SYSTEM_PARAMETER_GENERATE_PASSWORD_LENGTH_VALUE)
					.setParameter(10, Constants.DATA_BASE_SYSTEM_PARAMETER_TEMPLATE_DIRECTORY_NAME_NAME)
					.setParameter(11, Constants.DATA_BASE_SYSTEM_PARAMETER_TEMPLATE_DIRECTORY_NAME_VALUE)
					.setParameter(12, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MIN_PASSWORD_LENGTH_NAME)
					.setParameter(13, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MIN_PASSWORD_LENGTH_VALUE)
					.setParameter(14, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MAX_PASSWORD_LENGTH_NAME)
					.setParameter(15, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_MAX_PASSWORD_LENGTH_VALUE)
					.setParameter(16, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE_NAME)
					.setParameter(17, Constants.DATA_BASE_SYSTEM_PARAMETER_APPLICATION_COMBO_BOX_DATA_PACKAGE_SIZE_VALUE)
					.executeUpdate();
			HibernateUtil.commitJpaTransaction();
			HibernateUtil.beginTransaction();
			LibrarySystemData librarySystemData = HibernateUtil.getSession().get(LibrarySystemData.class, Constants.DATA_BASE_LIBRARY_DATA_ID);
			librarySystemData.setNumberOfSystemParameters(Constants.DATA_BASE_NUMBER_OF_SYSTEM_PARAMETERS);
			HibernateUtil.getSession().update(librarySystemData);
			HibernateUtil.commitTransaction();
			SystemProperties.getInstance().initializeSystemParameters();
		}
	}
}

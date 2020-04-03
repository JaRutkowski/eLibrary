package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dto.library.LibrarySystemData;

public class FeedSystemDataProcess implements Process {
	@Override
	public void execute() {
		feedSystemData();
	}

	private void feedSystemData() {
		if (!Common.checkIfAnySystemDataExists()) {
			HibernateUtil.beginTransaction();
			LibrarySystemData librarySystemData = new LibrarySystemData();
			librarySystemData.setIdSystemData(Constants.DATA_BASE_SYSTEM_DATA_ID);
			librarySystemData.setSystemDataInitializationDate(Constants.DATA_BASE_SYSTEM_DATA_INITIALIZATION_DATE);
			librarySystemData.setLibraryData(Common.findLibraryDataById(Constants.DATA_BASE_LIBRARY_DATA_ID).get());
			HibernateUtil.getSession().save(librarySystemData);
			HibernateUtil.commitTransaction();
		}
	}
}

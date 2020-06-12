package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dto.library.LibraryBranchData;
import com.javafee.elibrary.hibernate.dto.library.LibraryData;

public class FeedLibraryDataProcess implements Process {
	@Override
	public void execute() {
		feedSystemData();
	}

	private void feedSystemData() {
		if (!Common.checkIfAnySystemDataExists()) {
			HibernateUtil.beginTransaction();
			LibraryData libraryData = new LibraryData();
			libraryData.setIdLibraryData(Constants.DATA_BASE_LIBRARY_DATA_ID);
			libraryData.setName(Constants.DATA_BASE_LIBRARY_DATA_NAME);
			HibernateUtil.getSession().save(libraryData);
			HibernateUtil.commitTransaction();

			HibernateUtil.beginTransaction();
			LibraryBranchData libraryBranchData = new LibraryBranchData();
			libraryBranchData.setIdLibraryBranchData(Constants.DATA_BASE_LIBRARY_BRANCH_DATA_ID);
			libraryBranchData.setName(Constants.DATA_BASE_LIBRARY_BRANCH_DATA_NAME);
			libraryBranchData.setLibraryData(Common.findLibraryDataById(Constants.DATA_BASE_LIBRARY_DATA_ID).get());
			HibernateUtil.getSession().save(libraryBranchData);
			HibernateUtil.commitTransaction();
		}
	}
}

package com.javafee.elibrary.core.process.initializator;

import com.javafee.elibrary.core.process.Process;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Common;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dto.association.Language;

public class FeedLanguageDataProcess implements Process {
	@Override
	public void execute() {
		feedSystemData();
	}

	private void feedSystemData() {
		if (!Common.checkIfAnyLanguageExists()) {
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(createLanguage(Constants.APPLICATION_LANGUAGE_PL));
			HibernateUtil.getSession().save(createLanguage(Constants.APPLICATION_LANGUAGE_EN));
			HibernateUtil.commitTransaction();
		}
	}

	private Language createLanguage(String name) {
		return Language.builder().name(name).build();
	}
}

package com.javafee.process.initializator;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dao.common.Constants;
import com.javafee.hibernate.dao.common.Query;
import com.javafee.process.Process;
import lombok.extern.java.Log;

@Log
public class FeedAdministratorDataProcess implements Process {
    @Override
    public void execute() {
        feedAdministratorData();
    }

    private void feedAdministratorData() {
        if (!Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).isPresent()) {
            HibernateUtil.createAndGetEntityManager();
            HibernateUtil.getEntityManager().getTransaction().begin();
            HibernateUtil.getEntityManager().createNativeQuery(Query.ProcessQuery.FEED_ADMINISTRATOR_DATA.getValue())
                    .setParameter(0, Constants.DATA_BASE_ADMIN_ID)
                    .setParameter(1, Constants.DATA_BASE_ADMIN_LOGIN)
                    .setParameter(2, Constants.DATA_BASE_ADMIN_PASSWORD)
                    .executeUpdate();
            HibernateUtil.getEntityManager().getTransaction().commit();
            HibernateUtil.getEntityManager().close();
        }
    }
}

package com.javafee.process.initializator;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dao.common.Common;
import com.javafee.hibernate.dao.common.Constants;
import com.javafee.process.Process;

public class FeedAdministratorDataProcess implements Process {

    @Override
    public void execute() {
        feedAdministratorData();
    }

    private void feedAdministratorData() {
        if (!Common.findUserDataById(Constants.DATA_BASE_ADMIN_ID).isPresent()) {
            HibernateUtil.createAndGetEntityManager();
            HibernateUtil.getEntityManager().getTransaction().begin();
            HibernateUtil.getEntityManager().createNativeQuery(
                    "insert into public.com_user_data" +
                            "(id_user_data, address, birth_date, document_number, e_mail, login, name, password, pesel, registered, sex, surname, id_city, id_system_properties)" +
                            " values(0, null, null, null, null, \'GENERATED\', null, \'GENERATED\', null, null, null, null, null, null)")
                    .executeUpdate();
            HibernateUtil.getEntityManager().getTransaction().commit();
        }
    }
}

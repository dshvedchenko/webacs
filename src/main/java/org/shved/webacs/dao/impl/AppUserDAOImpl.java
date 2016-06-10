package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public class AppUserDAOImpl implements AppUserDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public AppUserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<AppUser> list() {
        List<AppUser> listUser = (List<AppUser>) sessionFactory.getCurrentSession()
                .createCriteria(AppUser.class)
                .list();
        return listUser;
    }
}

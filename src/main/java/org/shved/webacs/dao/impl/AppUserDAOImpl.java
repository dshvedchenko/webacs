package org.shved.webacs.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public class AppUserDAOImpl extends AbstractDao<Long, AppUser> implements AppUserDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public AppUserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @SuppressWarnings("unchecked")
//    @Transactional
    public List<AppUser> findAllAppUsers() {
        Criteria criteria = createEntityCriteria(AppUser.class);
        return (List<AppUser>) criteria.setFetchMode("permissions", FetchMode.JOIN).list();
    }

    @SuppressWarnings("unchecked")
//    @Transactional
    public AppUser findById(Long id) {
        return getByKey(id, AppUser.class);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveAppUser(AppUser user) {
        persist(user);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public void deleteById(Long id) {
        getSession().delete(getSession().get(AppUser.class, id));
    }


    protected AppUser findByStringProperty(String propName, String value) {
        Criteria criteria = getSession().createCriteria(AppUser.class);
        criteria.add(Restrictions.eq(propName, value).ignoreCase());
        Object userObj = criteria.uniqueResult();
        AppUser appUser = null;
        if (userObj != null) {
            appUser = (AppUser) userObj;
        }
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        return findByStringProperty("username", username);
    }

    @Override
    public AppUser findByEmail(String email) {
        return findByStringProperty("email", email);
    }
}

package org.shved.webacs.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Repository
public class AppUserDAOImpl extends AbstractDao<Long, AppUser> implements IAppUserDAO {

    @SuppressWarnings("unchecked")
    public List<AppUser> findAllAppUsers() {
        Criteria criteria = createEntityCriteria(AppUser.class);
        return (List<AppUser>) criteria.setFetchMode("permission", FetchMode.JOIN).list();
    }

    @Override
    public List<AppUser> findAllEnabled() {
        Criteria criteria = createEntityCriteria(AppUser.class)
                .add(Restrictions.eq("enabled", true));
        return (List<AppUser>) criteria.setFetchMode("permission", FetchMode.JOIN).list();
    }

    @Override
    public List<AppUser> findAllDisabled() {
        Criteria criteria = createEntityCriteria(AppUser.class)
                .add(Restrictions.eq("enabled", false));
        return (List<AppUser>) criteria.setFetchMode("permission", FetchMode.JOIN).list();
    }

    @SuppressWarnings("unchecked")
    public AppUser findById(Long id) {
        return getByKey(id, AppUser.class);
    }

    @SuppressWarnings("unchecked")
    public void save(AppUser user) {
        persist(user);
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

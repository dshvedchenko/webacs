package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.exception.OperationIsNotAllowed;
import org.shved.webacs.model.AppUser;
import org.springframework.stereotype.Repository;

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
        if (user.getId() != null && user.getId() < 1000)
            throw new OperationIsNotAllowed("Can not modify internal users");
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
        Criteria criteria = getSession().createCriteria(AppUser.class);
        criteria.add(
                Restrictions.and(
                        Restrictions.eq("email", email).ignoreCase(),
                        Restrictions.eq("enabled", true)
                )
        );
        Object userObj = criteria.uniqueResult();
        AppUser appUser = null;
        if (userObj != null) {
            appUser = (AppUser) userObj;
        }
        return appUser;
    }
}

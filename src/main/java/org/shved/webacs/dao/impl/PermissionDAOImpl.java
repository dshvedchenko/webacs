package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IPermissionDAO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class PermissionDAOImpl extends AbstractDao<Long, Permission> implements IPermissionDAO {

    @Autowired
    SessionFactory sessionFactory;

    public PermissionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Permission> findAllPermissions() {
        Criteria criteria = getSession().createCriteria(Permission.class);
        return criteria.list();
    }

    @Override
    public Permission findById(Long id) {
        return getByKey(id, Permission.class);
    }

    @Override
    public void save(Permission permission) {
        persist(permission);
    }

    @Override
    public void deleteById(Long id) {
        getSession().delete(getSession().get(Permission.class, id));
    }

    @Override
    public List<Permission> findAllByTitle(String title) {
        Criteria criteria = getSession().createCriteria(Permission.class)
                .add(Restrictions.eq("title", title).ignoreCase());
        return criteria.list();
    }

    @Override
    public List<Permission> findAllByAppUser(AppUser appUser) {
        Criteria criteria = getSession().createCriteria(Permission.class)
                .createCriteria("userPermission")
                .add(Restrictions.eq("user", appUser));
        return criteria.list();
    }

    @Override
    public List<Permission> findAllByResource(Resource resource) {
        Criteria criteria = getSession().createCriteria(Permission.class)
                .add(Restrictions.eq("resource", resource));
        return criteria.list();
    }
}

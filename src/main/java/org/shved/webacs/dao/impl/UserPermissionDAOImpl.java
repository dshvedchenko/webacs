package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.UserPermissionDAO;
import org.shved.webacs.model.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class UserPermissionDAOImpl extends AbstractDao<Long, UserPermission> implements UserPermissionDAO {

    @Autowired
    SessionFactory sessionFactory;

    public UserPermissionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserPermission> findAllUserPermissions() {
        return null;
    }

    @Override
    public UserPermission findById(Long id) {
        return null;
    }

    @Override
    public List<UserPermission> findAllByUserId(Long Id) {
        return null;
    }

    @Override
    public List<UserPermission> findAllByPermission(Long id) {
        return null;
    }

    @Override
    public void disable(UserPermission userPermission) {

    }

    @Override
    public void saveUserPermission(UserPermission userPermission) {

    }

    @Override
    public UserPermission findByClaimId(Long claimId) {
        return null;
    }
}

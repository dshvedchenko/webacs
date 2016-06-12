package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.UserPermissionDAO;
import org.shved.webacs.model.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
        Criteria criteria = getSession().createCriteria(UserPermission.class);
        return criteria.list();
    }

    @Override
    public UserPermission findById(Long id) {
        return getByKey(id, UserPermission.class);
    }

    @Override
    public List<UserPermission> findAllByUserId(Long Id) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .add(Restrictions.eq("user_id", Id));
        return criteria.list();
    }

    @Override
    public List<UserPermission> findAllByPermission(Long id) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .add(Restrictions.eq("permission_id", id));
        return criteria.list();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteByClaimId(Long claimId) {
        getSession().delete(findByClaimId(claimId));
    }


    @Override
    public void saveUserPermission(UserPermission userPermission) {
        persist(userPermission);
    }

    @Override
    public UserPermission findByClaimId(Long claimId) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .add(Restrictions.eq("claim_id", claimId));
        return (UserPermission) criteria.uniqueResult();
    }
}

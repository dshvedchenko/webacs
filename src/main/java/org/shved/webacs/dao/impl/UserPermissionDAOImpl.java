package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.UserPermissionDAO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.model.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
                .createAlias("claim", "cl")
                .add(Restrictions.eq("cl.user.id", Id));
        return criteria.list();
    }

    @Override
    public List<UserPermission> findAllByPermissionId(Long id) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .createAlias("claim", "cl")
                .add(Restrictions.eq("cl.permission.id", id));
        return criteria.list();
    }

    @Override
    public List<UserPermission> findAllByPermission(Permission permission) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .add(Restrictions.eq("permission", permission));
        return criteria.list();
    }
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteByClaim(PermissionClaim claim) {
        getSession().delete(findByClaim(claim));
    }

    @Override
    public void saveUserPermission(UserPermission userPermission) {
        persist(userPermission);
    }

    @Override
    public UserPermission findByClaim(PermissionClaim claim) {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .add(Restrictions.eq("claim", claim));
        return (UserPermission) criteria.uniqueResult();
    }

    @Override
    public List<UserPermission> findAllToBeRevoked() {
        Criteria criteria = getSession().createCriteria(UserPermission.class)
                .createAlias("claim", "cl")
                .add(Restrictions.and(
                        Restrictions.lt("cl.endAt", new Date()),
                        Restrictions.ne("cl.claimState.id", 3)
                        )
                );
        return criteria.list();
    }
}

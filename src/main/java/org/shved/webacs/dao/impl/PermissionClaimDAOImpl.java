package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.PermissionClaimDAO;
import org.shved.webacs.dao.PermissionDAO;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public class PermissionClaimDAOImpl extends AbstractDao<Long, PermissionClaim> implements PermissionClaimDAO {

    @Autowired
    SessionFactory sessionFactory;

    public PermissionClaimDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<PermissionClaim> findAllPermissionClaim() {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class);
        return criteria.list();
    }

    @Override
    public List<PermissionClaim> findAllPermissionClaimByClaimState(ClaimState claimState) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .add(Restrictions.eq("claimState", claimState));
        return criteria.list();
    }
}

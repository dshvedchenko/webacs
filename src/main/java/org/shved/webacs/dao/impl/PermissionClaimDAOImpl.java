package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Repository
public class PermissionClaimDAOImpl extends AbstractDao<Long, PermissionClaim> implements IPermissionClaimDAO {

    @Override
    public List<PermissionClaim> findAllPermissionClaim() {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class);
        return criteria.list();
    }

    @Override
    public void save(PermissionClaim permissionClaim) {
        persist(permissionClaim);
    }

    @Override
    public List<PermissionClaim> findAllByClaimState(ClaimState claimState) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .add(Restrictions.eq("claimState", claimState));
        return criteria.list();
    }

    @Override
    public List<PermissionClaim> findAllByResource(Resource resource) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .createAlias("permission", "p")
                .add(Restrictions.eq("p.resource", resource));
        return criteria.list();
    }

    @Override
    public List<PermissionClaim> findAllByPermission(Permission permission) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .add(Restrictions.eq("permission", permission));
        return criteria.list();
    }

    @Override
    public List<PermissionClaim> findAllClaimed() {
        ClaimState cl = new ClaimState();
        cl.setId(0);
        return findAllByClaimState(cl);
    }

    @Override
    public List<PermissionClaim> findAllApproved() {
        ClaimState cl = new ClaimState();
        cl.setId(1);
        return findAllByClaimState(cl);
    }

    @Override
    public List<PermissionClaim> findAllGranted() {
        ClaimState cl = new ClaimState();
        cl.setId(2);
        return findAllByClaimState(cl);
    }

    @Override
    public List<PermissionClaim> findAllRevoked() {
        ClaimState cl = new ClaimState();
        cl.setId(3);
        return findAllByClaimState(cl);
    }

    @Override
    public List<PermissionClaim> findAllToBeApprovedBy(AppUser appUser) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .add(Restrictions.eq("claimState.id", 0))
                .createAlias("permission", "p")
                .createAlias("p.resource", "r")
                .createAlias("r.ownerPermission", "op")
                .createAlias("op.appUsers", "aus")
                .add(Restrictions.eq("aus.id", appUser.getId()));
        return criteria.list();
    }

    @Override
    public PermissionClaim findById(Long id) {
        Criteria criteria = getSession().createCriteria(PermissionClaim.class)
                .add(Restrictions.eq("id", id));
        return (PermissionClaim) criteria.uniqueResult();
    }

    @Override
    public void deleteById(Long id) {

    }
}

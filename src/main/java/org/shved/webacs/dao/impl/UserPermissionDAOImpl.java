package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.model.UserPermission;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
@Repository
public class UserPermissionDAOImpl extends AbstractDao<Long, UserPermission> implements IUserPermissionDAO {

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
    public void deleteByClaim(PermissionClaim claim) {
        UserPermission userPermission = findByClaim(claim);
        if (userPermission != null) {
            getSession().delete(userPermission);
        }
    }

    @Override
    public void save(UserPermission userPermission) {
        persist(userPermission);
    }

    @Override
    public void recalculateNewEffectiveUserPermissions() {
        getSession().createSQLQuery("INSERT INTO app.user_permission(permission_id, user_id, claim_id) \n" +
                "SELECT \n" +
                "  c.permission_id,\n" +
                "  c.user_id,\n" +
                "  c.id\n" +
                "FROM app.permission_claim c\n" +
                "LEFT JOIN app.user_permission up ON c.id = up.claim_id\n" +
                "WHERE up.id IS NULL\n" +
                " AND c.state_id = 2 " +
                " AND (now() BETWEEN c.start_at AND c.end_at AND c.start_at IS NOT NULL AND c.end_at IS NOT NULL " +
                "                          OR now() > c.start_at AND c.start_at IS NOT NULL AND c.end_at IS NULL  " +
                "                          OR now() < c.end_at AND c.end_at IS NOT NULL AND c.start_at IS NULL" +
                "                          OR c.start_at IS NULL AND c.end_at IS NULL)").executeUpdate();
    }

    @Override
    public void deleteExpiringRevokedUserPermissions() {
        getSession().createSQLQuery("DELETE FROM app.user_permission up\n" +
                "USING app.permission_claim c\n" +
                "WHERE up.claim_id = c.id\n" +
                " AND (now() > c.end_at AND c.end_at IS NOT NULL " +
                "      OR c.state_id = 3)").executeUpdate();
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

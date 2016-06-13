package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.model.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestUserPermissionDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestUserPermissionDAO.class);

    @Autowired
    UserPermissionDAO userPermissionDAO;

    @Autowired
    PermissionDAO permissionDAO;

    @Autowired
    AppUserDAO appUserDAO;

    @Autowired
    PermissionClaimDAO permissionClaimDAO;

    @Test
    public void testDao() {
        Assert.assertNotNull(userPermissionDAO);
    }

    @Test
    public void findAllUserPermission() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
    }

    @Test
    public void findAllToBeRevoked() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllToBeRevoked();
        Assert.assertTrue(userPermissionList.size() == 1);
    }

    @Test
    public void deleteAllToBeRevoked() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllToBeRevoked();
        for (UserPermission userPermission : userPermissionList) {
            PermissionClaim permissionClaim = userPermission.getClaim();
            permissionClaim.setRevokedAt(new Date());
            permissionClaim.setRevoker(appUserDAO.findByUsername("admin"));
            permissionClaimDAO.savePermissionClaim(permissionClaim);
            userPermissionDAO.deleteByClaim(userPermission.getClaim());
            UserPermission upDeleted = userPermissionDAO.findById(userPermission.getId());
            Assert.assertNull(upDeleted);
        }
    }
}

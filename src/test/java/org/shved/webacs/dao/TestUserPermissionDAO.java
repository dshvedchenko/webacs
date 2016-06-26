package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestUserPermissionDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestUserPermissionDAO.class);

    @Autowired
    IUserPermissionDAO userPermissionDAO;

    @Autowired
    IPermissionDAO permissionDAO;

    @Autowired
    IAppUserDAO appUserDAO;

    @Autowired
    IPermissionClaimDAO permissionClaimDAO;

    @Autowired
    IClaimStateDAO claimStateDAO;

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

    @Test
    public void findById() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        Long id = userPermissionList.get(0).getId();
        UserPermission up = userPermissionDAO.findById(id);
        Assert.assertNotNull(up);
    }

    @Test
    public void findByUserId() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        Long id = userPermissionList.get(0).getClaim().getUser().getId();
        List<UserPermission> up = userPermissionDAO.findAllByUserId(id);
        Assert.assertNotNull(up);
        Assert.assertTrue(up.size() > 0);
    }


    @Test
    public void findAllByPermissionId() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        Long id = userPermissionList.get(0).getClaim().getPermission().getId();
        List<UserPermission> up = userPermissionDAO.findAllByPermissionId(id);
        Assert.assertNotNull(up);
        Assert.assertTrue(up.size() > 0);
    }

    @Test
    public void findAllByPermission() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        Permission permission = userPermissionList.get(0).getClaim().getPermission();
        List<UserPermission> up = userPermissionDAO.findAllByPermission(permission);
        Assert.assertNotNull(up);
        Assert.assertTrue(up.size() > 0);
    }

    @Test()
    public void deleteByClaim() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        PermissionClaim pc = userPermissionList.get(0).getClaim();
        userPermissionDAO.deleteByClaim(pc);
    }

    @Test()
    public void findByClaim() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);
        PermissionClaim pc = userPermissionList.get(0).getClaim();
        Assert.assertNotNull(userPermissionDAO.findByClaim(pc));
        ;
    }

    @Test
    public void saveUserPermission() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = claimStateDAO.getById(0);
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);
        UserPermission up = new UserPermission();
        permissionClaimDAO.savePermissionClaim(pc);
        up.setClaim(pc);
        userPermissionDAO.saveUserPermission(up);

        Assert.assertNotNull(up.getId());
    }
}

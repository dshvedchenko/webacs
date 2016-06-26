package org.shved.webacs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public class TestPermissionClaimDAO extends AbstractRepositoryTest {

    @Autowired
    IPermissionClaimDAO permissionClaimDAO;

    @Autowired
    IClaimStateDAO claimStateDAO;

    @Autowired
    IPermissionDAO permissionDAO;

    @Autowired
    IAppUserDAO appUserDAO;

    @Test
    public void testDao() {
        Assert.assertNotNull(permissionClaimDAO);
    }

    @Test
    public void findAllPermissionClaim() {
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaim();
        Assert.assertTrue(permissionClaimList.size() > 0);
    }

    @Test
    public void findAllByClaimState_2() {
        ClaimState claimState = new ClaimState();
        claimState.setId(2);
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllByClaimState(claimState);
        Assert.assertTrue(permissionClaimList.size() > 10);
    }

    @Test
    public void findAllByClaimState_3() {
        ClaimState claimState = new ClaimState();
        claimState.setId(3);
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllByClaimState(claimState);
        Assert.assertTrue(permissionClaimList.size() == 0);
    }

    @Test
    public void findAllByPermission() {
        Permission permission = permissionClaimDAO.findAllPermissionClaim().get(0).getPermission();
        List<PermissionClaim> pcL = permissionClaimDAO.findAllByPermission(permission);
        Assert.assertNotNull(pcL);
    }

    @Test
    public void findAllByResource() {
        Resource resource = permissionClaimDAO.findAllPermissionClaim().get(0).getPermission().getResource();
        List<PermissionClaim> pcL = permissionClaimDAO.findAllByResource(resource);
        Assert.assertNotNull(pcL);
    }

    @Test
    public void findAllApproved() {

    }

    @Test
    public void findAllToBeApprovedBy() {

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

        au = appUserDAO.findByUsername("billk");
        Assert.assertNotNull(au);
        List<PermissionClaim> pc1 = permissionClaimDAO.findAllToBeApprovedBy(au);

        Assert.assertNotNull(pc1);
    }
}

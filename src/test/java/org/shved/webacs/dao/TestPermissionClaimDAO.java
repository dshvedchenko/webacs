package org.shved.webacs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.PermissionClaim;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public class TestPermissionClaimDAO extends AbstractRepositoryTest {

    @Autowired
    PermissionClaimDAO permissionClaimDAO;

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
    public void findAllPermissionClaimByClaimState_2() {
        ClaimState claimState = new ClaimState();
        claimState.setId(2);
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaimByClaimState(claimState);
        Assert.assertTrue(permissionClaimList.size() > 10);
    }

    @Test
    public void findAllPermissionClaimByClaimState_3() {
        ClaimState claimState = new ClaimState();
        claimState.setId(3);
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaimByClaimState(claimState);
        Assert.assertTrue(permissionClaimList.size() == 0);
    }
}

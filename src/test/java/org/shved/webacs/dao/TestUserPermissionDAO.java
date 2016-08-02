package org.shved.webacs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestUserPermissionDAO extends AbstractRepositoryTest {

    @Autowired
    private IUserPermissionDAO userPermissionDAO;

    @Autowired
    private IPermissionDAO permissionDAO;

    @Autowired
    private IAppUserDAO appUserDAO;

    @Autowired
    private IPermissionClaimDAO permissionClaimDAO;

    @Autowired
    private IClaimStateDAO claimStateDAO;

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
            permissionClaimDAO.save(permissionClaim);
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
    }

    @Test
    public void saveUserPermission() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.CLAIMED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);
        UserPermission up = new UserPermission();
        permissionClaimDAO.save(pc);
        up.setClaim(pc);
        userPermissionDAO.save(up);

        Assert.assertNotNull(up.getId());
    }

    @Test
    public void recalculateNewEffectiveUserPermissions_skipClaimedTest() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.CLAIMED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);
        UserPermission up = new UserPermission();
        permissionClaimDAO.save(pc);
        up.setClaim(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNull(up_recalculated);
    }

    @Test
    @Transactional
    public void recalculateNewEffectiveUserPermissionsGrantedTest() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.GRANTED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);
        permissionClaimDAO.save(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNotNull(up_recalculated);
    }

    @Test
    @Transactional
    public void recalculateNewEffectiveUserPermissionsGranted_Start_and_End_Date_SET_Test() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.GRANTED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);

        pc.setStartAt(getDateWithCorrections(-10));
        pc.setEndAt(getDateWithCorrections(+10));

        permissionClaimDAO.save(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNotNull(up_recalculated);
    }


    @Test
    @Transactional
    public void recalculateNewEffectiveUserPermissionsGranted_Start_SET_Test() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.GRANTED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);

        pc.setStartAt(getDateWithCorrections(-10));

        permissionClaimDAO.save(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNotNull(up_recalculated);
    }

    @Test
    @Transactional
    public void recalculateNewEffectiveUserPermissionsGranted_END_SET_Test() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.GRANTED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);

        pc.setEndAt(getDateWithCorrections(+10));

        permissionClaimDAO.save(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNotNull(up_recalculated);
    }

    @Test
    @Transactional
    public void recalculateNewEffectiveUserPermissionsGranted_END_SET_in_PAST_Test() {
        PermissionClaim pc = new PermissionClaim();
        Permission permission = permissionDAO.findAllPermissions().get(0);
        AppUser au = appUserDAO.findAllAppUsers().get(0);
        ClaimState cs = ClaimState.GRANTED;
        pc.setApprover(au);
        pc.setGranter(au);
        pc.setUser(au);
        pc.setPermission(permission);
        pc.setClaimedAt(new Date());
        pc.setClaimState(cs);

        pc.setEndAt(getDateWithCorrections(-10));

        permissionClaimDAO.save(pc);

        userPermissionDAO.recalculateNewEffectiveUserPermissions();

        UserPermission up_recalculated = userPermissionDAO.findByClaim(pc);

        Assert.assertNull(up_recalculated);
    }

    public Date getDateWithCorrections(int correctedMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, correctedMinutes);
        return cal.getTime();
    }
}

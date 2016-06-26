package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public class TestUserDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestUserDAO.class);

    @Autowired
    private IAppUserDAO appUserDAO;



    @Test
    public void daoExists() {
        Assert.assertNotNull(appUserDAO);
    }

    @Test
    public void testUserDao() {
        for (AppUser appUser : appUserDAO.findAllAppUsers()) {
            logger.infof("User %s \r\n", appUser.getUsername());
            logger.infof(" permissions count : " + appUser.getPermissions().size());
        }
    }

    private AppUser initUser() {
        AppUser user = new AppUser();
        user.setUsername("black02");
        user.setEmail("test@test.net");
        user.setEnabled(true);
        user.setFirstname("Jill");
        user.setLastname("Krupps");
        user.setPassword("3420394uw3423423423423423432");
        user.setSysrole(SysRole.GENERIC);
        return user;
    }

    @Test
    @Transactional
    public void createUser() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        logger.info("USER IN DB : " + user);
        Assert.assertNotNull(user.getId());

        user = appUserDAO.findById(user.getId());
        Assert.assertEquals("black02", user.getUsername());
        Assert.assertEquals("test@test.net", user.getEmail());
        Assert.assertEquals(true, user.getEnabled());
        Assert.assertEquals("Jill", user.getFirstname());
        Assert.assertEquals("Krupps", user.getLastname());
        Assert.assertEquals("3420394uw3423423423423423432", user.getPassword());
    }

//    @Test
//    public void deleteUser() {
//        AppUser user = initUser();
//        appUserDAO.saveAppUser(user);
//        logger.info("USER IN DB : " + user);
//        appUserDAO.delete(user);
//        AppUser findDeleted = appUserDAO.findById(user.getId());
//        Assert.assertNull(findDeleted);
//    }
//
//
//    @Test
//    public void deleteUserById() throws Exception {
//        AppUser user = initUser();
//        appUserDAO.saveAppUser(user);
//        logger.info("USER IN DB : " + user);
//        appUserDAO.deleteById(user.getId());
//
//        AppUser findDeleted = appUserDAO.findById(user.getId());
//        Assert.assertNull(findDeleted);
//    }

    @Test
    @Transactional
    public void findByUsername() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        user = appUserDAO.findByUsername("black03");
        Assert.assertNull(user);

        user = appUserDAO.findByUsername("black02");
        Assert.assertNotNull(user);
        Assert.assertEquals("black02", user.getUsername());
        Assert.assertEquals("test@test.net", user.getEmail());
        Assert.assertEquals(true, user.getEnabled());
        Assert.assertEquals("Jill", user.getFirstname());
        Assert.assertEquals("Krupps", user.getLastname());
        Assert.assertEquals("3420394uw3423423423423423432", user.getPassword());
    }

    @Test
    public void findByEmail() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        user = appUserDAO.findByEmail("test@test1.net");
        Assert.assertNull(user);

        user = appUserDAO.findByEmail("test@test.net");
        Assert.assertNotNull(user);
        Assert.assertEquals("black02", user.getUsername());
        Assert.assertEquals("test@test.net", user.getEmail());
        Assert.assertEquals(true, user.getEnabled());
        Assert.assertEquals("Jill", user.getFirstname());
        Assert.assertEquals("Krupps", user.getLastname());
        Assert.assertEquals("3420394uw3423423423423423432", user.getPassword());
    }

    @Test
    public void getAllEnabledTest() {
        List<AppUser> lap = appUserDAO.findAllEnabled();
        Assert.assertEquals(5, lap.size());
        Assert.assertEquals("First should be Admin", (Long) 1L, lap.get(0).getId());
    }

    @Test
    public void getAllDisabledTest() {
        List<AppUser> lap = appUserDAO.findAllDisabled();
        Assert.assertEquals(1, lap.size());
        Assert.assertEquals("Fired Fireoff", (Long) 6L, lap.get(0).getId());
    }
}

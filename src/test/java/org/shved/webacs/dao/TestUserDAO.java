package org.shved.webacs.dao;

import org.hibernate.Session;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.transaction.TransactionManager;

/**
 * @author dshvedchenko on 6/10/16.
 */
public class TestUserDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestUserDAO.class);

    @Autowired
    private AppUserDAO appUserDAO;



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
        user.setSysrole(0);
        return user;
    }

    @Test
    public void createUser() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        logger.info("USER IN DB : " + user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void deleteUser() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        logger.info("USER IN DB : " + user);
        appUserDAO.delete(user);
        AppUser findDeleted = appUserDAO.findById(user.getId());
        Assert.assertNull(findDeleted);
    }


    @Test
    public void deleteUserById() throws Exception {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        logger.info("USER IN DB : " + user);
        appUserDAO.deleteById(user.getId());

        AppUser findDeleted = appUserDAO.findById(user.getId());
        Assert.assertNull(findDeleted);
    }

    @Test
    public void findByUsername() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        user = appUserDAO.findByUsername("black03");
        Assert.assertNull(user);

        user = appUserDAO.findByUsername("black02");
        Assert.assertNotNull(user);
    }

    @Test
    public void findByEmail() {
        AppUser user = initUser();
        appUserDAO.saveAppUser(user);
        user = appUserDAO.findByEmail("test@test1.net");
        Assert.assertNull(user);

        user = appUserDAO.findByEmail("test@test.net");
        Assert.assertNotNull(user);
    }
}

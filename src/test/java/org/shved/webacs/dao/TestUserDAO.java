package org.shved.webacs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dshvedchenko on 6/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test/hibernate-context-test.cfg.xml")
public class TestUserDAO {

    @Autowired
    private AppUserDAO appUserDAO;

    @Test
    public void testUserDao() {
        Assert.assertNotNull(appUserDAO);

        for (AppUser appUser : appUserDAO.list()) {
            System.out.printf("User %s \r\n", appUser.getUsername());
            System.out.println(" permissions count : " + appUser.getPermissions().size());
        }
    }
}

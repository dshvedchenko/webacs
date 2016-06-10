package org.shved.webacs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dshvedchenko on 6/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:hibernate-context-test.cfg.xml")
public class TestUserDAO {

    @Autowired
    private AppUserDAO appUserDAO;

    @Test
    public void testUserDao() {
        Assert.assertNotNull(appUserDAO);
    }
}

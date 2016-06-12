package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void testDao() {
        Assert.assertNotNull(userPermissionDAO);
    }

    @Test
    public void findAllUserPermission() {
        List<UserPermission> userPermissionList = userPermissionDAO.findAllUserPermissions();
        Assert.assertTrue(userPermissionList.size() > 0);


    }

}

package org.shved.webacs.dao;

import junitx.framework.Assert;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestPermissionDAO extends AbstractRepositoryTest {

    @Autowired
    PermissionDAO permissionDAO;

    @Test
    public void checkPermissionDAO() {
        Assert.assertNotNull(permissionDAO);
    }


}

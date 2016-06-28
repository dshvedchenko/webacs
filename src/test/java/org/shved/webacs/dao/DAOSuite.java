package org.shved.webacs.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author dshvedchenko on 6/12/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestPermissionDAO.class,
        TestResourceDAO.class,
        TestUserDAO.class,
        TestAuthTokenDAO.class,
        TestPermissionClaimDAO.class,
        TestUserPermissionDAO.class
})
public class DAOSuite {
}

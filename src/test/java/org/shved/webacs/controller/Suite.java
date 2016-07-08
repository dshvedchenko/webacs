package org.shved.webacs.controller;

import org.junit.runner.RunWith;
import org.shved.webacs.dao.*;

/**
 * @author dshvedchenko on 6/12/16.
 */
@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        TestAuthController.class,
        TestClaimStateController.class,
        TestRestClaimPermissionController.class,
        TestRestResourceController.class,
        TestRestUserController.class,
        TestResTypeController.class,
        TestUserRegistrationController.class
})
public class Suite {
}

package org.shved.webacs;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.orm.jpa.vendor.Database.HSQL;
import static org.springframework.orm.jpa.vendor.Database.POSTGRESQL;

/**
 * @author dshvedchenko on 6/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"POSTGRESQL"})
public abstract class AbstractProfileTest {
    /**
     * Setup the security context before each test.
     */
    @Before
    public void setUp() {
        doInit();
    }

    /**
     * Clear the security context after each test.
     */
    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Set the default user on the security context.
     */
    protected abstract void doInit();
}

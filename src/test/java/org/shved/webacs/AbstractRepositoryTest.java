package org.shved.webacs;

import org.springframework.test.context.ContextConfiguration;

/**
 * @author dshvedchenko on 6/11/16.
 */
@ContextConfiguration("classpath:test/hibernate-context-test.cfg.xml")
public class AbstractRepositoryTest extends AbstractTransactionalProfileTest {
}

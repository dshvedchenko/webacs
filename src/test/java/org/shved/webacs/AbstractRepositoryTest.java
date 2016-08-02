package org.shved.webacs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author dshvedchenko on 6/11/16.
 */
@ContextConfiguration(value = "classpath:test/hibernate-context-test.cfg.xml")
@ComponentScan({"org.shved.webacs.dao"})
public abstract class AbstractRepositoryTest extends AbstractTransactionalProfileTest {
}

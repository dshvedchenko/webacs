package org.shved.webacs;

import org.shved.webacs.config.DAOConfig;
import org.shved.webacs.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dshvedchenko on 6/11/16.
 */
@ContextConfiguration("classpath:test/hibernate-context-test.cfg.xml")
public abstract class AbstractRepositoryTest extends AbstractTransactionalProfileTest {
}

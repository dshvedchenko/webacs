package org.shved.webacs;

//import org.shved.webacs.config.DAOConfig;
import org.shved.webacs.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dshvedchenko on 6/11/16.
 */
@ContextConfiguration(value = "classpath:test/hibernate-context-test.cfg.xml")
@ComponentScan({"org.shved.webacs", "org.shved.webacs.dao.impl.*"})
public abstract class AbstractRepositoryTest extends AbstractTransactionalProfileTest {
}

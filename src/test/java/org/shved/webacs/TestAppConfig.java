package org.shved.webacs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dshvedchenko on 7/1/16.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"org.shved.webacs.*"})
public class TestAppConfig {
}

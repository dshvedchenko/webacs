package org.shved.webacs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dshvedchenko on 7/8/16.
 */
@Configuration
@ComponentScan({"org.shved.webacs.services", "org.shved.webacs.services.impl"})
public class ServiceConfig {
}

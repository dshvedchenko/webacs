package org.shved.webacs.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author dshvedchenko on 6/18/16.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"/WEB-INF/spring.properties"})
@ComponentScan({"org.shved.webacs.dao"})
public class HibernateConfig {
    @Autowired
    Environment env;

    @Bean(name = "sessionFactory")
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("org.shved.webacs.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {

        ComboPooledDataSource cdps = new ComboPooledDataSource();
        try {
            cdps.setDriverClass(env.getProperty("app.jdbc.driverClassName"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cdps.setJdbcUrl(env.getProperty("app.jdbc.url"));
        cdps.setUser(env.getProperty("app.jdbc.username"));
        cdps.setPassword(env.getProperty("app.jdbc.password"));
        cdps.setInitialPoolSize(5);
        cdps.setMinPoolSize(3);
        cdps.setMaxConnectionAge(600);
        cdps.setAcquireIncrement(5);
        cdps.setIdleConnectionTestPeriod(60);
        cdps.setMaxStatements(50);

        return cdps;
    }


    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
                setProperty("hibernate.enable_lazy_load_no_trans", env.getProperty("hibernate.enable_lazy_load_no_trans"));
            }
        };
    }

}

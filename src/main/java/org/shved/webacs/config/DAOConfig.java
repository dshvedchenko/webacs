package org.shved.webacs.config;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.*;
import org.shved.webacs.dao.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dshvedchenko on 6/24/16.
 */
@Configuration
@EnableTransactionManagement
public class DAOConfig {
    @Bean(name = "appUserDAO")
    @Autowired
    AppUserDAO appUserDAO(SessionFactory sessionFactory) {
        return new AppUserDAOImpl(sessionFactory);
    }


    @Bean(name = "authTokenDAO")
    @Autowired
    AuthTokenDAO authTokenDAO(SessionFactory sessionFactory) {
        return new AuthTokenDAOImpl(sessionFactory);
    }

    @Bean(name = "permissionDAO")
    @Autowired
    PermissionDAO permissionDAO(SessionFactory sessionFactory) {
        return new PermissionDAOImpl(sessionFactory);
    }

    @Bean(name = "resourceDAO")
    @Autowired
    ResourceDAO resourceDAO(SessionFactory sessionFactory) {
        return new ResourceDAOImpl(sessionFactory);
    }

    @Bean(name = "userPermissionDAO")
    @Autowired
    UserPermissionDAO userPermissionDAO(SessionFactory sessionFactory) {
        return new UserPermissionDAOImpl(sessionFactory);
    }

    @Bean(name = "permissionClaimDAO")
    @Autowired
    PermissionClaimDAO permissionClaimDAO(SessionFactory sessionFactory) {
        return new PermissionClaimDAOImpl(sessionFactory);
    }

    @Bean(name = "claimStateDAO")
    @Autowired
    ClaimStateDAO claimStateDAO(SessionFactory sessionFactory) {
        return new ClaimStateDAOImpl(sessionFactory);
    }
}

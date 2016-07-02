package org.shved.webacs.config;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.*;
import org.shved.webacs.dao.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dshvedchenko on 6/24/16.
 */
@Configuration
@EnableTransactionManagement
@Component
public class DAOConfig {
//    @Bean(name = "appUserDAO")
//    IAppUserDAO appUserDAO() {
//        return new AppUserDAOImpl();
//    }
//
//    @Bean(name = "authTokenDAO")
//    IAuthTokenDAO authTokenDAO() {
//        return new AuthTokenDAOImpl();
//    }
//
//    @Bean(name = "permissionDAO")
//    IPermissionDAO permissionDAO() {
//        return new PermissionDAOImpl();
//    }
//
//    @Bean(name = "resourceDAO")
//    IResourceDAO resourceDAO() {
//        return new ResourceDAOImpl();
//    }
//
//    @Bean(name = "userPermissionDAO")
//    IUserPermissionDAO userPermissionDAO() {
//        return new UserPermissionDAOImpl();
//    }
//
//    @Bean(name = "permissionClaimDAO")
//    IPermissionClaimDAO permissionClaimDAO() {
//        return new PermissionClaimDAOImpl();
//    }
//
//    @Bean(name = "claimStateDAO")
//    IClaimStateDAO claimStateDAO() {
//        return new ClaimStateDAOImpl();
//    }
//
//    @Bean(name = "resTypeDAO")
//    IResTypeDAO resTypeDAO() {
//        return new ResTypeDAOImpl();
//    }
}

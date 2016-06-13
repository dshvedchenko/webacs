package org.shved.webacs.services;

import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dao.PermissionClaimDAO;
import org.shved.webacs.dao.PermissionDAO;
import org.shved.webacs.dao.UserPermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class PermissionClaimService {

    @Autowired
    PermissionClaimDAO permissionClaimDAO;

    @Autowired
    PermissionDAO permissionDAO;

    @Autowired
    UserPermissionDAO userPermissionDAO;

    @Autowired
    AppUserDAO appUserDAO;
}

package org.shved.webacs.services;

import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dao.PermissionDAO;
import org.shved.webacs.dao.ResourceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class PermissionService {
    @Autowired
    PermissionDAO permissionDAO;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    ResourceDAO resourceDAO;
}

package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IPermissionDAO;
import org.shved.webacs.dao.IResourceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class PermissionServiceImpl {
    @Autowired
    IPermissionDAO permissionDAO;
    @Autowired
    IAppUserDAO appUserDAO;
    @Autowired
    IResourceDAO resourceDAO;
}

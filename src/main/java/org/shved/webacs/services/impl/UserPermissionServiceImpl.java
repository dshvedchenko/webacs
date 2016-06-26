package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IUserPermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class UserPermissionServiceImpl {

    @Autowired
    IUserPermissionDAO userPermissionDAO;
}

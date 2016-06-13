package org.shved.webacs.services;

import org.shved.webacs.dao.UserPermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class UserPermissionService {

    @Autowired
    UserPermissionDAO userPermissionDAO;
}

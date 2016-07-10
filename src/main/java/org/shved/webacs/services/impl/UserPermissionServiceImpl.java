package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.services.IUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class UserPermissionServiceImpl implements IUserPermissionService {

    @Autowired
    IUserPermissionDAO userPermissionDAO;

    @Override
    public void createUserPermissionWithinTimePeriod() {
        userPermissionDAO.recalculateNewEffectiveUserPermissions();
    }

    @Override
    public void dropUserPermissionOutOfTimePeriod() {
        userPermissionDAO.deleteExpiringRevokedUserPermissions();
    }
}

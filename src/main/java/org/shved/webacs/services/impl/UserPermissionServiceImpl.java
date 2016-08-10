package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.services.IUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class UserPermissionServiceImpl implements IUserPermissionService {

    @Autowired
    private IUserPermissionDAO userPermissionDAO;

    @Override
    @Transactional
    @Scheduled(fixedRate = PERMISSION_FROM_CLAIM_DELAY)
    public void createUserPermissionWithinTimePeriod() {
        userPermissionDAO.recalculateNewEffectiveUserPermissions();
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = PERMISSION_FROM_CLAIM_DELAY)
    public void dropUserPermissionOutOfTimePeriod() {
        userPermissionDAO.deleteExpiringRevokedUserPermissions();
    }
}

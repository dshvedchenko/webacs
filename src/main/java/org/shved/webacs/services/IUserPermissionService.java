package org.shved.webacs.services;

/**
 * @author dshvedchenko on 7/10/16.
 */
public interface IUserPermissionService {
    int PERMISSION_FROM_CLAIM_DELAY = 15 * 60 * 1000;
    void createUserPermissionWithinTimePeriod();
    void dropUserPermissionOutOfTimePeriod();
}

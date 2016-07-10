package org.shved.webacs.services;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author dshvedchenko on 7/10/16.
 */
public interface IUserPermissionService {
    public final static int PERMISSION_FROM_CLAIM_DELAY = 15 * 60 * 1000;

    //scheduled API
    @Scheduled(fixedRate = PERMISSION_FROM_CLAIM_DELAY)
    void createUserPermissionWithinTimePeriod();

    @Scheduled(fixedRate = PERMISSION_FROM_CLAIM_DELAY)
    void dropUserPermissionOutOfTimePeriod();
}

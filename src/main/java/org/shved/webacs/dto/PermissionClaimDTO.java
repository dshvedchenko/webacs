package org.shved.webacs.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author dshvedchenko on 7/5/16.
 */
@Data
public class PermissionClaimDTO {
    private Long id;
    private AppUserDTO user;
    private PermissionDTO permission;
    private Date claimedAt;
    private AppUserDTO approver;
    private Date approvedAt;
    private AppUserDTO granter;
    private Date grantedAt;
    private AppUserDTO revoker;
    private Date revokedAt;
    private Date startAt;
    private Date endAt;
    private ClaimStateDTO claimState;
}

package org.shved.webacs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date claimedAt;
    private AppUserDTO approver;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date approvedAt;
    private AppUserDTO granter;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date grantedAt;
    private AppUserDTO revoker;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date revokedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date endAt;
    private ClaimStateDTO claimState;
}

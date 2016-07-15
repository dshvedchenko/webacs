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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date claimedAt;
    private AppUserDTO approver;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date approvedAt;
    private AppUserDTO granter;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date grantedAt;
    private AppUserDTO revoker;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date revokedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "UTC")
    private Date endAt;
    private ClaimStateDTO claimState;
}

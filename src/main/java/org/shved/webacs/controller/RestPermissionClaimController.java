package org.shved.webacs.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IPermissionClaimService;
import org.shved.webacs.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD})
@RequestMapping(value = RestEndpoints.API_V1_CLAIMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionClaimController {

    Logger logger = LoggerFactory.logger(RestPermissionClaimController.class);

    @Autowired
    private IPermissionClaimService permissionClaimService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<PermissionClaimDTO> createPermissionClaim(
            @RequestBody List<CreatePermissionClaimDTO> createClaimList
    ) {
        List<PermissionClaimDTO> createdClaimListDTO = permissionClaimService.create(createClaimList);
        return new ResponseData(createdClaimListDTO);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> findById(
            @PathVariable Long id
    ) {
        PermissionClaimDTO res = permissionClaimService.getById(id);
        return new ResponseData(res);
    }


    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePermissionClaim(
            @RequestBody PermissionClaimDTO claimDTO
    ) {
        permissionClaimService.update(claimDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePermissionClaim(
            @PathVariable("id") Long id
    ) {
        permissionClaimService.delete(id);
    }

    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void approvePermissionClaim(
            @PathVariable("id") Long id
    ) {
        permissionClaimService.approve(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}/grant", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantPermissionClaim(
            @PathVariable("id") Long id
    ) {
        permissionClaimService.grant(id);
    }

    @RequestMapping(value = "/{id}/revoke", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokePermissionClaim(
            @PathVariable("id") Long id
    ) {
        permissionClaimService.revoke(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAll(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAll();
        return new ResponseData(list);
    }

    @PreAuthorize("hasAuthority('GENERIC')")
    @RequestMapping(value = "/own", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getOwnClaims(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllOwn();
        return new ResponseData(list);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/approved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllApproved(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.APPROVED);
        return new ResponseData(list);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/granted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllGranted(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.GRANTED);
        return new ResponseData(list);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/revoked", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllRevoked(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.REVOKED);
        return new ResponseData(list);
    }

}

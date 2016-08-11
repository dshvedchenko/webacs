package org.shved.webacs.controller;

import org.modelmapper.ModelMapper;
import org.shved.webacs.constants.Auth;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IPermissionClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@RequestMapping(value = RestEndpoints.API_V1_CLAIMS)
public class RestPermissionClaimController extends AbstractAPIV1Controller {

    @Autowired
    private ModelMapper modelMapper;

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

    @PreAuthorize(Auth.hasAdminAutority)
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

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAll(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAll();
        return new ResponseData(list);
    }

    @PreAuthorize(Auth.hasGenericAutority)
    @RequestMapping(value = "/own", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getOwnClaims(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllOwn();
        return new ResponseData(list);
    }


    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "/approved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllApproved(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.APPROVED);
        return new ResponseData(list);
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "/granted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllGranted(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.GRANTED);
        return new ResponseData(list);
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "/revoked", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllRevoked(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAllByState(ClaimState.REVOKED);
        return new ResponseData(list);
    }

    @PreAuthorize(Auth.hasGenericAutority)
    @RequestMapping(value = "/claimed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAllClaimed(
    ) {
        List<PermissionClaim> list = permissionClaimService.getAllClaimedForApproval();
        List<PermissionClaimDTO> claims = convertListPermissionClaimsToPermissionClaimDTO(list);
        return new ResponseData(claims);
    }

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        if (permissionClaimList != null) {
            List<PermissionClaimDTO> permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
            return permissions;
        } else return null;
    }
}

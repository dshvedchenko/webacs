package org.shved.webacs.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IPermissionClaimService;
import org.shved.webacs.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = RestEndpoints.API_V1_CLAIMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionClaimController extends AbstractAPIV1Controller {

    Logger logger = LoggerFactory.logger(RestPermissionClaimController.class);

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IPermissionClaimService permissionClaimService;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAuthTokenService authTokenService;


    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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

//
@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseStatus(HttpStatus.ACCEPTED)
public void updatePermissionClaim(
        @RequestBody PermissionClaimDTO claimDTO,
        //TODO - consume id !!!
        @PathVariable("id") Long id
) {
    permissionClaimService.update(claimDTO);
}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePermissionClaim(
            //TODO - consume id !!!
            @PathVariable("id") Long id
    ) {
        permissionClaimService.delete(id);
    }

    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void approvePermissionClaim(
            @RequestBody PermissionClaimDTO claimDTO,
            //TODO - consume id !!!
            @PathVariable("id") Long id
    ) {
        permissionClaimService.approve(id);
    }

    @RequestMapping(value = "/{id}/grant", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantPermissionClaim(
            @RequestBody PermissionClaimDTO claimDTO,
            //TODO - consume id !!!
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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAll(
    ) {
        List<PermissionClaimDTO> list = permissionClaimService.getAll();
        return new ResponseData(list);
    }

}

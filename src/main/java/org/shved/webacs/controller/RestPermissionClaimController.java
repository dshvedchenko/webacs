package org.shved.webacs.controller;

import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IPermissionClaimService;
import org.shved.webacs.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/claim", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionClaimController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IPermissionClaimService permissionClaimService;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAuthTokenService authTokenService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<PermissionClaimDTO> createPermission(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody PermissionClaimDTO claimDTO
    ) {
        authTokenService.isTokenValid(token);
        PermissionClaimDTO createdClaimDTO = permissionClaimService.create(claimDTO);
        return new ResponseData(createdClaimDTO);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> findById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable Long id
    ) {
        authTokenService.isTokenValid(token);
        PermissionClaimDTO res = permissionClaimService.getById(id);
        return new ResponseData(res);
    }

//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void updatePermission(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @RequestBody PermissionDTO permissionDTO,
//            //TODO - consume id !!!
//            @PathVariable("id") Long id
//    ) {
//        authTokenService.isTokenValid(token);
//        permissionService.update(permissionDTO);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void deleteById(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @PathVariable Long id
//    ) {
//        authTokenService.isTokenValid(token);
//        permissionService.deleteById(id);
//    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionClaimDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<PermissionClaimDTO> list = permissionClaimService.getAll();
        return new ResponseData(list);
    }

}

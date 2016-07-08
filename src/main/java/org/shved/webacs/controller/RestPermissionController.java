package org.shved.webacs.controller;

import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.Permission;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IPermissionService;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = RestEndpoints.API_V1_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IAuthTokenService authTokenService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<ResourceDTO> createPermission(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody PermissionDTO permissionDTO
    ) {
        authTokenService.isTokenValid(token);
        PermissionDTO permissionDTO1 = permissionService.create(permissionDTO);
        return new ResponseData(permissionDTO1);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
public ResponseData<PermissionDTO> findById(
        @RequestHeader(name = "X-AUTHID") String token,
        @PathVariable Long id
) {
    authTokenService.isTokenValid(token);
    PermissionDTO res = permissionService.getById(id);
    return new ResponseData(res);
}


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePermission(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody PermissionDTO permissionDTO,
            //TODO - consume id !!!
            @PathVariable("id") Long id
    ) {
        authTokenService.isTokenValid(token);
        permissionService.update(permissionDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable Long id
    ) {
        authTokenService.isTokenValid(token);
        permissionService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<PermissionDTO> list = permissionService.getAll();
        return new ResponseData(list);
    }

}

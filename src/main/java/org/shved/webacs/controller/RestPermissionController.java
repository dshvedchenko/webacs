package org.shved.webacs.controller;

import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResourceDTO;
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
@RequestMapping(value = "/api/v1/permission", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IAuthTokenService authTokenService;

//    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseData<ResourceDTO> createUser(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @RequestBody ResourceDTO resourceDTO
//    ) {
//        authTokenService.isTokenValid(token);
//        ResourceDTO createdRes = resourceService.create(resourceDTO);
//        return new ResponseData(createdRes);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<ResourceDTO> findById(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @PathVariable Long id
//    ) {
//        authTokenService.isTokenValid(token);
//        ResourceDTO res = resourceService.getById(id);
//        return new ResponseData(res);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void updateResource(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @RequestBody ResourceDTO resourceDTO
//    ) {
//        authTokenService.isTokenValid(token);
//        resourceService.updateResource(resourceDTO);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void deleteById(
//            @RequestHeader(name = "X-AUTHID") String token,
//            @PathVariable Long id
//    ) {
//        authTokenService.isTokenValid(token);
//        resourceService.deleteById(id);
//    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<PermissionDTO> list = permissionService.getAll();
        return new ResponseData(list);
    }

}

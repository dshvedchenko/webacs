package org.shved.webacs.controller;

import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.PermissionTitleDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.response.ResponseData;
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
@RequestMapping(value = RestEndpoints.API_V1_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<ResourceDTO> createPermission(
            @RequestBody PermissionDTO permissionDTO
    ) {
        PermissionDTO permissionDTO1 = permissionService.create(permissionDTO);
        return new ResponseData(permissionDTO1);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionDTO> findById(
            @PathVariable Long id
    ) {
        PermissionDTO res = permissionService.getById(id);
        return new ResponseData(res);
    }


    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePermission(
            @RequestBody PermissionDTO permissionDTO
    ) {
        permissionService.update(permissionDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(
            @PathVariable Long id
    ) {
        permissionService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<PermissionDTO> getAll(
    ) {
        List<PermissionDTO> list = permissionService.getAll();
        return new ResponseData(list);
    }


    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAllByResourceId(
            @PathVariable Long id
    ) {
        List<PermissionTitleDTO> list = permissionService.getAllByResourceId(id);
        return new ResponseData(list);
    }

}

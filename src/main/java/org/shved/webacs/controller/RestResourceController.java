package org.shved.webacs.controller;

import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.model.Resource;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/resource", consumes = "application/json", produces = "application/json")
public class RestResourceController {

    @Autowired
    IResourceService resourceService;

    @Autowired
    IAuthTokenService authTokenService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<ResourceDTO> createUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody ResourceDTO resourceDTO
    ) {
        authTokenService.isTokenValid(token);
        ResourceDTO createdRes = resourceService.create(resourceDTO);
        ResponseData rd = new ResponseData();
        rd.setData(createdRes);
        return rd;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> findById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable Long id
    ) {
        authTokenService.isTokenValid(token);
        ResourceDTO res = resourceService.getById(id);
        ResponseData rd = new ResponseData();
        rd.setData(res);
        return rd;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateResource(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody ResourceDTO resourceDTO
    ) {
        authTokenService.isTokenValid(token);
        resourceService.updateResource(resourceDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable Long id
    ) {
        authTokenService.isTokenValid(token);
        resourceService.deleteById(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<ResourceDTO> resourceDTOList = resourceService.getAll();
        ResponseData rd = new ResponseData();
        rd.setData(resourceDTOList);
        return rd;
    }

    @Transactional
    @RequestMapping(value = "/kind/{kindName}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAllByKind(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable String kindName
    ) {
        authTokenService.isTokenValid(token);
        List<ResourceDTO> resourceDTOList = resourceService.getAllByKind(kindName);
        ResponseData rd = new ResponseData();
        rd.setData(resourceDTOList);
        return rd;
    }

}

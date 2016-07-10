package org.shved.webacs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IResourceService;
import org.springframework.asm.TypeReference;
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
@RequestMapping(value = RestEndpoints.API_V1_RESOURCES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestResourceController {

    @Autowired
    IResourceService resourceService;

    @Autowired
    IAuthTokenService authTokenService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<ResourceDTO> createUser(
            @RequestBody ResourceDTO resourceDTO
    ) {
        ResourceDTO createdRes = resourceService.create(resourceDTO);
        return new ResponseData(createdRes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> findById(
            @PathVariable Long id
    ) {
        ResourceDTO res = resourceService.getById(id);
        return new ResponseData(res);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateResource(
            @RequestBody ResourceDTO resourceDTO
    ) {
        resourceService.updateResource(resourceDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(
            @PathVariable Long id
    ) {
        resourceService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAll(
    ) {
        List<ResourceDTO> resourceDTOList = resourceService.getAll();
        return new ResponseData(resourceDTOList);
    }

    @Transactional
    @RequestMapping(value = "/type/{typeId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<ResourceDTO> getAllByResTypeId(
            @PathVariable("typeId") Integer typeId
    ) {
        List<ResourceDTO> resourceDTOList = resourceService.getAllByResTypeId(typeId);
        return new ResponseData(resourceDTOList);
    }
}

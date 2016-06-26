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
import org.springframework.web.bind.annotation.*;

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
    public ResponseData<AuthToken> createUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody ResourceDTO resourceDTO
    ) {
        authTokenService.isTokenValid(token);
        ResourceDTO createdRes = resourceService.create(resourceDTO);
        ResponseData rd = new ResponseData();
        rd.setData(createdRes);
        return rd;
    }
}

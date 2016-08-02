package org.shved.webacs.controller;

import org.shved.webacs.constants.Auth;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IResTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/28/16.
 */
@RestController
@RequestMapping(value = RestEndpoints.API_V1_RESTYPES)
public class RestResTypeController extends AbstractAPIV1Controller {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IResTypeService resTypeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getById(
            @PathVariable(value = "id") Integer id
    ) {
        return new ResponseData(resTypeService.getById(id));
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(
            @PathVariable(value = "id") Integer id
    ) {
        resTypeService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getAll(
    ) {
        return new ResponseData(resTypeService.getAll());
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void update(
            @RequestBody ResTypeDTO updatedItem
    ) {
        resTypeService.save(updatedItem);
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseData<ResTypeDTO> create(
            @RequestBody ResTypeDTO updatedItem
    ) {
        ResTypeDTO rtdto = resTypeService.save(updatedItem);
        return new ResponseData<>(rtdto);
    }

}

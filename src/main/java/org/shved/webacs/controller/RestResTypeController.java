package org.shved.webacs.controller;

import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IClaimStateService;
import org.shved.webacs.services.IResTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/28/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/restypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestResTypeController {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IResTypeService resTypeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "id") Integer id
    ) {
        authTokenService.isTokenValid(token);
        return new ResponseData(resTypeService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        return new ResponseData(resTypeService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "id") Integer id,
            @RequestBody ResTypeDTO updatedItem
    ) {
        authTokenService.isTokenValid(token);
        updatedItem.setId(id);
        resTypeService.save(updatedItem);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseData<ResTypeDTO> create(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody ResTypeDTO updatedItem
    ) {
        authTokenService.isTokenValid(token);
        ResTypeDTO rtdto = resTypeService.save(updatedItem);
        return new ResponseData<>(rtdto);
    }

}

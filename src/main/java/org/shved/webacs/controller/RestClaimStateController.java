package org.shved.webacs.controller;

import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IClaimStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/28/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = RestEndpoints.API_V1_CLAIMSTATES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestClaimStateController {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IClaimStateService claimStateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "id") Integer id
    ) {
        return new ResponseData(claimStateService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        return new ResponseData(claimStateService.getAll());
    }
}

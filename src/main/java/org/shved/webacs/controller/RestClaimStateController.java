package org.shved.webacs.controller;

import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAuthTokenService;
import org.shved.webacs.services.IClaimStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dshvedchenko on 6/28/16.
 */
@RestController
@RequestMapping(value = RestEndpoints.API_V1_CLAIMSTATES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestClaimStateController extends AbstractAPIV1Controller {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IClaimStateService claimStateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getById(
            @PathVariable(value = "id") Integer id
    ) {
        return new ResponseData(claimStateService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<ClaimStateDTO> getAll(
    ) {
        return new ResponseData(claimStateService.getAll());
    }
}

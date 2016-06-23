package org.shved.webacs.controller;

import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
public class RestRegistrationController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    @Qualifier("passwordValidator")
    private Validator validator;

    //TODO : http://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/

    @RequestMapping(value = "/api/register", consumes = "application/json", method = RequestMethod.POST)
    public ResponseData<Boolean> register(
            @Valid @RequestBody UserRegistrationDTO regInfo,
            BindingResult result, WebRequest request, Errors errors
    ) {
        ResponseData rd = new ResponseData();
        AppUser authRes = null;
        authRes = appUserService.registerUser(regInfo);

        rd.setData(true);
        return rd;
    }
}

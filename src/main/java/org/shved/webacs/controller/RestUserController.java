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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
@RequestMapping(value = "/api/v1/user", consumes = "application/json", produces = "application/json")
public class RestUserController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    @Qualifier("passwordValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator); //? only this validator fires
    }

    //TODO : http://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseData<Boolean> register(
            @RequestBody @Valid UserRegistrationDTO regInfo,
            BindingResult result, WebRequest request, Errors errors
    ) {
        ResponseData rd = new ResponseData();
        AppUser authRes = null;
        authRes = appUserService.registerUser(regInfo);

        rd.setData(true);
        return rd;
    }
}

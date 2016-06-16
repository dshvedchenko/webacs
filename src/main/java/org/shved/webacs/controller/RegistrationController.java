package org.shved.webacs.controller;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author dshvedchenko on 6/16/16.
 */
@Controller
public class RegistrationController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    @Qualifier("passwordValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createNewUser(Model model) {
        model.addAttribute(new AppUserDTO());
        return "users/edit";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addAppUserFromForm(@Valid AppUserDTO appUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        appUserService.registerUser(appUserDTO);
        return "redirect:/users" + appUserDTO.getUsername();
    }

}

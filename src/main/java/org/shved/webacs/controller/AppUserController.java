package org.shved.webacs.controller;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
@RestController
@RequestMapping("/api/appusers")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @RequestMapping(
            method = RequestMethod.GET
            , produces = {"application/xml", "application/json"}
    )
    public
    @ResponseBody
    ResponseData<List<AppUserDTO>>
    getAll(@RequestHeader(name = "Accept") String accept) {
        List<AppUserDTO> appUserDTOs = appUserService.getAll();
        ResponseData<List<AppUserDTO>> rs = new ResponseData<>();
        if (appUserDTOs.size() == 0) {

        } else {
            rs.setData(appUserDTOs);
        }

        return rs;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET, params = "new")
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

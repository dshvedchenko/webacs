package org.shved.webacs.controller;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
            , produces = {"application/json", "application/xml"}
    )
    public
    @ResponseBody
    ResponseData<List<AppUserDTO>>
    getAll() {
        List<AppUserDTO> appUserDTOs = appUserService.getAll();
        ResponseData<List<AppUserDTO>> rs = new ResponseData<>();
        if (appUserDTOs.size() == 0) {

        } else {
            rs.setData(appUserDTOs);
        }

        return rs;
    }
}

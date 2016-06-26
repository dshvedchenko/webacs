package org.shved.webacs.controller;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
@RestController
@RequestMapping("/api/v1/appusers")
public class AppUserController {

    @Autowired
    IAppUserService appUserService;

    @Autowired
    ModelMapper modelMapper;

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

}

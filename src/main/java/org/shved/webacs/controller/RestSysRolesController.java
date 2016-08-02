package org.shved.webacs.controller;

import org.modelmapper.ModelMapper;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.model.SysRole;
import org.shved.webacs.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 7/17/16.
 */
@RestController
@RequestMapping(value = RestEndpoints.API_V1_SYSROLES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSysRolesController {
    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<List<String>> getListOfSysRoles(
    ) {
        ResponseData rd = new ResponseData();
        List<String> rolesInfo = Arrays.asList(SysRole.values()).stream()
                .map(item -> {
//                    SysRoleDTO s = new SysRoleDTO();
//                    s.setName(item.name());
//                    s.setId(item.ordinal());
                    return item.name();
                })
                .collect(Collectors.toList());

        rd.setData(rolesInfo);
        return rd;
    }
}

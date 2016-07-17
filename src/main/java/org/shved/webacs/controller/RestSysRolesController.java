package org.shved.webacs.controller;

import org.modelmapper.ModelMapper;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.SysRoleDTO;
import org.shved.webacs.model.SysRole;
import org.shved.webacs.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dshvedchenko on 7/17/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = RestEndpoints.API_V1_SYSROLES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSysRolesController {
    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<List<SysRoleDTO>> getListOfSysRoles(
    ) {
        ResponseData rd = new ResponseData();
        List<SysRoleDTO> rolesInfo = new LinkedList<>();
        SysRoleDTO sysRoleDTO = new SysRoleDTO();
        sysRoleDTO.setId(SysRole.ADMIN.ordinal());
        sysRoleDTO.setName("ADMIN");
        rolesInfo.add(sysRoleDTO);

        sysRoleDTO = new SysRoleDTO();
        sysRoleDTO.setId(SysRole.GENERIC.ordinal());
        sysRoleDTO.setName("GENERIC");
        rolesInfo.add(sysRoleDTO);

        rd.setData(rolesInfo);
        return rd;
    }
}

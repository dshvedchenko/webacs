package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.model.AppUser;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/16/16.
 */
public class Transformer {

    public static AppUserDTO getDTO(AppUser appUser) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setEmail(appUser.getEmail());
        appUserDTO.setLastname(appUser.getLastname());
        appUserDTO.setSysrole(appUser.getSysrole());
        return appUserDTO;
    }

    public static List<AppUserDTO> getDTOList(List<AppUser> appUserList) {
        List<AppUserDTO> appUserDTOList = appUserList.stream().map(
                inp -> Transformer.getDTO(inp)
        ).collect(Collectors.toList());
        return appUserDTOList;
    }

    public static AppUser getAppUser(AppUserDTO dto) {
        AppUser persistObj = new AppUser();
        persistObj.setUsername(dto.getUsername());
        persistObj.setEmail(dto.getEmail());
        persistObj.setLastname(dto.getLastname());
        return persistObj;
    }
}

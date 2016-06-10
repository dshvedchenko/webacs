package org.shved.webacs.services;

import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Service
public class AppUserService {
    @Autowired
    AppUserDAO appUserDAO;

    public List<AppUserDTO> getAll() {
        List<AppUserDTO> appUserDTOs = new LinkedList<>();
        List<AppUser> appUsers = appUserDAO.list();

        for (AppUser u : appUsers) {
            AppUserDTO a = new AppUserDTO();
            a.setUsername(u.getUsername());
            appUserDTOs.add(a);
        }

        return appUserDTOs;
    }
}

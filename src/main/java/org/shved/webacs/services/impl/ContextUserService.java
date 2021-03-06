package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.SysRole;
import org.shved.webacs.services.IContextUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 7/14/16.
 */
@Service
public class ContextUserService implements IContextUserService {

    @Autowired
    IAppUserDAO appUserDAO;

    @Override
    public Boolean isAdmin() {
        return getContextUser().getSysrole() == SysRole.ADMIN;
    }

    @Override
    public AppUser getContextUser() {
        String principal = getPrincipal();
        if (principal != null) {
            AppUser contextUser = appUserDAO.findByUsername(principal);
            if (contextUser == null) throw new AppException("Can not get username from backend fro security principal");
            return contextUser;
        }

        return null;
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            if (principal != null)
            userName = principal.toString();
        }
        return userName;
    }
}

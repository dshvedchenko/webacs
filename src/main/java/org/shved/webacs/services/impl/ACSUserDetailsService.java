package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dshvedchenko on 6/14/16.
 */
@Service("userDetailsService")
public class ACSUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IAppUserDAO appUserDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        AppUser user = appUserDAO.findByUsername(username);
        if (!user.getEnabled()) user = null;
        List<GrantedAuthority> authorities = buildUserAuthority(user.getSysrole());

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(AppUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(SysRole role) {
//        return userRoles.stream()
//                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
//                .collect(Collectors.toList());
        List<GrantedAuthority> result = new LinkedList<>();
        result.add(new SimpleGrantedAuthority("GENERIC"));
        if (role == SysRole.ADMIN) result.add(new SimpleGrantedAuthority("ADMIN"));
        return result;
    }
}

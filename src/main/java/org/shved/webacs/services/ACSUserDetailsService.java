package org.shved.webacs.services;

import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.model.AppUser;
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
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/14/16.
 */
@Service("userDetailsService")
public class ACSUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        AppUser user = appUserDAO.findByUsername(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getSysrole());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(AppUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Integer role) {
//        return userRoles.stream()
//                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
//                .collect(Collectors.toList());
        List<GrantedAuthority> result = new LinkedList<>();
        result.add(new SimpleGrantedAuthority("GENERIC"));
        if (role == 0) result.add(new SimpleGrantedAuthority("ADMIN"));
        return result;
    }
}

package org.shved.webacs.security;

import org.shved.webacs.constants.Auth;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dshvedchenko on 7/8/16.
 */

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    final private IAuthTokenService authTokenService;
    final private UserDetailsService userDetailsService;
//    private AuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(IAuthTokenService authTokenService, UserDetailsService userDetailsService) {
        this.authTokenService = authTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(Auth.AUTH_TOKEN_NAME);
        if (authToken == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            try {
                String username = getUsernameByToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (authTokenService.isTokenValid(authToken)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (NotFoundException e) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }

        }
        chain.doFilter(request, response);
    }


    private String getUsernameByToken(String authToken) {
        AppUserDTO appUserDTO = authTokenService.getUserByToken(authToken);
        if (appUserDTO == null) throw new NotFoundException("TOKEN NOT VALID, not user data");

        return appUserDTO.getUsername();
    }
}

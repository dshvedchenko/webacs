package org.shved.webacs.controller;

import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.constants.Auth;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestAuthController extends AbstractAppTest {


    //@Autowired
    //private IAppUserDAO appUserDAO;

    @Autowired
    private IAuthTokenService authTokenService;

    @Test
    public void testLogin() throws Exception {

        String resp = getTokenInfo();
        Assert.assertTrue(authTokenService.isTokenValid(resp));
    }


    @Test
    public void testBadLogin() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("eeeee");
        mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(contentType))
        ;
    }

    @Test
    public void testLogout() throws Exception {
        String tokenStr = getTokenInfo();
        mockMvc.perform(post("/api/v1/logout")
                .header(Auth.AUTH_TOKEN_NAME, tokenStr)
                .content("")
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
        ;
    }

    @Test
    public void testDoubleLogout() throws Exception {
        String tokenStr = getTokenInfo();
        mockMvc.perform(post("/api/v1/logout")
                .header(Auth.AUTH_TOKEN_NAME, tokenStr)
                .content("")
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
        ;
        mockMvc.perform(post("/api/v1/logout")
                .header(Auth.AUTH_TOKEN_NAME, tokenStr)
                .content("")
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isUnauthorized())
        ;
    }

}

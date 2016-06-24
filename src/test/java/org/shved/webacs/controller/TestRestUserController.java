package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestRestUserController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";


    @Autowired
    private AppUserDAO appUserDAO;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testGetUserById() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("1qaz2wsx");
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResultActions resUserById = mockMvc.perform(post("/api/v1/user/1")
                .content(this.json(loginInfo))
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").exists());

        String useri = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data");
    }

}

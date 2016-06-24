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

    private String userName = "ad";


    @Autowired
    private AppUserDAO appUserDAO;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    //enabled to avoid : Could not obtain transaction-synchronized Session for current thread
    //also it lead that all service+ dao method executed in this transaction and all DB changes rolledback
    @Transactional
    public void testRegisterUser() throws Exception {
        UserRegistrationDTO regInfo = new UserRegistrationDTO();
        String username = UUID.randomUUID().toString();
        String email = username + "_test.com";
        regInfo.setUsername(username);
        regInfo.setPassword("1qaz2wsx");
        regInfo.setEmail(email);
        regInfo.setFirstName("UserJ");
        regInfo.setLastName("UserF");
        regInfo.setSysrole(0);

        ResultActions res = mockMvc.perform(post("/api/v1/user/register")
                .content(this.json(regInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                // .andExpect(jsonPath("$", hasSize(1))) ?? java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)V
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(true));


        AppUser au = appUserDAO.findByUsername(username);
        Assert.assertNotNull(au);
        Assert.assertEquals(username, au.getUsername());
        Assert.assertEquals(email, au.getEmail());
        Assert.assertEquals("UserJ", au.getFirstname());
        Assert.assertEquals("UserF", au.getLastname());
        // appUserDAO.delete(au);
    }

}

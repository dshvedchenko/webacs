package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestRegistrationController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    //enabled to avoid : Could not obtain transaction-synchronized Session for current thread
    @Transactional
    public void testRegisterUser() throws Exception {
        UserRegistrationDTO regInfo = new UserRegistrationDTO();
        String username = UUID.randomUUID().toString();
        regInfo.setUsername(username);
        regInfo.setPassword("1qaz2wsx");
        regInfo.setEmail(username + "@test.com");
        regInfo.setFirstName("UserJ");
        regInfo.setLastName("UserF");
        regInfo.setSysrole(0);

        ResultActions res = mockMvc.perform(post("/api/register")
                .content(this.json(regInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(true));

        AppUser au = appUserDAO.findByUsername(username);
        Assert.assertNotNull(au);
        Assert.assertTrue(au.getUsername().equals(username));
        appUserDAO.delete(au);

//        AppUser appRes = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data");

    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.shved.webacs.config.AppConfig;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


/**
 * @author dshvedchenko on 6/21/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class AbstractAppTest {
    public static final String PASSWORD = "1qaz2wsx";
    protected String userName = "admin";
    protected MediaType contentType = MediaType.APPLICATION_JSON;
    protected HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    @Autowired
    ModelMapper modelMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                //- adding it enables filter
                // but conflicts with @WithMockUser - as latest set SecurityContext
                // removing @WithMockUser - enables full Filter functionality
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected String getTokenInfo() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post(RestEndpoints.API_V1_LOGIN)
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        return JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");
    }

}

package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestAuthController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void testLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                .content(this.json(new UserLogin("admin", "1qaz2wsx")))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isString());
    }


    @Test
    public void testBadLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                .content(this.json(new UserLogin("admin", "wsx")))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

        ;
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


    private class UserLogin {
        @Getter
        String username;
        @Getter
        String password;

        public UserLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}

package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/26/16.
 */
public class TestRestResourceController extends AbstractAppTest {

    public static final String PASSWORD = "1qaz2wsx";

    private MockMvc mockMvc;
    private String userName = "admin";

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Transactional
    @Test
    public void createResourceTest() throws Exception {

        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResourceDTO rdto = new ResourceDTO();
        rdto.setKind("time");
        rdto.setName("future");
        rdto.setDetail("my future");


        ResultActions response = mockMvc.perform(
                post("/api/v1/resource")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rdto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.kind", is("time")))
                .andExpect(jsonPath("$.data.name", is("future")))
                .andExpect(jsonPath("$.data.detail", is("my future")));

        Integer newResourceId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");
        Assert.assertNotNull(newResourceId);
    }

    @Transactional
    @Test
    public void getResurceByIdTest() throws Exception {
        final long EXIST_RESOURCE_ID = 1L;

        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResultActions response = mockMvc.perform(
                get("/api/v1/resource/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("xDep Calendar")))
                .andExpect(jsonPath("$.data.kind", is("Calendar")))
                .andExpect(jsonPath("$.data.detail", is("xDep shared calendar")));

    }

    @Transactional
    @Test
    public void getResurceEditdTest() throws Exception {
        final long EXIST_RESOURCE_ID = 1L;

        final String EXISTS_RESOURCE_KIND = "Calendar";
        final String EXISTS_RESOURCE_DETAIL = "xDep shared calendar";

        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResultActions response = mockMvc.perform(
                get("/api/v1/resource/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("xDep Calendar")))
                .andExpect(jsonPath("$.data.kind", is(EXISTS_RESOURCE_KIND)))
                .andExpect(jsonPath("$.data.detail", is(EXISTS_RESOURCE_DETAIL)));

        Map resourceClientBag = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");

        resourceClientBag.replace("kind", "qazwsx");
        resourceClientBag.replace("detail", "detail detail");

        ResultActions updateResponce = mockMvc.perform(
                put("/api/v1/resource/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(resourceClientBag))
        ).andExpect(status().isAccepted());
    }
}
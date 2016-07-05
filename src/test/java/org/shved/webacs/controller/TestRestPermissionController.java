package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/26/16.
 */
public class TestRestPermissionController extends AbstractAppTest {

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Transactional
    @Test
    public void getAllTest() throws Exception {

        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get("/api/v1/permission/list")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(8)))
                .andExpect(jsonPath("$.data[0].title", is("Reader")))
                .andExpect(jsonPath("$.data[7].title", is("Owner")));

    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {

        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get("/api/v1/permission/" + 1)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.title", is("Reader")))
                .andExpect(jsonPath("$.data.description", is("readers")))
                .andExpect(jsonPath("$.data.resource.id", is(1)));
    }


    @Transactional
    @Test
    public void updatePermissionTest() throws Exception {

        ResultActions response;
        String tokenStr = getTokenInfo();

        Map respData = getPermissionById(1L, tokenStr);
        respData.replace("description", "readers new description");

        response = mockMvc.perform(
                put("/api/v1/permission/1")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(respData))
        ).andExpect(status().isAccepted())
        ;
    }

    @Transactional
    @Test
    public void createPermissionTest() throws Exception {

        ResultActions response;
        String tokenStr = getTokenInfo();

        Map resource = getResourceById(1L, tokenStr);
        ResourceDTO resourceDTO = modelMapper.map(resource, ResourceDTO.class);

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setResource(resourceDTO);
        permissionDTO.setTitle("title 3");
        permissionDTO.setDescription("descr 535");

        response = mockMvc.perform(
                post("/api/v1/permission")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(permissionDTO))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title", is("title 3")))
                .andExpect(jsonPath("$.data.description", is("descr 535")))
        ;
        ;
    }


    @Transactional
    @Test
    public void deletePermissionTest() throws Exception {

        ResultActions response;
        String tokenStr = getTokenInfo();

        Map resource = getResourceById(1L, tokenStr);
        ResourceDTO resourceDTO = modelMapper.map(resource, ResourceDTO.class);

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setResource(resourceDTO);
        permissionDTO.setTitle("title 3");
        permissionDTO.setDescription("descr 535");

        response = mockMvc.perform(
                post("/api/v1/permission")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(permissionDTO))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title", is("title 3")))
                .andExpect(jsonPath("$.data.description", is("descr 535")))
        ;
        Integer newPermId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");
        ;

        response = mockMvc.perform(
                delete("/api/v1/permission/" + newPermId)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
        )
                .andExpect(status().isAccepted());
    }

    private Map getPermissionById(Long id, String tokenStr) throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/permission/" + id)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.title", is("Reader")))
                .andExpect(jsonPath("$.data.description", is("readers")))
                .andExpect(jsonPath("$.data.resource.id", is(id.intValue())));

        return JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");
    }

    private Map getResourceById(Long id, String tokenStr) throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/resource/" + id)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());

        return JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");
    }

    private String getTokenInfo() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        return JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");
    }

}
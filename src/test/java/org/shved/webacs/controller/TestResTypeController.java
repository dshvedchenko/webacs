package org.shved.webacs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dao.IResTypeDAO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.services.IClaimStateService;
import org.shved.webacs.services.IResTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestResTypeController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";

    @Autowired
    private IResTypeDAO resTypeDAO;

    @Autowired
    private IResTypeService resTypeService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void listResTypesTest() throws Exception {
        String tokenStr = getAuthToken();

        mockMvc.perform(
                get("/api/v1/restype/list")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
        )
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[?(@.id == 1)].name", hasItem("Calendar")))
                .andExpect(jsonPath("$.data[?(@.id == 2)].name", hasItem("wiki")))
                .andExpect(jsonPath("$.data[?(@.id == 3)].name", hasItem("room")))
                .andExpect(jsonPath("$.data[?(@.id == 1)].name", hasSize(1)))
                .andExpect(jsonPath("$.data[?(@.id == 2)].name", hasSize(1)))
                .andExpect(jsonPath("$.data[?(@.id == 3)].name", hasSize(1)));
    }


    @Test
    @Transactional
    public void getResTypeByIdTest() throws Exception {
        String tokenStr = getAuthToken();
        Map<Integer, String> resTypes = ImmutableMap.of(1, "Calendar", 2, "wiki", 3, "room");

        for (Map.Entry entry : resTypes.entrySet()) {
            mockMvc.perform(
                    get("/api/v1/restype/" + entry.getKey())
                            .header("X-AUTHID", tokenStr)
                            .accept(contentType)
                            .contentType(contentType)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data.name", is(entry.getValue())))
            ;
        }
    }

    @Test
    @Transactional
    public void getCreateResTypeTest() throws Exception {
        String tokenStr = getAuthToken();
        ResTypeDTO rtdto = new ResTypeDTO();

        rtdto.setName("Software");

        ResultActions createdResp = mockMvc.perform(
                post("/api/v1/restype/")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rtdto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Software")))
                .andExpect(jsonPath("$.data.id", greaterThan(10)));
    }


    private String getAuthToken() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("1qaz2wsx");
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        return JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");
    }


}

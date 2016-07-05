package org.shved.webacs.controller;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.services.IClaimStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
public class TestClaimStateController extends AbstractAppTest {


    @Autowired
    private IClaimStateDAO claimStateDAO;

    @Autowired
    private IClaimStateService claimStateService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void listClaimStateTest() throws Exception {
        String tokenStr = getAuthToken();

        ResultActions claimStateList = mockMvc.perform(
                get("/api/v1/claimstate/list")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[?(@.id == 0)].name", hasItem("CLAIMED")))
                .andExpect(jsonPath("$.data[?(@.id == 1)].name", hasItem("APPROVED")))
                .andExpect(jsonPath("$.data[?(@.id == 2)].name", hasItem("GRANTED")))
                .andExpect(jsonPath("$.data[?(@.id == 3)].name", hasItem("REVOKED")))
                .andExpect(jsonPath("$.data[?(@.id == 0)].name", hasSize(1)))
                .andExpect(jsonPath("$.data[?(@.id == 1)].name", hasSize(1)))
                .andExpect(jsonPath("$.data[?(@.id == 2)].name", hasSize(1)))
                .andExpect(jsonPath("$.data[?(@.id == 3)].name", hasSize(1)));
    }


    @Test
    @Transactional
    public void getClaimStateByIDTest() throws Exception {
        String tokenStr = getAuthToken();
        Map<Integer, String> claimStates = ImmutableMap.of(0, "CLAIMED", 1, "APPROVED", 2, "GRANTED", 3, "REVOKED");

        for (Map.Entry claimState : claimStates.entrySet()) {

            ResultActions claimStateList = mockMvc.perform(
                    get("/api/v1/claimstate/" + claimState.getKey())
                            .header("X-AUTHID", tokenStr)
                            .accept(contentType)
                            .contentType(contentType)

            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data.name", is(claimState.getValue())))
                    ;
        }
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

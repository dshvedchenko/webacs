package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sun.javafx.collections.MappingChange;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.dto.PermissionDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 7/5/16.
 */
public class TestRestClaimPermissionController extends AbstractAppTest {


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void getAllClaimsTest() throws Exception {
        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_CLAIMS)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(14)))
                .andExpect(jsonPath("$.data[0].approver.username", is("billk")))
                .andExpect(jsonPath("$.data[0].user.username", is("ninaa")))
                .andExpect(jsonPath("$.data[0].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].grantedAt", is("2016-06-10 10:20:00.000+0000")))
                .andExpect(jsonPath("$.data[13].approver.username", is("admin")))
                .andExpect(jsonPath("$.data[13].user.username", is("billk")))
                .andExpect(jsonPath("$.data[13].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[13].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[13].grantedAt", is("2016-06-10 10:20:00.000+0000")));
    }

    @Test
    @Transactional
    public void getClaimByIdTest() throws Exception {
        String rawToken = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_CLAIMS + "/1")
                        .header("X-AUTHID", rawToken)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.approver.username", is("billk")))
                .andExpect(jsonPath("$.data.user.username", is("ninaa")))
                .andExpect(jsonPath("$.data.claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data.approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data.grantedAt", is("2016-06-10 10:20:00.000+0000")));
    }

    @Test
    @Transactional
    public void getCreateClaimTest() throws Exception {
        String rawToken = getTokenInfo();

        PermissionDTO pdto = getPermissionDTOById(2L);

        ResultActions response = mockMvc.perform(
                post(RestEndpoints.API_V1_CLAIMS)
                        .header("X-AUTHID", rawToken)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(pdto))

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
        PermissionClaimDTO pcdto = modelMapper.map(JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data"), PermissionClaimDTO.class);
    }

    PermissionDTO getPermissionDTOById(Long id) throws Exception {
        String rawToken = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_PERMISSIONS + "/" + 1)
                        .header("X-AUTHID", rawToken)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk());

        Map rawPerm = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");
        PermissionDTO result = modelMapper.map(rawPerm, PermissionDTO.class);

        return result;
    }

}

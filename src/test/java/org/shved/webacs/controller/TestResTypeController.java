package org.shved.webacs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dao.IResTypeDAO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.ResType;
import org.shved.webacs.services.IClaimStateService;
import org.shved.webacs.services.IResTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestResTypeController extends AbstractAppTest {

    @Autowired
    private IResTypeDAO resTypeDAO;

    @Test
    public void listResTypesTest() throws Exception {
        String tokenStr = getTokenInfo();

        mockMvc.perform(
                get(RestEndpoints.API_V1_RESTYPES)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
        )
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(6)))
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
        String tokenStr = getTokenInfo();
        Map<Integer, String> resTypes = ImmutableMap.of(1, "Calendar", 2, "wiki", 3, "room");

        for (Map.Entry entry : resTypes.entrySet()) {
            mockMvc.perform(
                    get(RestEndpoints.API_V1_RESTYPES + "/" + entry.getKey())
                            .header("X-AUTHID", tokenStr)
                            .accept(contentType)
                            .contentType(contentType)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data.name", is(entry.getValue())))
                    .andExpect(jsonPath("$.data.id", is(entry.getKey())))
            ;
        }
    }

    @Test
    @Transactional
    public void getCreateResTypeTest() throws Exception {
        String tokenStr = getTokenInfo();
        ResTypeDTO rtdto = new ResTypeDTO();

        rtdto.setName("Software");

        ResultActions createdResp = mockMvc.perform(
                post(RestEndpoints.API_V1_RESTYPES + "/")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rtdto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Software")))
                .andExpect(jsonPath("$.data.id", greaterThan(10)));
    }

    @Test
    @Transactional
    public void getEditResTypeTest() throws Exception {
        String tokenStr = getTokenInfo();
        ResTypeDTO rtdto = new ResTypeDTO();

        rtdto.setName("Software");

        ResultActions createdResp = mockMvc.perform(
                post(RestEndpoints.API_V1_RESTYPES + "/")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rtdto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Software")))
                .andExpect(jsonPath("$.data.id", greaterThan(10)));

        Map newResType = JsonPath.read(createdResp.andReturn().getResponse().getContentAsString(), "$.data");
        Integer newId = (Integer) newResType.get("id");
        rtdto.setId(newId);
        rtdto.setName("Commercial Software");

        ResultActions editedResp = mockMvc.perform(
                put(RestEndpoints.API_V1_RESTYPES)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rtdto))
        )
                .andExpect(status().isOk());
        ResType rt = resTypeDAO.findById(newId);
        Assert.assertEquals("Commercial Software", rt.getName());
    }


    @Test
    @Transactional
    public void getDeleteResTypeTest() throws Exception {
        String tokenStr = getTokenInfo();
        ResTypeDTO rtdto = new ResTypeDTO();

        rtdto.setName("Software");

        ResultActions createdResp = mockMvc.perform(
                post(RestEndpoints.API_V1_RESTYPES + "/")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rtdto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Software")))
                .andExpect(jsonPath("$.data.id", greaterThan(10)));

        Map newResType = JsonPath.read(createdResp.andReturn().getResponse().getContentAsString(), "$.data");
        Integer newId = (Integer) newResType.get("id");
        rtdto.setId(newId);
        rtdto.setName("Commercial Software");

        ResultActions editedResp = mockMvc.perform(
                delete(RestEndpoints.API_V1_RESTYPES + "/" + newId)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
        )
                .andExpect(status().isOk());
        ResType rt = resTypeDAO.findById(newId);
        Assert.assertNull(rt);
    }

}
